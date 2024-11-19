import { useState } from 'react';
import { LayoutDefault } from "../layouts/LayoutDefault.jsx";
import ProgressBar from '../components/Charts/ProgressBar.jsx';
import CircularProgress from '../components/Charts/CircularProgress.jsx';
import BarChart from '../components/Charts/BarChart.jsx';

/**
 * Evaluation component displaying progress bars and charts with responsive audit comparison functionality.
 * @component
 */
export function Evaluation() {

    // Daten später durch Backend erhalten?
    const [mainProgress, setMainProgress] = useState(75);
    const [categoryProgress, setCategoryProgress] = useState([
        { id: 1, name: 'Kategorie 1', progress: 60 },
        { id: 2, name: 'Kategorie 2', progress: 80 },
    ]);
    const [pointsDistribution, setPointsDistribution] = useState([3, 5, 2, 8, 4, 6]);

    const [comparisonProgress, setComparisonProgress] = useState(null);
    const [comparisonCategoryProgress, setComparisonCategoryProgress] = useState(null);
    const [comparisonPointsDistribution, setComparisonPointsDistribution] = useState(null);

    const colors = ['#a50026', '#d73027', '#fdae61', '#d9ef8b', '#66bd63', '#006837'];

    const audits = [
        {
            id: 1,
            name: "Audit A",
            progress: 85,
            categories: [
                { id: 1, name: 'Kategorie 1', progress: 70 },
                { id: 2, name: 'Kategorie 2', progress: 90 },
            ],
            distribution: [4, 6, 3, 7, 5, 8],
        },
        {
            id: 2,
            name: "Audit B",
            progress: 65,
            categories: [
                { id: 1, name: 'Kategorie 1', progress: 50 },
                { id: 2, name: 'Kategorie 2', progress: 75 },
            ],
            distribution: [2, 3, 4, 5, 6, 7],
        }
    ];

    const handleAuditSelection = (event) => {
        const selectedAudit = audits.find(audit => audit.id === parseInt(event.target.value));
        if (selectedAudit) {
            setComparisonProgress(selectedAudit.progress);
            setComparisonCategoryProgress(selectedAudit.categories);
            setComparisonPointsDistribution(selectedAudit.distribution);
        } else {
            setComparisonProgress(null);
            setComparisonCategoryProgress(null);
            setComparisonPointsDistribution(null);
        }
    };

    return (
        <LayoutDefault>
            <div className="p-4 flex flex-col items-center">
                <h1 className="text-center text-4xl m-6">Evaluation</h1>

                <div className="mb-6 w-full flex justify-center">
                    <label className="mr-4 text-lg">Vergleiche mit:</label>
                    <select
                        onChange={handleAuditSelection}
                        className="p-2 border rounded"
                    >
                        <option value="">Kein Vergleich</option>
                        {audits.map(audit => (
                            <option key={audit.id} value={audit.id}>
                                {audit.name}
                            </option>
                        ))}
                    </select>
                </div>

                <div className={`grid w-full gap-8 ${comparisonProgress ? 'grid-cols-2' : 'grid-cols-1'}`}>
                    {/* Haupt-Audit */}
                    <div className="p-4 bg-white shadow rounded">
                        <h2 className="text-center text-2xl mb-4">Haupt-Audit</h2>
                        <div className="mb-4">
                            <ProgressBar value={mainProgress} />
                            <p className="text-center text-xl">Gesamtfortschritt</p>
                        </div>
                        <div className="grid grid-cols-2 gap-4">
                            {categoryProgress.map(category => (
                                <div key={category.id} className="text-center">
                                    <CircularProgress value={category.progress} size={60} />
                                    <p>{category.name}</p>
                                </div>
                            ))}
                        </div>
                        <BarChart 
                            data={pointsDistribution} 
                            colors={colors} 
                            width={comparisonProgress ? 400 : 900} 
                            height={comparisonProgress ? 300 : 350} 
                        />
                    </div>

                    {/* Vergleichs-Audit */}
                    {comparisonProgress !== null && (
                        <div className="p-4 bg-white shadow rounded">
                            <h2 className="text-center text-2xl mb-4">Vergleichs-Audit</h2>
                            <div className="mb-4">
                                <ProgressBar value={comparisonProgress} />
                                <p className="text-center text-xl">Gesamtfortschritt</p>
                            </div>
                            <div className="grid grid-cols-2 gap-4">
                                {comparisonCategoryProgress.map(category => (
                                    <div key={category.id} className="text-center">
                                        <CircularProgress value={category.progress} size={60} />
                                        <p>{category.name}</p>
                                    </div>
                                ))}
                            </div>
                            <BarChart data={comparisonPointsDistribution} colors={colors} width={400} height={300} />
                        </div>
                    )}
                </div>
            </div>
        </LayoutDefault>
    );
}
