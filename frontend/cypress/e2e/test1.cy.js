describe('First test', () => {
  it('Does not do much', () => {
    cy.visit('http://localhost:5173/')
    cy.get('footer')
    cy.get('.text-sm') 
  })

 
  

})

