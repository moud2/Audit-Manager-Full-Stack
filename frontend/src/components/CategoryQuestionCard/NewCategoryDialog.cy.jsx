import React from 'react'
import NewCategoryDialog from "./NewCategoryDialog.jsx";

describe('<NewCategoryDialog />', () => {
    it('renders', () => {
        // see: https://on.cypress.io/mounting-react
        cy.mount(<NewCategoryDialog  />)
    })

    it('should have title', ()=>{
        cy.mount(<NewCategoryDialog open={true}/>)
        cy.get('[data-cy="base-dialog-title"]').should('have.text', "Kategorie anlegen")
    })
})