import React, { useState } from 'react';
import { useNavigate } from "react-router-dom";

export function CompareAudit({ selectedAudit }) {
    const navigate = useNavigate();
    const [error, setError] = useState(null);

    const handleNavigate = () => {
        if (selectedAudit && selectedAudit.id) {
            navigate(`/compare-audits/${selectedAudit.id}`); 
        } else {
            setError("Kein Audit-Datensatz verfügbar. Bitte laden Sie die Seite neu oder wählen Sie ein anderes Audit aus.");
        }
    };

    return (
        <div className="flex flex-col items-center mt-6">
            {/* Fehlermeldung */}
            {error && (
                <p className="text-red-500 text-sm mb-4">
                    {error}
                </p>
            )}

            {/* Button */}
            <button
                className="fixed right-16 p-2 bottom-20 mb-12 bg-blue-500 text-white rounded"
                onClick={handleNavigate}
            >
                Audit vergleichen
            </button>
        </div>
    );
}
