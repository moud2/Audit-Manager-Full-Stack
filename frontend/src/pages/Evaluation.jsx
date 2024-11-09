import { useState, useEffect } from 'react';
import { LayoutDefault } from "../layouts/LayoutDefault.jsx";
import ProgressBar from '../components/Charts/ProgressBar.jsx';
import CircularProgress from '../components/Charts/CircularProgress.jsx';
import BarChart from '../components/Charts/BarChart.jsx';

/**
 * Evaluation component displaying progress bars and charts for evaluation data.
 * @component
 */
export function Evaluation() {
    // States für Daten aus dem Backend
    const [mainProgress, setMainProgress] = useState(0);
    const [categoryProgress, setCategoryProgress] = useState([]);
    const [pointsDistribution, setPointsDistribution] = useState([]);
    const colors = ['#a50026', '#d73027', '#fdae61', '#d9ef8b', '#66bd63', '#006837'];

    // Funktion zum Abrufen der Daten aus dem Backend
    useEffect(() => {
        async function fetchEvaluationData() {
            try {
                const response = await fetch("/api/v1/audits/{auditId}/progress");
                if (!response.ok) {
                    throw new Error("Failed to fetch evaluation data");
                }
                const data = await response.json();

                // Daten setzen
                setMainProgress(data.mainProgress);
                setCategoryProgress(data.categoryProgress);
                setPointsDistribution(data.pointsDistribution);

            } catch (error) {
                console.error("Error fetching evaluation data:", error);
            }
        }

        fetchEvaluationData();
    }, []);

    return (
        <LayoutDefault>
            <div className="p-4 flex flex-col items-center">
                <div id="title">
                    <h1 className="text-center text-4xl m-6">Evaluation</h1>
                </div>

                <div id="result" className={"w-full flex flex-col justify-center items-center h-20 mb-6"}>
                    <div className={"text-center w-4/5"}>
                        <ProgressBar value={mainProgress} />
                    </div>
                    <p className={"text-center text-xl"}>Gesamtfortschritt</p>
                </div>

                <div id="categories" className="w-full grid grid-cols-1 gap-6 mb-6 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5">
                    {categoryProgress.map(category => (
                        <div key={category.id} className={"flex flex-col items-center"}>
                            <CircularProgress
                                value={category.progress}
                                size={60}
                            />
                            <p className={"text-center mt-2"}>{category.name}</p>
                        </div>
                    ))}
                </div>

                <div id="result_per_question" className={"max-w-full overflow-x-auto pb-10"}>
                    <BarChart data={pointsDistribution} colors={colors} />
                </div>
            </div>
        </LayoutDefault>
    );
}
