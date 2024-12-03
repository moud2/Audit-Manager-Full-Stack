import React, { useState } from 'react';
import { useNavigate } from "react-router-dom";

/**
 * CompareAudit component renders a button for navigating to the comparison page.
 * The button allows users to compare the currently selected audit.
 *
 * @param {Object} props - The props passed to the component.
 * @param {Object} props.selectedAudit - The selected audit data.
 * @returns {JSX.Element} A button that navigates to the CompareAudits page.
 */
export function CompareAudit({ selectedAudit }) {
    const navigate = useNavigate(); // Hook for navigation
    const [error, setError] = useState(null); // State to handle error messages

    /**
     * Handles navigation to the CompareAudits page.
     * Checks if a valid audit is selected before navigating.
     */
    const handleNavigate = () => {
        if (selectedAudit && selectedAudit.id) {
            navigate(`/compare-audits/${selectedAudit.id}`); // Navigate with the audit ID
        } else {
            setError("Kein Audit-Datensatz verfügbar. Bitte laden Sie die Seite neu oder wählen Sie ein anderes Audit aus.");
        }
    };

    return (
        <div className="flex flex-col items-center mt-6">
            {/* Error Message */}
            {error && (
                <p className="text-red-500 text-sm mb-4">
                    {error}
                </p>
            )}

            {/* Button for navigation */}
            <button
                className="fixed right-16 p-2 bottom-20 mb-12 bg-blue-500 text-white rounded"
                onClick={handleNavigate}
            >
                Audit vergleichen
            </button>
        </div>
    );
}
