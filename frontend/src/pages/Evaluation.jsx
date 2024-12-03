import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { LayoutDefault } from "../layouts/LayoutDefault.jsx";
import LinearProgressWithLabel from '../components/Charts/ProgressBar.jsx';
import CircularProgressWithLabel from '../components/Charts/CircularProgress.jsx';
import CustomBarChart from '../components/Charts/BarChart.jsx';
import Title from '../components/Textareas/Title.jsx';
import api from '../api';
import Box from "@mui/material/Box";

/**
 * Evaluation component fetches audit data and displays it as a series of progress indicators,
 * including an overall progress bar, circular progress indicators for each category,
 * and a bar chart representing question ratings.
 *
 * @component
 * @returns {JSX.Element} A layout component rendering the evaluation details.
 */
export function Evaluation() {
    // Extract audit ID from the route parameters to dynamically load audit data
    const { auditId } = useParams();

    /**
     * overallProgress - Represents the overall completion percentage of the audit.
     * categoryProgress - Array of objects representing each category's progress as a percentage.
     * questionCountByRating - Array showing the count of questions rated with each score (0-5, and nA).
     */
    const [overallProgress, setOverallProgress] = useState(0);
    const [categoryProgress, setCategoryProgress] = useState([]);
    const [questionCountByRating, setQuestionCountByRating] = useState([]);
    const [loading, setLoading] = useState(false); // Added loading state
    const [error, setError] = useState(null); // Added error state

    // Define color codes for the bar chart, where the last color (black) represents "nA"
    const colors = ['#a50026', '#d73027', '#fdae61', '#d9ef8b', '#66bd63', '#006837', '#000000'];

    /**
     * Fetches audit progress data from the backend when the component mounts or when auditId changes.
     * Sets the state values for overallProgress, categoryProgress, and questionCountByRating
     * based on the retrieved data.
     */
    useEffect(() => {
        setLoading(true); // Set loading to true when fetching starts
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
                setError(null); // Clear previous errors
            })
            .catch(error => {
                if (error.response) {
                    console.error(`Server error: ${error.response.status} - ${error.response.data?.message || "Unknown error"}`);
                    setError(`Error loading evaluation data: ${error.response.data?.message || "An unknown error occurred."}`);
                } else if (error.request) {
                    console.error("Network error: No response received from the server.");
                    setError("Network error: Please check your connection.");
                } else {
                    console.error(`Error: ${error.message}`);
                    setError(`An error occurred: ${error.message}`);
                }
            })
            .finally(() => setLoading(false)); // Set loading to false when fetching ends
    }, [auditId]);

    if (loading) {
        return <p>Loading...</p>; // Display loading message while data is being fetched
    }

    if (error) {
        return <p className="text-red-500">{error}</p>; // Display error message if fetch fails
    }

    
    return (
        <LayoutDefault>
            <div className="p-4 flex flex-col items-center">
                <Title>Evaluation</Title>

                {/* Overall Progress Bar */}
                <div data-cy={"ProgressBar"} id="result" className="w-full flex flex-col justify-center items-center h-20 mb-6">
                    <Box className="text-center" sx={{ width: '80%' }}>
                        <LinearProgressWithLabel value={overallProgress} />
                    </Box>
                    <p className="text-center text-xl">Gesamtfortschritt</p>
                </div>

                {/* Category Progress Circular Charts */}
                <div className="w-full grid grid-cols-1 gap-6 mb-6 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5">
                    {categoryProgress.map(category => (
                        <div data-cy={"CircularChart"} key={category.name} className="flex flex-col items-center" >
                            <CircularProgressWithLabel value={category.progress} label={category.name} size={60} />
                        </div>
                    ))}
                </div>

                {/* Question Count by Rating Bar Chart */}
                <div data-cy={"BarChart"} className="max-w-full overflow-x-auto pb-10">
                    <CustomBarChart
                        data={questionCountByRating.map(item => item.count)} // Extract count for the chart data
                        colors={colors}
                    />
                </div>
            </div>
        </LayoutDefault>
    );
}
