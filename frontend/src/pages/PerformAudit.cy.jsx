// import React from 'react';
// import { PerformAudit } from './PerformAudit.jsx';
//
//
// describe('PerformAudit page tests', () => {
//
//
//     it('', () => {
//
//     });
//
//     it('', () => {
//
//     });
//
//     it('', () => {
//
//     });
// });


export const interceptGET = (points, na, comment) => {
    cy.intercept('GET', '/api/v1/audits/1/ratings', {
        statusCode: 200,
        body: [
            { id: 1, question: "Frage 1", points: points, comment: comment, na: na },
        ]
    }).as('questions');
};

// export const interceptPATCH = (points, na, comment) => {
//     cy.intercept('PATCH', '/v1/ratings/0', {
//         statusCode: 200,
//         body: [{ id: 1, question: "Frage 1", points: points, comment: comment, na: na}]
//     });
// };

describe('PerformAudit Component', () => {
    beforeEach(() => {
        interceptGET(null, null, '');
        cy.visit('http://localhost:5173/#/performAudit/1');
    });

    it('should allow typing in all textareas', () => {
        interceptPATCH(null, null, 'Test');
        cy.get('[data-cy="commentTextarea"]').first().click().type('Test');
        cy.get('[data-cy="commentTextarea"]').first().should('have.value', 'Test');
    });

});