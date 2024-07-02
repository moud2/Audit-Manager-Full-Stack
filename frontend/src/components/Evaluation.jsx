import React, {useEffect, useState} from 'react';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import CircularProgress from '@mui/material/CircularProgress';
import {BarChart} from '@mui/x-charts/BarChart';
import LinearProgress from '@mui/material/LinearProgress';
import api from "../api.js";
import {useParams} from "react-router-dom";

function Evaluation() {
    const [responseData, setResponseData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [categoryProgress, setCategoryProgress] = useState({});
    const {auditId} = useParams();

    console.log(auditId);

    const [mainProgress, setMainProgress] = useState(0);
    const [pointsDistribution, setPointsDistribution] = useState(new Array(6).fill(0));

    useEffect(() => {
        api.get(`/v1/audits/${auditId}/ratings`)
            .then(response => {
                console.log(response);
                const data = response.data || [];
                setResponseData(data);
                setLoading(false);

                const totalQuestions = data.length;
                const maxScore = totalQuestions * 5;

                console.log("Total Questions:", totalQuestions);
                console.log("Max Score:", maxScore);

                const actualScore = data.reduce((acc, rating) => {
                    console.log("Rating Points:", rating.points);
                    return acc + (rating.points || 0);
                }, 0);

                console.log("Actual Score:", actualScore);

                const calculatedProgress = Math.round((actualScore / maxScore) * 100);
                console.log("Calculated Progress:", calculatedProgress);

                setMainProgress(calculatedProgress);

                // Berechnung des Fortschritts für jede Kategorie
                const categoryScores = {};
                data.forEach(rating => {
                    const categoryId = rating.category.id;
                    const categoryName = rating.category.name;

                    if (!categoryScores[categoryId]) {
                        categoryScores[categoryId] = {
                            name: categoryName,
                            totalPoints: 0,
                            maxPoints: 0,
                            questionCount: 0,
                        };
                    }

                    categoryScores[categoryId].totalPoints += (rating.points || 0);
                    categoryScores[categoryId].maxPoints += 5;
                    categoryScores[categoryId].questionCount += 1;
                });

                const calculatedCategoryProgress = {};
                Object.keys(categoryScores).forEach(categoryId => {
                    const category = categoryScores[categoryId];
                    calculatedCategoryProgress[categoryId] = {
                        name: category.name,
                        progress: Math.round((category.totalPoints / category.maxPoints) * 100)
                    };
                });

                setCategoryProgress(calculatedCategoryProgress);

                // Berechnung der Punkteverteilung
                const distribution = data.reduce((acc, rating) => {
                    const points = rating.points || 0;
                    acc[points] = (acc[points] || 0) + 1;
                    return acc;
                }, new Array(6).fill(0));

                console.log("Points Distribution:", distribution);
                setPointsDistribution(distribution);

            })
            .catch(err => {
                console.error('Error fetching data:', err);
                setError(err);
                setLoading(false);
            });
    }, [auditId]);

    if (loading) {
        return <p>Laden...</p>;
    }

    if (error) {
        return <p>Fehler: {error.message}</p>;
    }


    // Setup function progress bar
    function LinearProgressWithLabel(props) {
        return (
            <Box sx={{display: 'flex', alignItems: 'center'}}>
                <Box sx={{width: '100%', mr: 1}}>
                    <LinearProgress variant="determinate" value={mainProgress} {...props} />
                </Box>
                <Box sx={{minWidth: 35}}>
                    <Typography variant="body2" color="text.secondary">{`${Math.round(
                        props.value,
                    )}%`}</Typography>
                </Box>
            </Box>
        );
    }

    // Setup progress circle
    function CircularProgressWithLabel(props) {
        return (
            <Box sx={{position: 'relative', display: 'inline-flex'}}>
                <CircularProgress variant="determinate" {...props} size={80}/>
                <Box
                    sx={{
                        top: 0,
                        left: 0,
                        bottom: 0,
                        right: 0,
                        position: 'absolute',
                        display: 'flex',
                        alignItems: 'center',
                        justifyContent: 'center',
                    }}
                >
                    <Typography variant="caption" component="div" color="text.secondary">
                        {`${Math.round(props.value)}%`}
                    </Typography>
                </Box>
            </Box>
        );
    }

    const colors = ['#a50026', '#d73027', '#fdae61', '#d9ef8b', '#66bd63', '#006837'];

    return (
        <div>
            <div id="title">
                <h1 className="text-center text-4xl m-6">Evaluation</h1>
            </div>

            <div id="result" className={"flex flex-col justify-center items-center h-20"}>
                <Box className={"text-center"} sx={{width: '80%'}}>
                    <LinearProgressWithLabel value={mainProgress}/>
                </Box>
                <p className={"text-center text-xl"}>Gesamtfortschritt</p>
            </div>

            <div id="categories" className={"flex flex-row justify-center items-center"}>
                {Object.keys(categoryProgress).map(categoryId => (
                    <div key={categoryId} className={"ml-6 m-8 text-center"}>
                        <CircularProgressWithLabel value={categoryProgress[categoryId].progress} className={"size-60"}/>
                        <p className={"text-center"}>{categoryProgress[categoryId].name}</p>
                    </div>
                ))}
            </div>

            <div id="result_per_question" className={"flex flex-row justify-center items-center"}>
                <BarChart
                    series={[
                        {data: pointsDistribution},
                    ]}
                    width={1200}
                    height={350}
                    xAxis={[
                        {
                            scaleType: 'band',
                            data: [0, 1, 2, 3, 4, 5],
                            colorMap: {
                                type: 'ordinal',
                                values: [0, 1, 2, 3, 4, 5],
                                colors: colors,
                                unknownColor: "#050505",
                            },
                            label: 'erreichte Punkte',
                        },
                    ]}
                    yAxis={[
                        {
                            label: 'Anzahl Fragen',
                        },
                    ]}
                />
            </div>
        </div>
    );
}

export default Evaluation;
