describe("<Header />", () => {
  beforeEach(() => {
    // Besuche die Startseite vor jedem Test
    cy.visit("http://localhost:5173/#");
  });

  it("Visits InSight (Localhost)", () => {
    // Überprüfen, ob die URL korrekt geladen wurde
    cy.url().should("include", "localhost:5173");
  });

  it('Checks for the heading "InSight"', () => {
    // Überprüfen, ob die Überschrift "InSight" sichtbar ist
    cy.contains("h1", "InSight").should("be.visible");
  });

  it("Renders the logo image", () => {
    // Überprüfen, ob das Logo korrekt angezeigt wird
    cy.get('img[alt="InSight Logo"]')
      .should("be.visible") // Sichtbar sein
      .and("have.attr", "src") // Attribut 'src' haben
      .and("include", "logo-insight.png"); // 'src' enthält den Bildnamen

    // Überprüfen, ob das alt-Attribut korrekt gesetzt ist
    cy.get('img[alt="InSight Logo"]').should("have.attr", "alt", "InSight Logo");
  });

  it("Has correct header styles", () => {
    // Überprüfen, ob der Header korrekt positioniert ist
    cy.get("header")
      .should("have.class", "fixed")
      .and("have.css", "top", "0px")
      .and("have.css", "background-color", "rgb(255, 255, 255)"); // Weißer Hintergrund
  });
});
