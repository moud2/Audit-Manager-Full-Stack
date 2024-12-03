import React from "react";

export function AuditDropdown({ audits, onAuditSelect }) {
    return (
        <div className="mb-6">
            <label htmlFor="auditDropdown" className="block text-gray-700 font-bold mb-2">
                Zweites Audit auswählen:
            </label>
            <select
                id="auditDropdown"
                className="border rounded px-4 py-2 w-full"
                onChange={(e) => {
                    const selectedId = parseInt(e.target.value);
                    const selectedAudit = audits.find(audit => audit.id === selectedId);
                    onAuditSelect(selectedAudit);
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
