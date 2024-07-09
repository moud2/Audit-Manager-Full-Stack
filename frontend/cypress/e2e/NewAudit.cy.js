describe("NewAudit Component Tests", () => {
  beforeEach(() => {
    cy.visit("http://localhost:5173/newAudit");
  });

  it("should have a search input field", () => {
    cy.get('input[type="search"]').should("be.visible");
  });

  it("should have a visible Next button", () => {
    cy.get("button").contains("Next").should("be.visible");
  });

  it("Displays loading state, fetches, and renders categories", () => {
    cy.intercept("GET", "http://localhost:8080/api/v1/categories", {
      statusCode: 200,
      body: [
        { id: 1, name: "Server Administration" },
        { id: 2, name: "Firewall" },
        { id: 3, name: "Netzwerk" },
        { id: 4, name: "Antivirus" },
        { id: 5, name: "VPN" },
        { id: 6, name: "Monitoring" },
        { id: 7, name: "Email" },
        { id: 8, name: "Secure Browsing" },
        { id: 9, name: "Client" },
        { id: 10, name: "Patch-Management" },
        { id: 11, name: "Schwachstellen-Management" },
        { id: 12, name: "Verschlüsselung" },
        { id: 13, name: "Zertifikate und PKI" },
        { id: 14, name: "Mobile Device Management" },
        { id: 15, name: "Backup" },
        { id: 16, name: "Privilege Access Management (PAM)" },
        { id: 17, name: "Identity, Passwörter und Secure Logon" },
        { id: 18, name: "Nutzung von Clouddiensten" },
        { id: 19, name: "Konzepte und Richtlinien" },
        { id: 20, name: "IAM" },
        { id: 21, name: "Digitale Signatur" },
      ],
    }).as("getCategories");

    cy.contains("Loading...").should("be.visible");
    cy.contains("Loading...").should("not.exist");
  });

  it("Handles API error gracefully", () => {
    cy.intercept("GET", "/api/v1/categories", { statusCode: 500 }).as(
      "getCategoriesError",
    );

    cy.contains("Loading...").should("be.visible");
    cy.contains("Fehler:").should("be.visible");
  });

  it("should render the cards in the correct columns", () => {
    cy.contains("Verfügbare Kategorien").parent().should("be.visible");
    cy.contains("Ausgewählte Kategorien").parent().should("be.visible");
  });

  it("should render the columns with correct titles", () => {
    cy.contains("Verfügbare Kategorien").should("be.visible");
    cy.contains("Ausgewählte Kategorien").should("be.visible");
  });
});
