import React from "react";
import CategoryProgress from "./CategoryProgress.jsx";

export default function AuditProgress() {
    const dummyData = [
        { name: "Netzwerk", progress: 20 },
        { name: "Server Administration", progress: 75 },
        { name: "Datenbanken", progress: 50 },
        { name: "Server Administration", progress: 75 },
        { name: "Datenbanken", progress: 50 },
    ];

    return (
        <div>
            {/*/!* Überschrift *!/*/}
            {/*<h2 className="text-lg font-semibold text-center mb-4 text-gray-900">*/}
            {/*    Fortschritt Audit*/}
            {/*</h2>*/}

            {/*/!* Fortschrittsbalken für jede Kategorie *!/*/}
            {dummyData.map((category, index) => (
                <div key={index} className="block p-1 mt-2.5 text-gray-700 hover:text-black hover:scale-105 transition-transform transform border border-gray-400 rounded text-center">
                    <CategoryProgress
                        key={index}
                        name={category.name}
                        progress={category.progress}
                    />
                </div>
            ))}
        </div>
    );
}