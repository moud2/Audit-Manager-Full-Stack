import React from "react";
import ProgressBar from "../Charts/ProgressBar.jsx";
import RadarChart from "../Charts/RadarChart";

export function AuditComparisonCard({ name, progress, categoryProgress }) {
    const labels = categoryProgress?.map(category => category.categoryName) || [];
    const data = categoryProgress?.map(category => category.currentCategoryProgress) || [];

    console.log("Labels for RadarChart:", labels);
    console.log("Data for RadarChart:", data);

    return (
        <div className="p-4 bg-gray-100 rounded shadow">
            <h2 className="text-xl font-semibold mb-4">{name}</h2>

            <div className="mb-4">
                <ProgressBar value={progress} />
                <p className="text-center text-sm mt-2">Gesamtfortschritt</p>
            </div>

            <div data-cy={"RadarChart"} className="w-full flex justify-center">
                {labels.length > 0 && data.length > 0 ? (
                    <RadarChart labels={labels} currentData={data} width={50} height={50} />
                ) : (
                    <p className="text-sm text-gray-500">Keine Daten für Kategorien verfügbar</p>
                )}
            </div>
        </div>
    );
}