import React, { useState, useEffect, useCallback } from "react";
import { useParams } from 'react-router-dom';
import { LayoutDefault } from "../layouts/LayoutDefault.jsx";
import { AuditDropdown } from "../components/CompareAudit/AuditDropdown.jsx";
import { AuditComparisonCard } from "../components/CompareAudit/AuditComparisonCard.jsx";
import api from "../api";
import Title from "../components/Textareas/Title.jsx";

export function CompareAudits() {
    const { auditId } = useParams(); 
    const [selectedAudit, setSelectedAudit] = useState(null);
    const [secondAudit, setSecondAudit] = useState({
        name: "",
        overallProgress: 0,
        categoryProgress: [],
    });

    const [allAudits, setAllAudits] = useState([]); 
    const [error, setError] = useState(null); 

    /**
     * Fetches audit progress and ratings data.
     * 
     * @param {number} auditId - The ID of the audit to fetch data for.
     * @param {Function} setAudit - State setter for updating audit data (selectedAudit or secondAudit).
     * @param {string} errorMessage - Error message to display if the fetch fails.
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

    // Load all audits first
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

    // Fetch selected audit data after allAudits are loaded
    useEffect(() => {
        if (auditId && allAudits.length > 0) {
            fetchAuditData(
                auditId,
                setSelectedAudit,
                "Fehler beim Laden der Daten des ersten Audits."
            );
        }
    }, [auditId, allAudits, fetchAuditData]);

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
