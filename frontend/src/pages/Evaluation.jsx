import { useState, useEffect } from 'react';
import { LayoutDefault } from "../layouts/LayoutDefault.jsx";
import ProgressBar from '../components/Charts/ProgressBar.jsx';
import CircularProgress from '../components/Charts/CircularProgress.jsx';
import BarChart from '../components/Charts/BarChart.jsx';

/**
 * Evaluation component for displaying the progress and rating summary of an audit.
 * This component fetches evaluation data from the backend, displays an overall progress bar,
 * category progress circular charts, and a bar chart for question ratings.
 *
 * @component
 */
export function Evaluation() {
    /**
     * State variables for storing evaluation data from the backend.
     */
    const [overallProgress, setOverallProgress] = useState(0);
    const [categoryProgress, setCategoryProgress] = useState([]);
    const [questionCountByRating, setQuestionCountByRating] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState(null);

    // Colors for the bar chart.
    const colors = ['#a50026', '#d73027', '#fdae61', '#d9ef8b', '#66bd63', '#006837'];
    const auditId = 1; // Static audit ID example; replace with dynamic ID if necessary.

    /**
     * Fetches evaluation data from the backend API.
     * Handles JSON response parsing and updates state with progress and rating data.
     * Displays error if response is not JSON or fetch fails.
     */
    const fetchEvaluationData = async () => {
        try {
            const response = await fetch(`/api/v1/audits/${auditId}/progress`);
            if (!response.ok) {
                throw new Error("Failed to fetch evaluation data");
            }

            const contentType = response.headers.get("content-type");
            if (contentType && contentType.includes("application/json")) {
                const data = await response.json();
                console.log("Fetched data:", data); // Debug: Log fetched data
                setOverallProgress(data.overallProgress || 0);
                setCategoryProgress(Object.entries(data.categoryProgress || {}).map(([id, progress]) => ({
                    id: Number(id),
                    progress,
                })));
                setQuestionCountByRating(Object.entries(data.questionCountByRating || {}).map(([rating, count]) => ({
                    rating: Number(rating),
                    count,
                })));
            } else {
                const rawText = await response.text();
                console.error("Received non-JSON data:", rawText); // Debug: Log non-JSON data
                throw new Error("Received data is not JSON");
            }
        } catch (err) {
            console.error("Error fetching evaluation data:", err);
            setError(err.message);
        } finally {
            setIsLoading(false);
        }
    };

    /**
     * useEffect hook to fetch evaluation data when the component mounts.
     * Uses an immediately-invoked async function to handle Promise within useEffect.
     */
    useEffect(() => {
        (async () => {
            await fetchEvaluationData();
        })();
    }, [auditId]);

    /**
     * Renders a loading message while data is being fetched.
     */
    if (isLoading) {
        return <p>Loading evaluation data...</p>;
    }

    /**
     * Renders an error message if data fetching failed.
     */
    if (error) {
        return <p>Error loading evaluation data: {error}</p>;
    }

    /**
     * Renders the main evaluation component layout including:
     * - Overall progress bar
     * - Category progress circular charts
     * - Bar chart of question ratings distribution
     */
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
                        <div key={category.id} className="flex flex-col items-center">
                            <CircularProgress value={category.progress} size={60} />
                            <p className="text-center mt-2">Category {category.id}</p>
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
