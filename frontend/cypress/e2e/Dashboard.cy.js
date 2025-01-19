describe('Dashboard Navigation Tests', () => {
    // Test when backend is available
    context('Backend is available', () => {
        beforeEach(() => {
            cy.intercept('GET', '/api/v1/audits', (req) => {
                req.reply({
                    statusCode: 200,
                    body: [
                        { id: 1, name: 'Audit 1' },
                        { id: 2, name: 'Audit 2' },
                        { id: 3, name: 'Example Audit' },
                        { id: 4, name: 'Failed Audit' },
                    ],
                });
            }).as('getAudits');

            cy.intercept('GET', '/api/v1/audits?customer=Example+Audit&sortBy=createdAt&sortDirection=desc', {
                statusCode: 200,
                body: [{ id: 3, name: 'Example Audit' }],
            }).as('getFilteredAudits');
            
            cy.visit('http://localhost:5173/#');
        });

        it('Names and links of fetched data work as expected', () => {
            cy.wait('@getAudits');

            // Check that all audits are displayed
            cy.get('[data-cy="data-buttons"]').should('have.length', 4);
            cy.get('[data-cy="data-buttons"]').eq(0).should('contain', 'Audit 1');
            cy.get('[data-cy="data-buttons"]').eq(1).should('contain', 'Audit 2');
            cy.get('[data-cy="data-buttons"]').eq(2).should('contain', 'Example Audit');
            cy.get('[data-cy="data-buttons"]').eq(3).should('contain', 'Failed Audit');
        });

        it('Search field exists and works correctly', () => {
            cy.wait('@getAudits');

            // Check if the search field exists
            cy.get('input#outlined-basic').should('exist').should('be.visible');

            // Enter a search term and ensure the correct audit is displayed
            cy.get('input#outlined-basic').type('Example Audit');
            cy.wait('@getFilteredAudits'); // Warte auf die Antwort der gefilterten Anfrage

            // Verify that only "Example Audit" is displayed
            cy.get('[data-cy="data-buttons"]').should('have.length', 1);
            cy.get('[data-cy="data-buttons"]').first().should('contain', 'Example Audit');
        });
    });

    // Test when backend is not available
    context('Backend is not available', () => {
        beforeEach(() => {
            cy.intercept('GET', '/api/v1/audits', {
                statusCode: 500,
                body: { error: "Backend not available" },
            }).as('getAuditsError');
            
            cy.visit('http://localhost:5173/#');
        });

        it('Plus button is visible and navigates correctly', () => {
            // Check if the plus icon box is visible
            cy.get('[data-cy="new-audit-button"]').should('be.visible');
            cy.get('[data-cy="new-audit-button"]').click();
            cy.url().should('include', '/new-audit');
        });


        it('No data buttons are visible when backend fails', () => {
            cy.wait('@getAuditsError');
            
            // No data buttons should be visible
            cy.get('[data-cy="data-buttons"]').should('not.exist');
        });


        it('should display error correctly if GET request fails', () => {

            // Reload the page
            cy.visit('http://localhost:5173/#');
            cy.wait('@getAuditsError');
    
            // Verify error message is displayed
            cy.contains('Fehler').should('be.visible');
        });

    });
    
});
