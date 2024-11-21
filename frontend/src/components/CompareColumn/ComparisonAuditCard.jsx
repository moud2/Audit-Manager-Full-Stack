import React from "react";
import ProgressBar from "../Charts/ProgressBar.jsx";
import CircularProgress from "../Charts/CircularProgress.jsx";
import BarChart from "../Charts/BarChart.jsx";

/**
 * ComparisonAuditCard component displays comparison details of an audit.
 *
 * @param {Object} props - The properties passed to the component.
 * @param {number} props.progress - The overall progress value.
 * @param {Array} props.categoryProgress - The category-wise progress data.
 * @param {Array} props.pointsDistribution - The points distribution data for the bar chart.
 * @param {Array} props.colors - The color scheme for the bar chart.
 * @returns {JSX.Element} The rendered component.
 */
export function ComparisonAuditCard({ 
  progress, 
  categoryProgress, 
  pointsDistribution, 
  colors 
}) {
  return (
    <div className="p-4 bg-white shadow rounded">
      <div className="mb-4">
        <ProgressBar value={progress} />
        <p className="text-center text-xl">Gesamtfortschritt</p>
      </div>
      <div className="grid grid-cols-2 gap-4">
        {categoryProgress.map((category) => (
          <div key={category.id} className="text-center">
            <CircularProgress value={category.progress} size={60} />
            <p>{category.name}</p>
          </div>
        ))}
      </div>
      <BarChart 
        data={pointsDistribution} 
        colors={colors} 
        width={400} 
        height={300} 
      />
    </div>
  );
}
