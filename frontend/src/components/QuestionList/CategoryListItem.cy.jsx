import React from 'react';
import { CategoryListItem } from './CategoryListItem.jsx';

describe('CategoryList component tests', () => {
    const labels = [0, 1, 2, 3, 4, 5, "N/A"];
    const exampleCategory = [
        {
            name: 'VPN',
            id: 1,
            questions: [
                { id: '1', question: 'Frage 1?', points: 3, nA: false, comment: 'Ok' },
                { id: '2', question: 'Frage 2?', points: null, nA: true, comment: '' },
            ]
        }
    ];

    it('', () => {
        const onChangeStub = cy.stub().as('onChangeStub');
        cy.mount(
            <CategoryListItem
                questions={exampleCategory}
                options={labels}
                onChange={onChangeStub}
            />
        );

    });

    it('', () => {

    });

    it('', () => {

    });
});