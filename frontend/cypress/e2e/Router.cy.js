describe('Router', () => {

  beforeEach(() => {
    cy.visit('http://localhost:5173/');
  });

  it('Visits sides', () => {

    cy.contains('Dashboard').click()

    cy.contains('New Audit').click()

    cy.contains('Perform Audit').click()

    cy.contains('Evaluation').click()
  })

  it ('Route: Dashboard -> Audit 1 -> Perform Audit', () => {
    cy.intercept('GET', '/api/v1/audits', (req) => {
      req.reply({
          statusCode: 200,
          body: [{ id: 1, name: 'Audit 1' }, { id: 2, name: 'Audit 2' }],
      });
  }).as('getAudits');
  cy.intercept('GET', '/api/v1/audits/1/ratings', {
    statusCode: 200,
    body: [
      { id: 1, question: "Frage 1", points: null, comment: '', na: null },
      { id: 2, question: "Frage 2", points: null, comment: '', na: null },
      { id: 3, question: "Frage 3", points: null, comment: '', na: null }
    ]
  }).as('questions');
    cy.get('[data-cy="data-buttons"]').first().click();
    cy.url().should('include', '/performAudit');
    cy.get('[data-cy="question_text"]').first().should('contain.text', 'Frage 1');
  }
  )
})