// Helper functions for intercepts
function interceptGET() {
    cy.intercept('GET', '/api/v1/audits/1/ratings', {
        statusCode: 200,
        body: [
            {
                id: 1,
                question: 'Frage 1',
                points: null,
                comment: '',
                nA: null,
                category: { id: 1, name: 'Category 1' },
            },
            {
                id: 2,
                question: 'Frage 2',
                points: 2,
                comment: 'Gut',
                nA: false,
                category: { id: 1, name: 'Category 1' },
            },
            {
                id: 3,
                question: 'Frage 3',
                points: null,
                comment: 'nA',
                nA: true,
                category: { id: 2, name: 'Category 2' },
            },
        ],
    }).as('getRatings');
}


function interceptPATCH() {
    cy.intercept('PATCH', '/api/v1/ratings/*', {
        statusCode: 200,
    }).as('patchRating');
}

// Cypress test suite
describe('PerformAudit Page', () => {
    beforeEach(() => {
        interceptGET();
        interceptPATCH();
        cy.visit('http://localhost:5173/#/perform-Audit/1');
    });

    it('should load and display questions grouped by categories', () => {
        cy.wait('@getRatings');

        // Check that categories are displayed
        cy.contains('Category 1').should('be.visible');
        cy.contains('Category 2').should('be.visible');

        // Check that questions are displayed under correct categories
        cy.contains('Category 1')
            .parent()
            .within(() => {
                cy.contains('Frage 1').should('be.visible');
                cy.contains('Frage 2').should('be.visible');
            });
        cy.contains('Category 2')
            .parent()
            .within(() => {
                cy.contains('Frage 3').should('be.visible');
            });
    });

    it('should handle checkbox interactions correctly', () => {
        cy.wait('@getRatings');

        // Toggle a checkbox and ensure it updates the UI
        cy.contains('Frage 1')
            .parent()
            .within(() => {
                cy.get('input[type="checkbox"]').check();
                cy.get('input[type="checkbox"]').should('be.checked');
            });
    });

    it('should send PATCH request for checkbox change correctly', () => {
        cy.wait('@getRatings');
    
        // Check and trigger PATCH
        cy.contains('Frage 1')
            .parent()
            .within(() => {
                cy.get('input[type="checkbox"]').check();
            });
    
        cy.wait('@patchRating').its('request.body').should('deep.equal', [
            { op: 'replace', path: '/na', value: true },
            { op: 'replace', path: '/points', value: null }, // Assuming initial points are null
            { op: 'replace', path: '/comment', value: '' },  // Assuming initial comment is empty
        ]);
    });
    

    it('should handle comment input and send PATCH request', () => {
        cy.get('[data-cy="question-comment"]').first().click().type('Neue Anmerkung');
        cy.wait(1500);
        cy.wait('@patchRating').its('request.body')
            .should('deep.equal', [
                { op: 'replace', path: '/na', value: null },
                { op: 'replace', path: '/points', value: null },
                { op: 'replace', path: '/comment', value: 'Neue Anmerkung' }
            ]);
        cy.get('[data-cy="question-comment"]').first().should('have.value', 'Neue Anmerkung');
    });


    it('should display error correctly if GET request fails', () => {
        // Intercept GET request with an error response
        cy.intercept('GET', '/api/v1/audits/1/ratings', {
            statusCode: 500,
            body: { message: 'Server Error' },
        }).as('getRatingsError');

        // Reload the page
        cy.visit('http://localhost:5173/#/perform-Audit/1');
        cy.wait('@getRatingsError');

        // Verify error message is displayed
        cy.contains('Fehler').should('be.visible');
        cy.contains('Server Error').should('be.visible');
    });


    it('should display error correctly if PATCH request fails', () => {
        cy.wait('@getRatings');

        // Trigger a PATCH request by checking a checkbox
        cy.contains('Frage 1')
            .parent()
            .within(() => {
                cy.get('input[type="checkbox"]').check();
            });

        // Intercept PATCH request with an error response
        cy.intercept('PATCH', '/api/v1/ratings/*', {
            statusCode: 500,
            body: { message: 'Server Error' },
        }).as('patchRatingError');

        // Wait for the failed PATCH request
        cy.wait('@patchRatingError');

        // Verify the error message is displayed
        cy.contains('Fehler').scrollIntoView().should('be.visible');
        cy.contains('Server Error').scrollIntoView().should('be.visible');
    });

});