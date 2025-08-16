import React from 'react'
import {CategoryDeleteForm} from "./CategoryDeleteForm.jsx";

describe('<CategoryDeleteForm />', () => {
    it('renders', () => {
        // see: https://on.cypress.io/mounting-react
        cy.mount(<CategoryDeleteForm category={{name: ""}} />)
    })

    it('can render category name', ()=>{
        cy.mount(<CategoryDeleteForm category={{name: "Hello"}} />)
        cy.get('[data-cy="category-form"] > p').should('have.text', 'Kategorie: Hello')
    })

    it('can submit form', ()=>{
        const onSubmit = cy.stub()
        cy.mount(<CategoryDeleteForm category={{name: "Hello"}} onSubmit={onSubmit} />)
        cy.get('[data-cy="category-form-submit-button"]').click()
        cy.wrap(onSubmit).should('have.been.calledOnce')
    });

})