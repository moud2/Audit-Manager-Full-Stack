describe('Evaluation Page Snapshot Tests', () => {
    beforeEach(() => {
        // Mock API response for progress data with realistic categories and questions
        cy.intercept('GET', 'http://localhost:8080/api/v1/audits/1/progress', {
            body: {
                overallProgress: 85,
                categoryProgress: {
                    "Customer Service": 70,
                    "Product Quality": 90,
                    "Compliance": 100,
                    "Safety Standards": 80,
                    "Employee Satisfaction": 60,
                },
                questionCountByRating: {
                    0: 2,  // Two questions with 0 points
                    1: 1,  // One question with 1 point
                    2: 3,  // Three questions with 2 points
                    3: 5,  // Five questions with 3 points
                    4: 2,  // Two questions with 4 points
                    5: 7,  // Seven questions with 5 points
                    na: 3  // Three questions marked as "not applicable"
                },
            },
        }).as('getProgress');

        // Visit the Evaluation page
        cy.visit('http://localhost:5173/#/evaluation/1');
        cy.wait('@getProgress');
    });

    it('should match snapshot for ProgressBar', () => {
        cy.get('[data-cy="ProgressBar"]').snapshot({ name: "ProgressBar" });
    });

    it('should match snapshot for CircularChart', () => {
        cy.get('[data-cy="CircularChart"]').snapshot({ name: "CircularChart" });
    });

    it('should match snapshot for BarChart', () => {
        cy.get('[data-cy="BarChart"]').snapshot({ name: "BarChart" });
    });
});
