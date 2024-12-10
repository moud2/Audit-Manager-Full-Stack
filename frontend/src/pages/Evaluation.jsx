import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { LayoutDefault } from "../layouts/LayoutDefault.jsx";
import LinearProgressWithLabel from '../components/Charts/ProgressBar.jsx';
import CircularProgressWithLabel from '../components/Charts/CircularProgress.jsx';
import CustomBarChart from '../components/Charts/BarChart.jsx';
import Title from '../components/Textareas/Title.jsx';
import api from '../api';
import Box from "@mui/material/Box";
import { CompareAudit } from '../components/CompareAudit/CompareAudit.jsx';

/**
 * Evaluation component fetches audit data and displays it as a series of progress indicators,
 * including an overall progress bar, circular progress indicators for each category,
 * and a bar chart representing question ratings.
 *
 * @component
 * @returns {JSX.Element} A layout component rendering the evaluation details.
 */
export function Evaluation() {
    const { auditId } = useParams();
    const [overallProgress, setOverallProgress] = useState(0);
    const [categoryProgress, setCategoryProgress] = useState([]);
    const [ratingDistribution, setRatingDistribution] = useState([0, 0, 0, 0, 0, 0, 0]);

    // Define color codes for the bar chart, where the last color (black) represents "nA"
    const colors = ['#a50026', '#d73027', '#fdae61', '#d9ef8b', '#66bd63', '#006837', '#000000'];

    useEffect(() => {
        // Fetch progress data
        api.get(`/v1/audits/${auditId}/progress`)
            .then(response => {
                setOverallProgress(response.data.currentAuditProgress || 0);
                setCategoryProgress(response.data.categoryProgress || []);
            })
            .catch(error => console.error("Error loading progress data:", error));

        // Fetch ratings data
        api.get(`/v1/audits/${auditId}/ratings`)
            .then(response => {
                // Initialize distribution array: [0, 1, 2, 3, 4, 5, "nA"]
                const distribution = [0, 0, 0, 0, 0, 0, 0];
                response.data.forEach(rating => {
                    if (rating.na === true) {
                        // If the question is explicitly marked as "nA"
                        distribution[6]++;
                    } else if (rating.points !== null) {
                        // If the question has a specific rating (0-5)
                        distribution[rating.points]++;
                    } else {
                        // If the question is unanswered
                        distribution[6]++;
                    }
                });
                setRatingDistribution(distribution);
            })
            .catch(error => console.error("Error loading ratings data:", error));
    }, [auditId]);

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
                        <div  data-cy={"CircularChart"} key={category.categoryName} className="flex flex-col items-center">
                            <CircularProgressWithLabel value={category.currentCategoryProgress} label={category.categoryName} size={60} />
                        </div>
                    ))}
                </div>

                {/* Question Count by Rating Bar Chart */}
                <div data-cy={"BarChart"} className="max-w-full overflow-x-auto pb-10">
                    <CustomBarChart
                        data={ratingDistribution} // Pass the rating distribution directly
                        colors={colors}
                    />
                </div>

                {/* Audit vergleichen Button */}
                <CompareAudit selectedAudit={auditId ? { id: auditId } : null} />

                
            </div>
        </LayoutDefault>
    );
}
