import React from 'react';
import { AuditDropdown } from './AuditDropdown';

describe('AuditDropdown Component', () => {
  const audits = [];

  let onAuditSelect;

  beforeEach(() => {
    onAuditSelect = cy.spy().as('onAuditSelectSpy');
    cy.mount(<AuditDropdown audits={audits} onAuditSelect={onAuditSelect} />);
  });

  it('renders the dropdown with the correct number of options', () => {
    cy.get('#auditDropdown option').should('have.length', audits.length + 1); 
    cy.get('#auditDropdown option').first().should('have.value', '').and('contain', 'Bitte wählen');
  });

  audits.forEach((audit, index) => {
    cy.get('#auditDropdown option').eq(index + 1)
      .should('have.value', audit.id.toString())
      .and('contain', audit.name);
  });


  it('calls onAuditSelect with the correct audit when an option is selected', () => {
    audits.forEach((audit, index) => {
      cy.get('#auditDropdown').select(audit.name);
      cy.get('@onAuditSelectSpy').should('have.been.calledWith', audit);
    });
  });

  it('calls onAuditSelect with null for the default option', () => {
    cy.get('#auditDropdown').select('Bitte wählen');
  });

  it('handles multiple selections correctly', () => {
    audits.forEach((audit) => {
      cy.get('#auditDropdown').select(audit.name);
      cy.get('@onAuditSelectSpy').should('have.been.calledWith', audit);

      cy.get('#auditDropdown').select('Bitte wählen');
      cy.get('@onAuditSelectSpy').should('have.been.calledWith', null);
    });
  });
});
