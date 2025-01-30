import React from 'react'
import DeleteCategoryDialog from "./DeleteCategoryDialog.jsx";

describe('<DeleteCategoryDialog />', () => {
    it('renders', () => {
        // see: https://on.cypress.io/mounting-react
        cy.mount(<DeleteCategoryDialog/>)
    })

    it('should have title', () => {
        cy.mount(<DeleteCategoryDialog open={true}/>)
        cy.get('[data-cy="base-dialog-title"]').should('have.text', "Kategorie löschen")
    })

    it('should call onSubmit with the correct object when form is submitted', () => {
      const onSubmit = cy.stub()
        cy.mount(<DeleteCategoryDialog open={true} onSubmit={onSubmit} />)
        cy.get('[data-cy="category-form-submit-button"]').click()
        cy.wrap(onSubmit).should('have.been.calledOnce')
    })
})