import React from 'react';
import { NewAudit } from '../pages/NewAudit';

describe('<NewAudit />', () => {
  it('renders and validates input fields for audit and customer names', () => {
    
    cy.mount(<NewAudit />);

    
    cy.get('input[placeholder="Audit Name"]').should('exist');
    cy.get('input[placeholder="Firmenname"]').should('exist');


    cy.get('button').contains('Audit erstellen').click();
    cy.on('window:alert', (text) => {
      expect(text).to.equal('Bitte geben Sie sowohl einen Audit-Namen als auch einen Firmennamen ein.');
    });

    cy.get('input[placeholder="Audit Name"]').type('Test Audit');
    cy.get('button').contains('Audit erstellen').click();
    cy.on('window:alert', (text) => {
      expect(text).to.equal('Bitte geben Sie sowohl einen Audit-Namen als auch einen Firmennamen ein.');
    });

    cy.get('input[placeholder="Firmenname"]').type('Test Firma');
    cy.get('button').contains('Audit erstellen').click();
    cy.on('window:alert', (text) => {
      expect(text).not.to.equal('Bitte geben Sie sowohl einen Audit-Namen als auch einen Firmennamen ein.');
    });
  });
});