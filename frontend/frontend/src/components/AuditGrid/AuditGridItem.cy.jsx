import React from 'react';
import { MemoryRouter } from 'react-router-dom';
import { AuditGridItem } from './AuditGridItem';

describe('<AuditGridItem />', () => {
  it('renders with customer and date information', () => {
    const audit = {
      id: '123',
      name: 'Sample Audit',
      customer: 'Test Company',
      createdAt: '2024-01-01',
    };

    cy.mount(
      <MemoryRouter>
        <AuditGridItem audit={audit} />
      </MemoryRouter>
    );

    // Prüft, ob der Text für den Firmennamen vorhanden ist
    cy.contains('Sample Audit').should('exist');
    // Prüft, ob der Text für den Firmennamen vorhanden ist
    cy.contains('Test Company').should('exist');
    // Prüft, ob das Erstellungsdatum korrekt formatiert ist
    cy.contains('01.01.2024').should('exist');
  });
});
