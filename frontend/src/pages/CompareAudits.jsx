import React from "react";
import { useState } from 'react';
import KeyboardArrowDownIcon from "@mui/icons-material/KeyboardArrowDown";
import { ComparisonAuditCard } from "../components/CompareColumn/ComparisonAuditCard";
import { LayoutDefault } from "../layouts/LayoutDefault.jsx";

/**
 * CompareColumn component handles the selection of audits for comparison
 * and passes the selected audit data to the parent component.
 *
 * @param {Array} audits - The list of audits to compare.
 * @param {Function} onCompareSelect - Callback to pass selected comparison data.
 */
export function CompareAudits() {
  const [firstSelectedAudit, setFirstSelectedAudit] = useState(null);
  const [secondSelectedAudit, setSecondSelectedAudit] = useState(null);

  const handleFirstAuditSelection = (event) => {
    const selectedAudit = audits.find((audit) => audit.id === parseInt(event.target.value));
    setFirstSelectedAudit(selectedAudit || null);
  };

  const handleSecondAuditSelection = (event) => {
    const selectedAudit = audits.find((audit) => audit.id === parseInt(event.target.value));
    setSecondSelectedAudit(selectedAudit || null);
  };

  const colors = ["#a50026", "#d73027", "#fdae61", "#d9ef8b", "#66bd63", "#006837"];

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
  ];

  return (
    <LayoutDefault>
    <div className="max-w-xl mx-auto items-center">
      <h1>Audit auswählen</h1>
      <div className="flex space-x-4 relative">
        {/* First Select Element */}
        <div className="relative flex-auto">
          <select
            onChange={handleFirstAuditSelection}
            className="font-semibold appearance-none p-4 border-2 rounded border-gray-300 text-gray-900 text-sm w-full focus:ring-black focus:border-blue-300 hover:border-black"
          >
            <option value="">Choose the Audit</option>
            {audits.map((audit) => (
              <option key={audit.id} value={audit.id}>
                {audit.name}
              </option>
            ))}
          </select>
          <KeyboardArrowDownIcon className="absolute right-4 top-1/2 transform -translate-y-1/2 text-blue-600 pointer-events-none" />
          {/* Render ComparisonAuditCard for First Selected Audit */}
          {firstSelectedAudit && (
            <ComparisonAuditCard
              progress={firstSelectedAudit.progress}
              categoryProgress={firstSelectedAudit.categories}
              pointsDistribution={firstSelectedAudit.distribution}
              colors={colors}
            />
          )}
        </div>

        {/* Second Select Element */}
        <div className="relative flex-auto">
          <select
            onChange={handleSecondAuditSelection}
            className="font-semibold appearance-none p-4 border-2 rounded border-gray-300 text-gray-900 text-sm w-full focus:ring-black focus:border-blue-300 hover:border-black"
          >
            <option value="">Choose the Audit</option>
            {audits.map((audit) => (
              <option key={audit.id} value={audit.id}>
                {audit.name}
              </option>
            ))}
          </select>
          <KeyboardArrowDownIcon className="absolute right-4 top-1/2 transform -translate-y-1/2 text-blue-600 pointer-events-none" />
          {/* Render ComparisonAuditCard for Second Selected Audit */}
          {secondSelectedAudit && (
            <ComparisonAuditCard
              progress={secondSelectedAudit.progress}
              categoryProgress={secondSelectedAudit.categories}
              pointsDistribution={secondSelectedAudit.distribution}
              colors={colors}
            />
          )}
        </div>
      </div>
    </div>
    </LayoutDefault>
  );
}