import React from "react";
import { Autocomplete, TextField } from "@mui/material";

export function AuditDropdown({ audits, onAuditSelect }) {
    return (
        <div className="mb-6">
            {/* Autocomplete dropdown for selecting audits */}
            <Autocomplete
                options={audits} // List of audits to display in the dropdown
                getOptionLabel={(option) => option.name} // Defines how each option is displayed
                onChange={(event, value) => onAuditSelect(value)} // Callback when an audit is selected
                renderInput={(params) => (
                    <TextField
                        {...params}
                        label="Zweites Audit auswählen" // Label for the input field
                        variant="outlined"
                        fullWidth
                    />
                )}
                clearOnEscape // Allows clearing the input by pressing the Escape key
            />
        </div>
    );
}
