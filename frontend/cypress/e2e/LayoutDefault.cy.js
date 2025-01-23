import React from 'react';
import LayoutDefault from '../layouts/LayoutDefault';

describe('LayoutDefault Page Test', () => {
  
    it('should verify that the Header and Footer is rendered and visible', () => {
        cy.mount(<LayoutDefault />);

        // Überprüfen, ob die Header-Box vorhanden und sichtbar ist
        cy.get('header') // Passe diesen Selektor an die tatsächliche Struktur deines Headers an
            .should('exist')
            .and('be.visible');

        // Überprüfen, ob die Footer-Box vorhanden und sichtbar ist
        cy.get('footer') // Passe diesen Selektor an die tatsächliche Struktur deines Footers an
            .should('exist')
            .and('be.visible');
    });
  });
  