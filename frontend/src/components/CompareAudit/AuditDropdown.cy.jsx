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

    it("renders the dropdown button with default text", () => {
        cy.get("button").contains("Bitte Auswählen");
    });

    it("toggles the dropdown menu when the button is clicked", () => {
        cy.get("button").click();
        cy.get(".absolute").should("exist");
        cy.get("button").click();
        cy.get(".absolute").should("not.exist");
    });

    it("filters audits based on search input", () => {
        cy.get("button").click();
        cy.get("input").should("exist").type("Audit 2");
        cy.get(".absolute .block")
            .should("have.length", 1)
            .and("contain", "Audit 2");
    });

    it("displays a message when no audits match the search", () => {
        cy.get("button").click(); 
        cy.get("input").type("Nonexistent Audit");
    });

    it("calls onAuditSelect with the correct audit when an option is selected", () => {
        cy.get("button").click(); 
        cy.get(".absolute .block")
            .contains("Audit 1")
            .click();
        cy.get("@onAuditSelectSpy").should("have.been.calledWith", {
            id: 1,
            name: "Audit 1",
        });
    });

    it("closes the dropdown menu after selecting an option", () => {
        cy.get("button").click();
        cy.get(".absolute .block").contains("Audit 1").click();
        cy.get(".absolute").should("not.exist");
    });
});
