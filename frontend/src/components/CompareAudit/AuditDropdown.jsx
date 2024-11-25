import React from "react";

/**
 * AuditDropdown component for selecting an audit.
 *
 * @param {Object} props - Props for the component.
 * @param {Array} props.audits - List of audits available for selection.
 * @param {Function} props.onAuditSelect - Callback function to handle audit selection.
 * @returns {JSX.Element}
 */
export function AuditDropdown({ audits, onAuditSelect }) {
    const handleSelection = (event) => {
        const selectedAuditId = parseInt(event.target.value);
        const selectedAudit = audits.find((audit) => audit.id === selectedAuditId);
        onAuditSelect(selectedAudit || null);
    };

    return (
        <div className="relative mb-6">
            <label htmlFor="auditDropdown" className="block text-gray-700 font-semibold mb-2">
                Zweites Audit auswählen:
            </label>
            <select
                id="auditDropdown"
                className="appearance-none p-4 border-2 rounded border-gray-300 text-gray-900 text-sm w-full"
                onChange={handleSelection}
            >
                <option value="">Bitte wählen</option>
                {audits.map((audit) => (
                    <option key={audit.id} value={audit.id}>
                        {audit.name}
                    </option>
                ))}
            </select>
        </div>
    );
}
