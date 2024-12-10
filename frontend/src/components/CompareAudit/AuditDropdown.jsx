import React from "react";
import { Autocomplete, TextField } from "@mui/material";

export function AuditDropdown({ audits, onAuditSelect }) {
    return (
        <div className="mb-6">
            <Autocomplete
                options={audits} // Daten für Dropdown
                getOptionLabel={(option) => option.name} // Wie die Optionen angezeigt werden
                onChange={(event, value) => onAuditSelect(value)} // Rückgabe der Auswahl
                renderInput={(params) => (
                    <TextField
                        {...params}
                        label="Zweites Audit auswählen"
                        variant="outlined"
                        fullWidth
                    />
                )}
                clearOnEscape
            />
        </div>
    );
}
