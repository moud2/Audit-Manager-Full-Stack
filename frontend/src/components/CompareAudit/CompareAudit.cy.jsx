import React from "react";
import { MemoryRouter } from "react-router-dom";
import { Evaluation } from "../../pages/Evaluation";

describe("Evaluation Component", () => {
  it("renders the CompareAudit button with the correct text", () => {
    cy.mount(
      <MemoryRouter>
        <Evaluation />
      </MemoryRouter>
    );
    cy.get("button").should("contain", "Audit vergleichen"); 
  });
});
