import React from 'react'
import DeleteQuestionDialog from "./DeleteQuestionDialog.jsx";

describe('<DeleteQuestionDialog />', () => {
    it('renders', () => {
        // see: https://on.cypress.io/mounting-react
        cy.mount(<DeleteQuestionDialog/>)
    })

    it('should have title', () => {
        cy.mount(<DeleteQuestionDialog open={true}/>)
        cy.get('[data-cy="base-dialog-title"]').should('have.text', "Frage löschen")
    })

    it('should call onSubmit with the correct object when form is submitted', () => {
          const onSubmit = cy.stub()
        cy.mount(<DeleteQuestionDialog open={true} onSubmit={onSubmit}/>)
        cy.get('[data-cy="question-name"]').type('Question Name')
        cy.get('[data-cy="question-form-submit-button"]').click()
        cy.wrap(onSubmit).should('have.been.calledOnce')
        cy.wrap(onSubmit).should('have.been.calledWith', {name: 'Question Name'})   })
})