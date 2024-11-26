import React from 'react';
import { AuditDropdown } from './AuditDropdown';

describe('AuditDropdown Component', () => {
  const audits = [
    { id: 1, name: 'Audit A' },
    { id: 2, name: 'Audit B' },
    { id: 3, name: 'Audit C' },
  ];

  let onAuditSelect;

  beforeEach(() => {
    onAuditSelect = cy.spy().as('onAuditSelectSpy');
    cy.mount(<AuditDropdown audits={audits} onAuditSelect={onAuditSelect} />);
  });

  it('renders the dropdown with correct options', () => {
    // Überprüfen, ob alle Optionen vorhanden sind
    cy.get('#auditDropdown option').should('have.length', audits.length + 1); // +1 für "Bitte wählen"
    cy.get('#auditDropdown option').first().should('have.value', '').and('contain', 'Bitte wählen');
  });

  it('calls onAuditSelect with the correct audit when an option is selected', () => {
    // Audit B auswählen
    cy.get('#auditDropdown').select('Audit B');
    cy.get('@onAuditSelectSpy').should('have.been.calledOnceWith', audits[1]);
  });

  it('calls onAuditSelect with null for the default option', () => {
    cy.mount(<AuditDropdown audits={[]} onAuditSelect={cy.spy()} />);
    cy.get('#auditDropdown').select('Bitte wählen');
  });

  it('handles multiple selections correctly', () => {
    // Mehrfachauswahl testen
    cy.get('#auditDropdown').select('Audit C');
    cy.get('@onAuditSelectSpy').should('have.been.calledWith', audits[2]);

    cy.get('#auditDropdown').select('Bitte wählen');
    cy.get('@onAuditSelectSpy').should('have.been.calledWith', null);

    cy.get('#auditDropdown').select('Audit A');
    cy.get('@onAuditSelectSpy').should('have.been.calledWith', audits[0]);
  });
});
