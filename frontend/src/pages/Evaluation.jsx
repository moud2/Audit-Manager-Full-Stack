import { useState } from 'react';
import { LayoutDefault } from "../layouts/LayoutDefault.jsx";
import ProgressBar from '../components/Charts/ProgressBar.jsx';
import CircularProgress from '../components/Charts/CircularProgress.jsx';
import BarChart from '../components/Charts/BarChart.jsx';
import { CompareAudit } from '../components/CompareAudit/CompareAudit.jsx';

/**
 * Evaluation component displaying progress bars and charts for evaluation data.
 * @component
 */
export function Evaluation() {
    // Daten für Diagramme und Fortschritt
    const audits = [
        {
            id: 1,
            name: "Audit A",
            progress: 85,
            categories: [
                { id: 1, name: "Kategorie 1", progress: 70 },
                { id: 2, name: "Kategorie 2", progress: 90 },
            ],
            distribution: [4, 6, 3, 7, 5, 8],
        },
    ];

    const [selectedAudit] = useState(audits[0]); // Standardmäßig Audit A anzeigen

    const colors = ['#a50026', '#d73027', '#fdae61', '#d9ef8b', '#66bd63', '#006837'];

    return (
        <LayoutDefault>
            <div className="p-4 flex flex-col items-center">
                {/* Titel */}
                <div id="title">
                    <h1 className="text-center text-4xl font-bold mb-6">Evaluation</h1>
                </div>

                {/* Fortschrittsbalken */}
                <div id="result" className="w-full flex flex-col items-center mb-6">
                    <div className="w-4/5">
                        <ProgressBar value={selectedAudit.progress} />
                    </div>
                    <p className="text-center text-xl mt-2">Gesamtfortschritt</p>
                </div>

                {/* Kategorien Fortschritt */}
                <div id="categories" className="w-full grid grid-cols-1 gap-6 mb-6 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5">
                    {selectedAudit.categories.map((category) => (
                        <div key={category.id} className="flex flex-col items-center">
                            <CircularProgress value={category.progress} size={70} />
                            <p className="text-center mt-2">{category.name}</p>
                        </div>
                    ))}
                </div>

                {/* Punktverteilung - Balkendiagramm */}
                <div id="result_per_question" className="max-w-full mb-10">
                    <BarChart
                        data={selectedAudit.distribution}
                        colors={colors}
                        width={800} // Feste Breite
                        height={400} // Feste Höhe
                    />
                </div>

                {/* CompareAudit-Komponente mit ausgewähltem Audit */}
                <CompareAudit selectedAudit={selectedAudit} />
            </div>
        </LayoutDefault>
    );
}
