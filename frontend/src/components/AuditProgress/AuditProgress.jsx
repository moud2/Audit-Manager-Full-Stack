import React from "react";
import CategoryProgress from "./CategoryProgress.jsx";

export default function AuditProgress() {
    const dummyData = [
        { name: "Netzwerk", progress: 20 },
        { name: "Server Administration", progress: 75 },
        { name: "Datenbanken", progress: 50 },
    ];

    return (
        <div className="w-5/6 mx-auto mt-4 p-4 border border-gray-300 rounded bg-gray-100">
            {/* Überschrift */}
            <h2 className="text-lg font-semibold text-center mb-4 text-gray-900">
                Fortschritt Audit
            </h2>

            {/* Fortschrittsbalken für jede Kategorie */}
            {dummyData.map((category, index) => (
                <CategoryProgress
                    key={index}
                    name={category.name}
                    progress={category.progress}
                />
            ))}
        </div>
    );
}