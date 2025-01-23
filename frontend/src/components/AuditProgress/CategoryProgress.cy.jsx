import React from 'react';
import {CategoryProgress} from "./CategoryProgress.jsx";

describe('CategoryProgress Component', () => {
    const name = "testCategory";

    it('renders component correctly: 1/2', () => {
        cy.mount(<CategoryProgress
            name={name}
            answeredQuestions={1}
            totalQuestions={2}
        />);

        cy.get('[data-cy="category-name"]').should("have.text", name);
        cy.get('[data-cy="answered-out-of-total"]').should("have.text", "1/2");
        cy.get('[data-cy="category-progress-bar"]').should('have.attr', 'style').and('contain', `width: 50%`);
    });

    it('renders component correctly: 0/10', () => {
        cy.mount(<CategoryProgress
            name={name}
            answeredQuestions={0}
            totalQuestions={10}
        />);

        cy.get('[data-cy="category-name"]').should("have.text", name);
        cy.get('[data-cy="answered-out-of-total"]').should("have.text", "0/10");
        cy.get('[data-cy="category-progress-bar"]').should('have.attr', 'style').and('contain', `width: 0%`);
    });


    it('renders component correctly: 15/15', () => {
        cy.mount(<CategoryProgress
            name={"Very very long and super important category name"}
            answeredQuestions={15}
            totalQuestions={15}
        />);

        cy.get('[data-cy="category-name"]').should("have.text", "Very very long and super important category name");
        cy.get('[data-cy="answered-out-of-total"]').should("have.text", "15/15");
        cy.get('[data-cy="category-progress-bar"]').should('have.attr', 'style').and('contain', `width: 100%`);
    });

    it('renders component correctly: 8/19', () => {
        cy.mount(<CategoryProgress
            name={""}
            answeredQuestions={8}
            totalQuestions={19}
        />);

        const expectedWidth= 42.1053;

        cy.get('[data-cy="category-name"]').should("have.text", "");
        cy.get('[data-cy="answered-out-of-total"]').should("have.text", "8/19");
        cy.get('[data-cy="category-progress-bar"]').should('have.attr', 'style').and('contain', `width: ${expectedWidth}%`);
    });
})