import React from 'react'
import { MyExampleComponent } from './MyExampleComponent'

describe('<MyExampleComponent />', () => {
  it('renders', () => {
    // see: https://on.cypress.io/mounting-react
    cy.mount(<MyExampleComponent title="Title" content="Content" />)
  })
})