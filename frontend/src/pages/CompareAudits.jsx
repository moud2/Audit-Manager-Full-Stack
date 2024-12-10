import React, { useState, useEffect, useCallback } from "react";
import { useParams } from 'react-router-dom';
import { LayoutDefault } from "../layouts/LayoutDefault.jsx";
import { AuditDropdown } from "../components/CompareAudit/AuditDropdown.jsx";
import { AuditComparisonCard } from "../components/CompareAudit/AuditComparisonCard.jsx";
import api from "../api";
import Title from "../components/Textareas/Title.jsx";

/**
 * CompareAudits Component
 * 
 * Allows the user to compare two audits by fetching their progress and ratings data.
 * Displays a dropdown for selecting a second audit and comparison cards for both audits.
 * 
 * @returns {JSX.Element} The rendered CompareAudits page.
 */
export function CompareAudits() {
    const { auditId } = useParams(); 
    const [selectedAudit, setSelectedAudit] = useState(null); 
    const [secondAudit, setSecondAudit] = useState(null); 
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
            const auditName =
                allAudits.find(a => a.id === parseInt(auditId))?.name || `Audit ${auditId}`;
            
            const categoryProgressArray = (progressResponse.data.categoryProgress || []).map(category => ({
                name: category.categoryName,
                progress: category.currentCategoryProgress,
            }));

            const auditData = {
                id: auditId,
                name: auditName,
                overallProgress: progressResponse.data.currentAuditProgress || 0,
                categoryProgress: categoryProgressArray,
            };

            const ratingsResponse = await api.get(`/v1/audits/${auditId}/ratings`);
            const distribution = [0, 0, 0, 0, 0, 0, 0];
            ratingsResponse.data.forEach(rating => {
                if (rating.na === true) {
                    distribution[6]++;
                } else if (rating.points !== null) {
                    distribution[rating.points]++;
                } else {
                    distribution[6]++;
                }
            });

            setAudit({
                ...auditData,
                distribution,
            });
        } catch {
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

                {/* Dropdown for selecting the second audit */}
                <AuditDropdown
                    audits={allAudits.filter(audit => audit.id !== selectedAudit?.id)}
                    onAuditSelect={handleAuditSelect}
                />

                {/* Display comparison cards for the selected and second audits */}
                <div className="grid grid-cols-2 gap-6">
                    {selectedAudit && (
                        <AuditComparisonCard
                            name={selectedAudit.name}
                            progress={selectedAudit.overallProgress}
                            categories={selectedAudit.categoryProgress}
                            distribution={selectedAudit.distribution}
                        />
                    )}
                    {secondAudit && (
                        <AuditComparisonCard
                            name={secondAudit.name}
                            progress={secondAudit.overallProgress}
                            categories={secondAudit.categoryProgress}
                            distribution={secondAudit.distribution}
                        />
                    )}
                </div>
            </div>
        </LayoutDefault>
    );
}
