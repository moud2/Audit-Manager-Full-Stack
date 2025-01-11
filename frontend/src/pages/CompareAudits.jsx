import React, { useState, useEffect, useCallback } from "react";
import { useParams } from 'react-router-dom';
import { LayoutDefault } from "../layouts/LayoutDefault.jsx";
import { AuditDropdown } from "../components/CompareAudit/AuditDropdown.jsx";
import { AuditComparisonCard } from "../components/CompareAudit/AuditComparisonCard.jsx";
import api from "../api";
import Title from "../components/Textareas/Title.jsx";

/**
 * CompareAudits component renders a page for comparing two audits.
 * The user can select a second audit to compare it with the currently selected audit.
 *
 * @component
 * @returns {JSX.Element} A page displaying a comparison of two audits with progress data and a radar chart.
 */
export function CompareAudits() {
    const { auditId } = useParams();
    const [selectedAudit, setSelectedAudit] = useState(null); // Stores data of the first audit
    const [secondAudit, setSecondAudit] = useState({ // Stores data of the second audit
        name: "",
        overallProgress: 0,
        categoryProgress: [],
    });

    const [allAudits, setAllAudits] = useState([]); // Stores the list of all audits available for comparison
    const [error, setError] = useState(null); // Stores error messages, if any

    /**
     * Fetches audit progress and category-specific progress data from the API.
     *
     * @param {number} auditId - The ID of the audit to fetch data for.
     * @param {Function} setAudit - State setter function for updating the audit data (selectedAudit or secondAudit).
     * @param {string} errorMessage - Error message to display if the API request fails.
     */
    const fetchAuditData = useCallback(async (auditId, setAudit, errorMessage) => {
        try {
            const progressResponse = await api.get(`/v1/audits/${auditId}/progress`);
            console.log("API Response:", progressResponse.data);

            const auditData = {
                id: auditId,
                name: allAudits.find(a => a.id === parseInt(auditId))?.name || `Audit ${auditId}`,
                overallProgress: progressResponse.data.currentAuditProgress || 0,
                categoryProgress: (progressResponse.data.categoryProgress || []).map(category => ({
                    categoryName: category.categoryName,
                    currentCategoryProgress: category.currentCategoryProgress,
                })),
            };

            console.log("Processed Audit Data:", auditData);

            setAudit(auditData);
        } catch (err) {
            console.error(errorMessage, err);
            setError(errorMessage);
        }
    }, [allAudits]);

    /**
     * Fetches the list of all available audits for selection.
     */
    useEffect(() => {
        const fetchAllAudits = async () => {
            try {
                const response = await api.get('/v1/audits');
                setAllAudits(response.data);
            } catch {
                setError("Fehler beim Laden der Audit-Liste.");
            }
        };

        fetchAllAudits();
    }, []);

    /**
     * Fetches data for the selected audit after all audits are loaded.
     */
    useEffect(() => {
        if (auditId && allAudits.length > 0) {
            fetchAuditData(
                auditId,
                setSelectedAudit,
                "Fehler beim Laden der Daten des ersten Audits."
            );
        }
    }, [auditId, allAudits, fetchAuditData]);

    /**
     * Handles the selection of the second audit from the dropdown.
     *
     * @param {Object} audit - The selected audit object.
     */
    const handleAuditSelect = (audit) => {
        fetchAuditData(
            audit.id,
            setSecondAudit,
            "Fehler beim Laden der Daten des zweiten Audits."
        );
    };

    return (
        <LayoutDefault>
            <div className="max-w-6xl mx-auto px-4">
                <Title>Audits vergleichen</Title>

                <AuditDropdown
                    audits={allAudits.filter(audit => audit.id !== selectedAudit?.id)}
                    onAuditSelect={handleAuditSelect}
                />

                <div className="grid grid-cols-2 gap-6">
                    {selectedAudit && (
                        <AuditComparisonCard
                            name={selectedAudit.name}
                            progress={selectedAudit.overallProgress}
                            categoryProgress={selectedAudit.categoryProgress}
                        />
                    )}
                    {secondAudit && secondAudit.name ? (
                        <AuditComparisonCard
                            name={secondAudit.name}
                            progress={secondAudit.overallProgress}
                            categoryProgress={secondAudit.categoryProgress}
                        />
                    ) : (
                        <div className="p-4 bg-gray-100 rounded shadow">
                            <p className="text-center text-sm">Bitte ein zweites Audit auswählen</p>
                        </div>
                    )}
                </div>
            </div>
        </LayoutDefault>
    );
}
