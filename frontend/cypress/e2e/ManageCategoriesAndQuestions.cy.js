describe('ManageCategoriesAndQuestions Page', () => {
    beforeEach(() => {
      
      cy.visit('localhost:5173/#/manage-categories-and-questions');
    });

  
    it('export button exists and triggers functionality on button click', () => {
      cy.get('[data-cy="ExportQuestionsButton"]')
      .should('exist')
      .and('be.visible').click();
      // Füge hier weitere Überprüfungen hinzu, z. B. ob ein API-Aufruf stattfindet
    });
  
    it('import button exists and triggers functionality on button click', () => {
      cy.get('[data-cy="ImportQuestionsButton"]')
      .should('exist')
      .and('be.visible').click();
      // Füge hier weitere Überprüfungen hinzu, z. B. ob eine Datei-Upload-Komponente geöffnet wird
    });
  });
  