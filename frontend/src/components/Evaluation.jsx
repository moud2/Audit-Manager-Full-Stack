// eslint-disable-next-line no-unused-vars
import React from 'react';

function Evaluation() {

    const evaluationData = {
        categories: [
            {
                name: "Category 1",
                questions: [
                    {question: "Question 1.1", rating: 4},
                    {question: "Question 1.2", rating: 1},

                ]
            },
            {
                name: "Category 2",
                questions: [
                    {question: "Question 2.1", rating: 5},
                    {question: "Question 2.2", rating: 2},

                ]
            }
        ]
    };


    return (


        <h2>Bewertung</h2>


    );
}

export default Evaluation;