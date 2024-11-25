import React from "react";
import ProgressBar from "../Charts/ProgressBar.jsx";
import CircularProgress from "../Charts/CircularProgress.jsx";
import BarChart from "../Charts/BarChart.jsx";

/**
 * AuditComparisonCard component displays detailed comparison data for an audit.
 *
 * @param {Object} props - The properties passed to the component.
 * @param {string} props.name - The name of the audit.
 * @param {number} props.progress - The overall progress of the audit.
 * @param {Array} props.categories - The category-wise progress data.
 * @param {Array} props.distribution - The points distribution data for the bar chart.
 * @returns {JSX.Element}
 */
export function AuditComparisonCard({ name, progress, categories, distribution }) {
    const colors = ["#a50026", "#d73027", "#fdae61", "#d9ef8b", "#66bd63", "#006837"];

    return (
        <div className="p-4 bg-gray-100 rounded shadow">
            <h2 className="text-xl font-semibold mb-4">{name}</h2>

            {/* Gesamtfortschritt */}
            <div className="mb-4">
                <ProgressBar value={progress} />
                <p className="text-center text-sm mt-2">Gesamtfortschritt</p>
            </div>

            {/* Kategorien Fortschritt */}
            <div className="grid grid-cols-2 gap-4 mb-4">
                {categories.map((category) => (
                    <div key={category.id} className="text-center">
                        <CircularProgress value={category.progress} size={60} />
                        <p className="text-sm mt-2">{category.name}</p>
                    </div>
                ))}
            </div>

            {/* Punktverteilung */}
            <BarChart
                data={distribution}
                colors={colors}
                width={400}
                height={300}
            />
        </div>
    );
}
