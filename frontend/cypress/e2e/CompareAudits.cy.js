describe('CompareAudits Page', () => {
    beforeEach(() => {
      // Intercept für die Audit-Liste mit mindestens 3 Kategorien pro Audit
      cy.intercept('GET', 'http://localhost:8080/api/v1/audits', {
        statusCode: 200,
        body: [
          { id: 1, name: 'Audit 1', customer: 'Kunde A', createdAt: '2023-12-01' },
          { id: 2, name: 'Audit 2', customer: 'Kunde B', createdAt: '2023-12-02' },
          { id: 3, name: 'Audit 3', customer: 'Kunde C', createdAt: '2023-12-03' },
        ],
      }).as('fetchAllAudits');
  
      // Intercept für den Fortschritt von Audit 1 mit 3 Kategorien
      cy.intercept('GET', 'http://localhost:8080/api/v1/audits/1/progress', {
        statusCode: 200,
        body: {
          currentAuditProgress: 75,
          categoryProgress: [
            { categoryName: 'Kategorie 1', currentCategoryProgress: 80 },
            { categoryName: 'Kategorie 2', currentCategoryProgress: 60 },
            { categoryName: 'Kategorie 3', currentCategoryProgress: 90 },
          ],
        },
      }).as('fetchFirstAudit');
  
      // Intercept für den Fortschritt von Audit 2 mit 3 Kategorien
      cy.intercept('GET', 'http://localhost:8080/api/v1/audits/2/progress', {
        statusCode: 200,
        body: {
          currentAuditProgress: 60,
          categoryProgress: [
            { categoryName: 'Kategorie 1', currentCategoryProgress: 50 },
            { categoryName: 'Kategorie 2', currentCategoryProgress: 70 },
            { categoryName: 'Kategorie 3', currentCategoryProgress: 40 },
          ],
        },
      }).as('fetchSecondAudit');
  
      // Intercept für den Fortschritt von Audit 3 mit 3 Kategorien
      cy.intercept('GET', 'http://localhost:8080/api/v1/audits/3/progress', {
        statusCode: 200,
        body: {
          currentAuditProgress: 85,
          categoryProgress: [
            { categoryName: 'Kategorie 1', currentCategoryProgress: 90 },
            { categoryName: 'Kategorie 2', currentCategoryProgress: 80 },
            { categoryName: 'Kategorie 3', currentCategoryProgress: 70 },
          ],
        },
      }).as('fetchThirdAudit');
  
      // Besuche die Seite mit dem ersten Audit
      cy.visit('http://localhost:5173/#/compare-audits/1');
    });
  
    it('loads data for the selected second audit', () => {
        // Warte, bis die Audit-Liste geladen ist
        cy.wait('@fetchAllAudits');
      
        // Öffne das Dropdown-Menü
        cy.get('.MuiAutocomplete-popupIndicator').should('exist').click();
      
        // Wähle eine Option aus
        cy.contains('Audit 2').click();
      
        // Warte, bis die Daten des zweiten Audits geladen werden
        cy.wait('@fetchSecondAudit');
      
        // Überprüfe, ob die AuditComparisonCard für Audit 2 gerendert wird
        cy.contains('Audit 2')
          .parents('.p-4.bg-gray-100.rounded.shadow') // Container der AuditComparisonCard
          .within(() => {
            // Überprüfe den Titel der Karte
            cy.get('h2').should('have.text', 'Audit 2');
      
            // Überprüfe den Fortschrittsbalken
            cy.get('[role="progressbar"]')
              .should('exist')
              .and('have.attr', 'aria-valuenow', '60'); // Gesamtfortschritt überprüfen
      
            // Überprüfe das RadarChart
            cy.get('[data-cy="RadarChart"]').should('exist').within(() => {
              cy.get('canvas').should('exist').and('be.visible');
            });
          });
      
        // Validierung der API-Daten
        cy.wait('@fetchSecondAudit').then((interception) => {
          expect(interception.response.body.categoryProgress).to.deep.equal([
            { categoryName: 'Kategorie 1', currentCategoryProgress: 50 },
            { categoryName: 'Kategorie 2', currentCategoryProgress: 70 },
            { categoryName: 'Kategorie 3', currentCategoryProgress: 40 },
          ]);
        });
      });
        
      
  });
  