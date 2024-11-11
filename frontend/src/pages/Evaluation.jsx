import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { LayoutDefault } from "../layouts/LayoutDefault.jsx";
import LinearProgressWithLabel from '../components/Charts/ProgressBar.jsx';
import CircularProgressWithLabel from '../components/Charts/CircularProgress.jsx';
import CustomBarChart from '../components/Charts/BarChart.jsx';
import api from '../api';

/**
 * Evaluation component that fetches audit data and renders charts for overall progress, category progress, and question ratings.
 * @component
 */
export function Evaluation() {

    const { auditId } = useParams();

    // State variables for storing the evaluation data from the backend
    const [overallProgress, setOverallProgress] = useState(0);
    const [categoryProgress, setCategoryProgress] = useState([]);
    const [questionCountByRating, setQuestionCountByRating] = useState([]);

    const colors = ['#a50026', '#d73027', '#fdae61', '#d9ef8b', '#66bd63', '#006837'];

    // Fetching data from the backend API
    useEffect(() => {
        api.get(`/v1/audits/${auditId}/progress`)
            .then(response => {
                setOverallProgress(response.data.overallProgress);
                setCategoryProgress(Object.entries(response.data.categoryProgress || {}).map(([name, progress]) => ({
                    name,
                    progress
                })));
                setQuestionCountByRating(Object.entries(response.data.questionCountByRating || {}).map(([rating, count]) => ({
                    rating: parseInt(rating, 10),
                    count
                })));
            })
            .catch(error => console.error("Fehler beim Laden der Evaluationsdaten:", error));
    }, [auditId]);

    return (
        <LayoutDefault>
            <div className="p-4 flex flex-col items-center">
                <h1 className="text-center text-4xl m-6">Evaluation</h1>

                {/* Overall Progress Bar */}
                <div className="w-full flex flex-col justify-center items-center h-20 mb-6">
                    <LinearProgressWithLabel value={overallProgress} />
                    <p className="text-center text-xl">Overall Progress</p>
                </div>

                {/* Category Progress Circular Charts */}
                <div className="w-full grid grid-cols-1 gap-6 mb-6 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5">
                    {categoryProgress.map(category => (
                        <div key={category.name} className="flex flex-col items-center">
                            <CircularProgressWithLabel value={category.progress} label={category.name} size={60} />
                        </div>
                    ))}
                </div>

                {/* Question Count by Rating Bar Chart */}
                <div className="max-w-full overflow-x-auto pb-10">
                    <CustomBarChart
                        data={questionCountByRating.map(item => item.count)} // Extract count for the chart data
                        colors={colors}
                    />
                </div>
            </div>
        </LayoutDefault>
    );
}
