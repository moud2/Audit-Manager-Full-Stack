describe('Sidebar Navigation', () => {
    beforeEach(() => {
        cy.visit('http://localhost:5173/',{ timeout: 10000 });
    });

    it('Links are working', () => {

        cy.get('[data-cy="nav-dashboard"]',{ timeout: 10000 }).click()

        cy.url().should('eq', 'http://localhost:5173/');
  
    });
});
  