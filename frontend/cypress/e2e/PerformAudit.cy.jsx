export const interceptGET = () => {
    cy.intercept('GET', '/api/v1/audits/1/ratings', {
        statusCode: 200,
        body: [
            {
                id: 1,
                question: "Frage 1",
                points: null,
                comment: '',
                nA: null,
                category: { id: 1, name: "Category 1" }
            },
            {
                id: 2,
                question: "Frage 2",
                points: 2,
                comment: 'Gut',
                nA: false,
                category: { id: 1, name: "Category 1" }
            },
            {
                id: 3,
                question: "Frage 3",
                points: null,
                comment: 'nA',
                nA: true,
                category: { id: 2, name: "Category 2" }
            }
        ]
    }).as('getQuestions');
};

// Intercept PATCH request for updating a question
export const interceptPATCH = () => {
    cy.intercept('PATCH', '/v1/ratings/1', {
        statusCode: 200,
    }).as('patchQuestion');
};

describe('PerformAudit Page', () => {
    beforeEach(() => {
        interceptGET();
        interceptPATCH();
        cy.visit('http://localhost:5173/#/perform-Audit/1');
    });

    it('should load and display questions grouped by categories', () => {
        cy.wait('@getQuestions');
        cy.get('.category-list').should('exist');
        cy.get('.category-list').within(() => {
            cy.contains('Category 1').should('exist');
            cy.contains('Frage 1').should('exist');
            cy.contains('Frage 2').should('exist');
            cy.contains('Category 2').should('exist');
            cy.contains('Frage 3').should('exist');
        });
    });

    it('should handle checkbox interactions correctly', () => {
        cy.get('input[type="checkbox"]').eq(0).check({ force: true }).should('be.checked');
        cy.get('input[type="checkbox"]').eq(0).uncheck({ force: true }).should('not.be.checked');
        cy.get('input[type="checkbox"]').eq(1).check({ force: true }).should('be.checked');
        cy.get('input[type="checkbox"]').eq(1).uncheck({ force: true }).should('not.be.checked');
        cy.get('input[type="checkbox"]').eq(2).check({ force: true }).should('be.checked');
        cy.get('input[type="checkbox"]').eq(2).uncheck({ force: true }).should('not.be.checked');
        cy.get('input[type="checkbox"]').eq(6).check({ force: true }).should('be.checked');
        cy.get('input[type="checkbox"]').eq(6).uncheck({ force: true }).should('not.be.checked');
    });

    it.only('should handle comment input and send PATCH request', () => {
        cy.get('[data-cy="question-comment"]').first().click().type('Neue Anmerkung');
        // cy.wait(1500);
        cy.wait('@patchQuestion')
        //     .its('request.body').should('deep.include', {
        //     op: 'replace',
        //     path: '/comment',
        //     value: 'Neue Anmerkung',
        // });
    });

    it('should navigate to the evaluation page on button click', () => {
        cy.get('button').contains('Bewertung anzeigen').click();
        cy.url().should('include', '/evaluation/1');
    });
});