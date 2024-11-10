import { useState, useEffect } from 'react';
import { LayoutDefault } from "../layouts/LayoutDefault.jsx";
import ProgressBar from '../components/Charts/ProgressBar.jsx';
import CircularProgress from '../components/Charts/CircularProgress.jsx';
import BarChart from '../components/Charts/BarChart.jsx';

export function Evaluation() {
    const [overallProgress, setOverallProgress] = useState(0);
    const [categoryProgress, setCategoryProgress] = useState([]);
    const [questionCountByRating, setQuestionCountByRating] = useState([]);

    const auditId = 1; // Statisch, muss später dynamisch gemacht werden.                        TODO
    const colors = ['#a50026', '#d73027', '#fdae61', '#d9ef8b', '#66bd63', '#006837'];

    useEffect(() => {
        fetch(`/api/v1/audits/${auditId}/progress`)
            .then(response => response.json())
            .then(data => {
                setOverallProgress(data.overallProgress || 0);
                setCategoryProgress(Object.entries(data.categoryProgress || {}).map(([name, progress]) => ({
                    name,
                    progress
                })));
                setQuestionCountByRating(Object.entries(data.questionCountByRating || {}).map(([rating, count]) => ({
                    rating: parseInt(rating),
                    count
                })));
            })
            .catch(error => console.error("Fehler beim Laden der Evaluationsdaten:", error));
    }, [auditId]);

    return (
        <LayoutDefault>
            <div className="p-4 flex flex-col items-center">
                <h1 className="text-center text-4xl m-6">Evaluation</h1>

                <div className="w-full flex flex-col justify-center items-center h-20 mb-6">
                    <ProgressBar value={overallProgress} />
                    <p className="text-center text-xl">Overall Progress</p>
                </div>

                <div className="w-full grid grid-cols-1 gap-6 mb-6 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5">
                    {categoryProgress.map(category => (
                        <div key={category.name} className="flex flex-col items-center">
                            <CircularProgress value={category.progress} size={60} />
                            <p className="text-center mt-2">{category.name}</p>
                        </div>
                    ))}
                </div>

                <div className="max-w-full overflow-x-auto pb-10">
                    <BarChart data={questionCountByRating} colors={colors} />
                </div>
            </div>
        </LayoutDefault>
    );
}
