import React, { useState } from "react";
import { useLocation } from "react-router-dom";
import { LayoutDefault } from "../layouts/LayoutDefault.jsx";
import { AuditComparisonCard } from "../components/CompareAudit/AuditComparisonCard.jsx";
import { AuditDropdown } from "../components/CompareAudit/AuditDropdown.jsx";

/**
 * CompareAudits component for comparing audits.
 * @component
 */
export function CompareAudits() {
    const location = useLocation();
    const selectedAudit = location.state?.selectedAudit;

    // Platzhalter-Daten
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
        {
            id: 2,
            name: "Audit B",
            progress: 65,
            categories: [
                { id: 1, name: "Kategorie 1", progress: 50 },
                { id: 2, name: "Kategorie 2", progress: 75 },
            ],
            distribution: [2, 3, 4, 5, 6, 7],
        },
        {
          id: 3,
          name: "Audit C",
          progress: 78,
          categories: [
              { id: 1, name: "Kategorie 1", progress: 60 },
              { id: 2, name: "Kategorie 2", progress: 80 },
              { id: 3, name: "Kategorie 3", progress: 90 },
          ],
          distribution: [5, 7, 3, 6, 4, 8],
      },
      {
          id: 4,
          name: "Audit D",
          progress: 90,
          categories: [
              { id: 1, name: "Kategorie 1", progress: 95 },
              { id: 2, name: "Kategorie 2", progress: 85 },
          ],
          distribution: [6, 8, 4, 7, 5, 9],
      },
    ];

    const [secondSelectedAudit, setSecondSelectedAudit] = useState(null);

    // Filter für das Dropdown
    const otherAudits = audits.filter((audit) => audit.id !== selectedAudit?.id);

    return (
        <LayoutDefault>
            <div className="max-w-6xl mx-auto px-4">
                <h1 className="text-center text-2xl font-bold mb-6">Audits vergleichen</h1>

                {/* Dropdown zur Auswahl des zweiten Audits */}
                <AuditDropdown
                    audits={otherAudits}
                    onAuditSelect={setSecondSelectedAudit}
                />

                {/* Vergleichsansicht */}
                <div className="grid grid-cols-2 gap-6">
                    {/* Erstes Audit */}
                    {selectedAudit && (
                        <AuditComparisonCard
                            name={selectedAudit.name}
                            progress={selectedAudit.progress}
                            categories={selectedAudit.categories}
                            distribution={selectedAudit.distribution}
                        />
                    )}

                    {/* Zweites Audit */}
                    {secondSelectedAudit && (
                        <AuditComparisonCard
                            name={secondSelectedAudit.name}
                            progress={secondSelectedAudit.progress}
                            categories={secondSelectedAudit.categories}
                            distribution={secondSelectedAudit.distribution}
                        />
                    )}
                </div>
            </div>
        </LayoutDefault>
    );
}
