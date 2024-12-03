import React, { useState, useEffect } from "react";
import { useParams } from 'react-router-dom';
import { LayoutDefault } from "../layouts/LayoutDefault.jsx";
import { AuditDropdown } from "../components/CompareAudit/AuditDropdown.jsx";
import { AuditComparisonCard } from "../components/CompareAudit/AuditComparisonCard.jsx";
import api from "../api";

/**
 * CompareAudits component for comparing two audits.
 * This component fetches data for the selected audit and allows
 * comparison with a second audit selected from a dropdown.
 *
 * @returns {JSX.Element} A layout component for comparing audits.
 */
export function CompareAudits() {
    const { auditId } = useParams(); // Extract audit ID from the URL
    const [selectedAudit, setSelectedAudit] = useState(null); // State for the selected audit
    const [secondAudit, setSecondAudit] = useState(null); // State for the second audit to compare
    const [allAudits, setAllAudits] = useState([]); // State for the list of all audits
    const [error, setError] = useState(null); // State for error messages

    /**
     * useEffect for fetching audit details and the list of all audits.
     * Runs when the auditId or allAudits state changes.
     */
    useEffect(() => {
        console.log("Audit ID from URL:", auditId);

        if (auditId) {
            api.get(`/v1/audits/${auditId}/progress`)
                .then(response => {
                    console.log("API Response:", response.data);

                    const categoryProgressArray = Object.entries(response.data.categoryProgress || {}).map(([name, progress]) => ({
                        name,
                        progress,
                    }));

                    // Retrieve the audit name from the list of all audits
                    const auditName = allAudits.find(a => a.id === parseInt(auditId))?.name || `Audit ID: ${auditId}`;

                    setSelectedAudit({
                        id: auditId,
                        name: auditName, // Add audit name
                        overallProgress: response.data.overallProgress,
                        categoryProgress: categoryProgressArray,
                        questionCountByRating: response.data.questionCountByRating,
                    });
                })
                .catch(() => setError("Fehler beim Laden des ersten Audits."));
        }

        api.get('/v1/audits')
            .then(response => setAllAudits(response.data))
            .catch(() => setError("Fehler beim Laden der Audit-Liste."));
    }, [auditId, allAudits]);

    /**
     * Handle the selection of a second audit to compare.
     *
     * @param {Object} audit - The audit selected from the dropdown.
     */
    const handleAuditSelect = (audit) => {
        api.get(`/v1/audits/${audit.id}/progress`)
            .then(response => {
                const categoryProgressArray = Object.entries(response.data.categoryProgress || {}).map(([name, progress]) => ({
                    name,
                    progress,
                }));

                setSecondAudit({
                    id: audit.id,
                    name: audit.name || `Audit ID: ${audit.id}`,
                    overallProgress: response.data.overallProgress,
                    categoryProgress: categoryProgressArray,
                    questionCountByRating: response.data.questionCountByRating,
                });
            })
            .catch(() => setError("Fehler beim Laden des zweiten Audits."));
    };

    return (
        <LayoutDefault>
            <div className="max-w-6xl mx-auto px-4">
                <h1 className="text-center text-2xl font-bold mb-6">Audits vergleichen</h1>

                {/* Display error messages, if any */}
                {error && <p className="text-red-500 text-center mb-4">{error}</p>}

                {/* Dropdown for selecting the second audit */}
                <AuditDropdown audits={allAudits.filter(audit => audit.id !== selectedAudit?.id)} onAuditSelect={handleAuditSelect} />

                {/* Display comparison cards for the selected and second audits */}
                <div className="grid grid-cols-2 gap-6">
                    {selectedAudit && (
                        <AuditComparisonCard
                            name={selectedAudit.name || `Audit ID: ${selectedAudit.id}`}
                            progress={selectedAudit.overallProgress || 0}
                            categories={selectedAudit.categoryProgress || []}
                            distribution={Object.values(selectedAudit.questionCountByRating || {})}
                        />
                    )}
                    {secondAudit && (
                        <AuditComparisonCard
                            name={secondAudit.name || `Audit ID: ${secondAudit.id}`}
                            progress={secondAudit.overallProgress || 0}
                            categories={secondAudit.categoryProgress || []}
                            distribution={Object.values(secondAudit.questionCountByRating || {})}
                        />
                    )}
                </div>
            </div>
        </LayoutDefault>
    );
}
