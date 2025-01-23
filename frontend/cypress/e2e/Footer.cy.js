describe("<Footer />", () => {
    beforeEach(() => {
      // Besuche die Startseite vor jedem Test
      cy.visit("http://localhost:5173/#");
    });
  
    it("Visits InSight (Localhost)", () => {
      // Überprüfen, ob die URL korrekt geladen wurde
      cy.url().should("include", "localhost:5173");
    });

    it('Checks for the Link "InSight"', () => {
      // Überprüfen, ob der Link "InSight" sichtbar ist
      cy.contains("a", "InSight").should("be.visible");
    });
  
    it("Has correct footer styles", () => {
      // Überprüfen, ob der Footer korrekt positioniert ist
      cy.get("footer")
        .should("have.class", "fixed")
        .and("have.css", "bottom", "0px")
        .and("have.css", "background-color", "rgb(255, 255, 255)"); // Weißer Hintergrund
    });
  });
  