import React, { useContext } from "react";
import { SidebarProvider, default as SidebarContext } from "./SidebarContext";

describe("SidebarContext", () => {
  const TestComponent = () => {
    const { open, toggleSidebar, openSidebar, closeSidebar } = useContext(SidebarContext);

    return (
      <div>
        <p data-cy="sidebar-state">{open ? "open" : "closed"}</p>
        <button data-cy="toggle-button" onClick={toggleSidebar}>Toggle</button>
        <button data-cy="open-button" onClick={openSidebar}>Open</button>
        <button data-cy="close-button" onClick={closeSidebar}>Close</button>
      </div>
    );
  };

  it("should toggle the sidebar state", () => {
    cy.mount(
      <SidebarProvider>
        <TestComponent />
      </SidebarProvider>
    );

    cy.get('[data-cy="sidebar-state"]').should("have.text", "closed");
    cy.get('[data-cy="toggle-button"]').click();
    cy.get('[data-cy="sidebar-state"]').should("have.text", "open");
    cy.get('[data-cy="toggle-button"]').click();
    cy.get('[data-cy="sidebar-state"]').should("have.text", "closed");
  });

  it("should open the sidebar", () => {
    cy.mount(
      <SidebarProvider>
        <TestComponent />
      </SidebarProvider>
    );

    cy.get('[data-cy="sidebar-state"]').should("have.text", "closed");
    cy.get('[data-cy="open-button"]').click();
    cy.get('[data-cy="sidebar-state"]').should("have.text", "open");
  });

  it("should close the sidebar", () => {
    cy.mount(
      <SidebarProvider>
        <TestComponent />
      </SidebarProvider>
    );

    cy.get('[data-cy="sidebar-state"]').should("have.text", "closed");
    cy.get('[data-cy="open-button"]').click();
    cy.get('[data-cy="sidebar-state"]').should("have.text", "open");
    cy.get('[data-cy="close-button"]').click();
    cy.get('[data-cy="sidebar-state"]').should("have.text", "closed");
  });
});
