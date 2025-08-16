import React from "react";
import { MemoryRouter } from "react-router-dom";
import { SidebarProvider } from "../layouts/SidebarContext";
import { NewAudit } from "../pages/NewAudit";

describe("<NewAudit />", () => {
  it("renders and validates input fields for audit and customer names", () => {
    // Mounting the component with MemoryRouter and SidebarProvider
    cy.mount(
      <MemoryRouter>
        <SidebarProvider>
          <NewAudit />
        </SidebarProvider>
      </MemoryRouter>
    );

    // Sicherstellen, dass die Labels existieren
    cy.get("label").contains("Audit Name", { timeout: 10000 }).should("exist");
    cy.get("label").contains("Firmenname", { timeout: 10000 }).should("exist");

    // Sicherstellen, dass der Button sichtbar ist
    cy.get("button").contains("Audit erstellen", { timeout: 10000 }).should("exist");

    // Klick auf den Button erzwingen, falls er nicht sichtbar ist
    cy.get("button").contains("Audit erstellen").click({ force: true });

    // Sicherstellen, dass eine Fehlermeldung angezeigt wird
    cy.get("[role='alert']").should(
      "contain.text",
      "Bitte geben Sie sowohl einen Audit-Namen als auch einen Firmennamen ein."
    );
  });
});
