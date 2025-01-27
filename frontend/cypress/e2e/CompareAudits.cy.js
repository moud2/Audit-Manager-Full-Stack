describe('CompareAudits Page', () => {
  beforeEach(() => {
    // Intercept für die Audit-Liste
    cy.intercept('GET', '/api/v1/audits', {
      statusCode: 200,
      body: [
        { id: 1, name: 'Audit 1', customer: 'Kunde A', createdAt: '2023-12-01' },
        { id: 2, name: 'Audit 2', customer: 'Kunde B', createdAt: '2023-12-02' },
        { id: 3, name: 'Audit 3', customer: 'Kunde C', createdAt: '2023-12-03' },
      ],
    }).as('fetchAllAudits');

    // Intercept für den Fortschritt von Audit 1
    cy.intercept('GET', '/api/v1/audits/1/progress', {
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

    // Intercept für den Fortschritt von Audit 2
    cy.intercept('GET', '/api/v1/audits/2/progress', {
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

    // Seite aufrufen
    cy.visit('http://localhost:5173/#/compare-audits/1');
  });

  it('loads the audit list', () => {
    cy.wait('@fetchAllAudits');
    cy.get('.MuiAutocomplete-popupIndicator').should('exist').click();
    cy.contains('Audit 2').should('exist');
  });

  it('selects and loads the second audit data', () => {
    cy.wait('@fetchAllAudits');
    cy.get('.MuiAutocomplete-popupIndicator').click();
    cy.contains('Audit 2').click();
    cy.wait('@fetchSecondAudit');
    cy.contains('Audit 2').should('exist');
  });

  it('renders the AuditComparisonCard for the second audit', () => {
    cy.wait('@fetchAllAudits');
    cy.get('.MuiAutocomplete-popupIndicator').click();
    cy.contains('Audit 2').click();
    cy.wait('@fetchSecondAudit');
    cy.contains('Audit 2')
      .parents('.p-4.bg-gray-100.rounded.shadow')
      .within(() => {
        cy.get('h2').should('have.text', 'Audit 2');
        cy.get('[role="progressbar"]')
          .should('exist')
          .and('have.attr', 'aria-valuenow', '60');
      });
  });

  it('validates the radar chart for the second audit', () => {
    cy.wait('@fetchAllAudits');
    cy.get('.MuiAutocomplete-popupIndicator').click();
    cy.contains('Audit 2').click();
    cy.wait('@fetchSecondAudit');
    cy.contains('Audit 2')
      .parents('.p-4.bg-gray-100.rounded.shadow')
      .within(() => {
        cy.get('[data-cy="RadarChart"]').should('exist').within(() => {
          cy.get('canvas').should('exist').and('be.visible');
        });
      });
  });

  it('validates API data for the second audit', () => {
    cy.wait('@fetchAllAudits');
    cy.get('.MuiAutocomplete-popupIndicator').click();
    cy.contains('Audit 2').click();
    cy.wait('@fetchSecondAudit').then((interception) => {
      expect(interception.response.body.categoryProgress).to.deep.equal([
        { categoryName: 'Kategorie 1', currentCategoryProgress: 50 },
        { categoryName: 'Kategorie 2', currentCategoryProgress: 70 },
        { categoryName: 'Kategorie 3', currentCategoryProgress: 40 },
      ]);
    });
  });
});
