import React from "react";
import Text from "../Textareas/Text.jsx";

/**
 * AuditDropdown component renders a dropdown menu for selecting an audit.
 * Allows users to select a second audit to compare with the selected one.
 *
 * @param {Object} props - The props passed to the component.
 * @param {Array} props.audits - The list of available audits.
 * @param {Function} props.onAuditSelect - Callback function to handle the audit selection.
 * @returns {JSX.Element} A dropdown component for selecting an audit.
 */
export function AuditDropdown({ audits, onAuditSelect }) {
    return (
        <div className="mb-6">
            {/* Label for the dropdown */}
            <Text htmlFor="auditDropdown" className="block text-gray-700 font-bold mb-2">
                Zweites Audit auswählen:
            </Text>
            
            {/* Dropdown for selecting an audit */}
            <select
                id="auditDropdown"
                className="border rounded px-4 py-2 w-full"
                onChange={(e) => {
                    const selectedId = parseInt(e.target.value, 10); // Parse the selected value as an integer
                    const selectedAudit = audits.find(audit => audit.id === selectedId); // Find the audit by ID
                    onAuditSelect(selectedAudit); // Trigger the callback with the selected audit
                }}
            >
                <option value="">Bitte wählen</option>
                {audits.map(audit => (
                    <option key={audit.id} value={audit.id}>
                        {audit.name}
                    </option>
                ))}
            </select>
        </div>
    );
}
