// eslint-disable-next-line no-unused-vars
import React from 'react';
import {CircularProgressWithLabel, LinearProgressWithLabel} from '@mui/material';

function Evaluation() {

    let progress = 70;
    let amountQuestions = 25;
    let amountCategories = 5;

    let categorie1 = [3, 5, 2, 1, 0, 0, 4, 5, 3];


    let categorie2 = [2, 5, 5, 1, 1, 3, 4, 5, 3];
    let categorie3 = [0, 1, 2, 1, 0, 0, 2, 5, 3];
    let categorie4 = [5, 5, 2, 1, 5, 0, 2, 5, 4];
    let categorie5 = [3, 5, 2, 4, 0, 2, 4, 5, 3];

    return (
        <div>
            <div id="title">
                <h1 className="text-center text-4xl m-10">Dashboard</h1>
            </div>

            <div id="result">
                <LinearProgressWithLabel value={progress}/>
            </div>

            <div id="categories">
                <CircularProgressWithLabel value={progress}/>
            </div>

            <div id="result_per_question">

            </div>
        </div>

    )
}

export default Evaluation;