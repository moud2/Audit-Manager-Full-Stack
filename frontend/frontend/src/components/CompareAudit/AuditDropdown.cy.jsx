import React from "react";
import { AuditDropdown } from "./AuditDropdown";

describe("AuditDropdown Component", () => {
    const audits = [
        { id: 1, name: "Audit 1" },
        { id: 2, name: "Audit 2" },
        { id: 3, name: "Audit 3" },
    ];

    let onAuditSelect;

    beforeEach(() => {
        onAuditSelect = cy.spy().as("onAuditSelectSpy");
        cy.mount(<AuditDropdown audits={audits} onAuditSelect={onAuditSelect} />);
    });

    it("renders the dropdown with a label", () => {
        cy.get("label").contains("Zweites Audit auswählen").should("exist");
    });

    it("opens the dropdown menu when clicked", () => {
        cy.get("input").click(); // Öffnet das Dropdown
        cy.get(".MuiAutocomplete-popper").should("exist");
    });

    it("filters audits based on search input", () => {
        cy.get("input").click().type("Audit 2"); // Öffnet das Dropdown und filtert
        cy.get(".MuiAutocomplete-popper li")
            .should("have.length", 1)
            .and("contain", "Audit 2");
    });

    it("displays no options message when no match is found", () => {
        cy.get("input").click().type("Nonexistent Audit"); // Sucht nach nicht vorhandener Option
        cy.get(".MuiAutocomplete-popper").should("exist"); // Popper sollte angezeigt werden
        cy.get(".MuiAutocomplete-popper").contains("No options").should("exist"); // Standard-MUI-Text überprüfen
    });
    

    it("calls onAuditSelect with the correct audit when an option is selected", () => {
        cy.get("input").click();
        cy.get(".MuiAutocomplete-popper li")
            .contains("Audit 1")
            .click(); // Wählt die erste Option
        cy.get("@onAuditSelectSpy").should("have.been.calledWith", { id: 1, name: "Audit 1" });
    });

    it("closes the dropdown menu after selecting an option", () => {
        cy.get("input").click();
        cy.get(".MuiAutocomplete-popper li").contains("Audit 1").click(); // Wählt und schließt
        cy.get(".MuiAutocomplete-popper").should("not.exist");
    });
});
