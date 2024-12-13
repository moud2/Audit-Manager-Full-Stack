describe('Sidebar Component', () => {
    beforeEach(() => {
        // Besuch der Seite, auf der die Sidebar gerendert wird
        cy.visit('/Dashboard');
      });
  
    it('should render the sidebar', () => {
      cy.get('[data-cy=sidebar]').should('exist');
    });
  
    it('should open the sidebar when clicking the menu icon', () => {
      cy.get('[data-cy=menu-icon]').click();
      cy.get('[data-cy=sidebar]').should('be.visible');
    });
  
    it('should close the sidebar when clicking the close icon', () => {
      cy.get('[data-cy=menu-icon]').click(); // Öffnen
      cy.get('[data-cy=close-icon]').click(); // Schließen
      cy.get('[data-cy=sidebar]').should('not.be.visible');
    });
  });