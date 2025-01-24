describe("NewAudit Page Tests", () => {
  // Test when backend is available
  context("Backend is available", () => {
    beforeEach(() => {
      cy.intercept("GET", "/api/v1/categories", {
        statusCode: 200,
        body: [
          { id: 1, name: "Category 1" },
          { id: 2, name: "Category 2" },
        ],
      }).as("getCategories");

      cy.visit("http://localhost:5173/#/new-audit");
      cy.wait("@getCategories");
    });

    it("Categories are fetched and displayed correctly", () => {
      cy.get(".cursor-grab").should("have.length", 2);
      cy.get(".cursor-grab").first().should("contain", "Category 1");
      cy.get(".cursor-grab").last().should("contain", "Category 2");
    });

    it("Inputs for Audit Name und Firmenname are available", () => {
      cy.get('input[type="text"]').should("be.visible");
      cy.get('label').contains('Audit Name').should('exist').and('be.visible');
      cy.get('input[type="text"]').should("be.visible");
      cy.get('label').contains('Firmenname').should('exist').and('be.visible');
    });


  }); 

 

  // Test when backend is not available
  context("Backend is not available", () => {
    beforeEach(() => {
      cy.intercept("GET", "/api/v1/categories", {
        statusCode: 500,
        body: { error: "Backend not available" },
      }).as("getCategoriesError");

      cy.visit("http://localhost:5173/#/new-audit");
      cy.wait("@getCategoriesError");
    });

    it("should display an error message when backend is not available", () => {
      cy.contains('Fehler').should('be.visible');
    });
  });
  
});

