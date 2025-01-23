import React from 'react';
import LayoutDefault from '../layouts/LayoutDefault';
import { layouts } from 'chart.js';

describe('Check if the Box with Header is present', () => {
    it('renders', () => {
        // see: https://on.cypress.io/mounting-react
        cy.mount(<LayoutDefault title="LayoutDefault" content="Header/Footer" />)
    })

    it('should verify that the Box is rendered and visible', () => {
        // Besuche die URL der Anwendung
        cy.visit('http://localhost:5173/');
        
        // Überprüfen, ob die Box mit der Header-Klasse vorhanden ist
        cy.get('.h-[74px]').should('exist').and('be.visible');
        // Überprüfen, ob die Box mit der Footer-Klasse vorhanden ist
        cy.get('.h-[64px]').should('exist').and('be.visible');  // Stellt sicher, dass sie sichtbar ist.
    });
})