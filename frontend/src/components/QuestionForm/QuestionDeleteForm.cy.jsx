import React from 'react'
import {QuestionDeleteForm} from "./QuestionDeleteForm.jsx";

describe('<QuestionDeleteForm />', () => {
  it('renders', () => {
    // see: https://on.cypress.io/mounting-react
    cy.mount(<QuestionDeleteForm question={{name: "", category: ""}}/>)
  })

  it('can render question name', ()=>{
    cy.mount(<QuestionDeleteForm question={{name: "Hello", category: ""}}/>)
    cy.get('[data-cy="question-name"] > div > textarea').should('have.question', 'Hello')
  })

  it('can render category name', ()=>{
    cy.mount(<QuestionDeleteForm question={{name: "", category: "Hey"}}/>)
    cy.get('[data-cy="category-name"] > div > textarea').should('have.question', 'Hey')
  })

  it('can submit form', ()=>{
    const onSubmit = cy.stub()
    cy.mount(<QuestionDeleteForm question={{name: "Hello", category: "Hey"}} onSubmit={onSubmit} />)
    cy.get('[data-cy="question-form-submit-button"]').click()
    cy.wrap(onSubmit).should('have.been.calledOnce')
  })

})