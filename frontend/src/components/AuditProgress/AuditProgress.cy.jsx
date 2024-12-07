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

    it('renders the correct category names and progress information', () => {
        cy.mount(<AuditProgress progress={progress}/>);

        progress.forEach((category) => {

        });
    });
});