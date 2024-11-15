import 'cypress-image-snapshot/command';

describe('Evaluation Page Snapshot Tests', () => {
    beforeEach(() => {
        // Mock API response for progress data
        cy.intercept('GET', 'http://localhost:8080/api/v1/audits/1/progress', {
            body: {
                overallProgress: 75,
                categoryProgress: {
                    baum: 50,
                    'nicht baum': 100,
                },
                questionCountByRating: {
                    0: 1,
                    3: 1,
                    5: 1,
                },
            },
        }).as('getProgress');

        // Visit the Evaluation page
        cy.visit('http://localhost:5173/#/evaluation/1');
        cy.wait('@getProgress');
    });

    it('should match snapshot for ProgressBar', () => {
        cy.get('[data-cy="ProgressBar"]').matchImageSnapshot();
    });

    it('should match snapshot for CircularChart', () => {
        cy.get('[data-cy="CircularChart"]').matchImageSnapshot();
    });

    it('should match snapshot for BarChart', () => {
        cy.get('[data-cy="BarChart"]').matchImageSnapshot();
    });
});
