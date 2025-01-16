import React from "react";
import { AuditProgress } from "./AuditProgress.jsx";

describe('AuditProgress Component', () => {
    const progress = [
        {
            categoryId: 1,
            categoryName: 'Category 1',
            answeredQuestions: 3,
            totalQuestions: 5,
        },
        {
            categoryId: 2,
            categoryName: 'Category 2',
            answeredQuestions: 2,
            totalQuestions: 4,
        },
        {
            categoryId: 3,
            categoryName: 'Category 3',
            answeredQuestions: 1,
            totalQuestions: 5,
        },
    ];

    beforeEach(() => {
        cy.mount(<AuditProgress progress={progress}/>);
    });

    it('renders the correct category names', () => {
        progress.forEach((category) => {
            cy.contains('div', category.categoryName)
                .should('exist')
                .and('have.text', category.categoryName);
        });
    });

    it('renders the correct progress information for each category', () => {
        progress.forEach((category) => {
            const expectedProgress = `${category.answeredQuestions}/${category.totalQuestions}`;

            cy.contains('div', expectedProgress)
                .should('exist')
                .and('have.text', expectedProgress);
        });
    });

    it('renders clickable links for each category', () => {
        progress.forEach((category) => {
            cy.contains(category.categoryName)
                .closest('a') // Sucht das `Link`-Element
                .should('have.attr', 'data-to', `category-${category.categoryId}`); // Prüft das `data-to`-Attribut
        });
    });
});