import React from 'react'
import {QuestionDeleteForm} from "./QuestionDeleteForm.jsx";

describe('<QuestionDeleteForm />', () => {
  it('renders', () => {
    // see: https://on.cypress.io/mounting-react
    cy.mount(<QuestionDeleteForm question={{name: "", category: ""}}/>)
  })

  it('can render question and category name', ()=>{
    cy.mount(<QuestionDeleteForm question={{name: "Hello", category: {name: "Kat"}}}/>)
    cy.get('[data-cy=question-form] > p:nth-of-type(1)').should('have.text', 'Frage: Hello')
    cy.get('[data-cy=question-form] > p:nth-of-type(2)').should('have.text', 'Kategorie: Kat')
  })


  it('can submit form', ()=>{
    const onSubmit = cy.stub()
    cy.mount(<QuestionDeleteForm question={{name: "Hello", category: "Hey"}} onSubmit={onSubmit} />)
    cy.get('[data-cy="question-form-submit-button"]').click()
    cy.wrap(onSubmit).should('have.been.calledOnce')
  })

})