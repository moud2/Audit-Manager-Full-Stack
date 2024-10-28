import {Checkbox, FormControlLabel, FormGroup} from "@mui/material";
import React, {useState} from "react";

export function CheckboxSelect({ value, options, onChange }) {

    const handleCheckboxChange = (event, label) => {
        const isChecked = event.target.checked;

        if (!isChecked) {
            onChange(null);
        } else {
            onChange(label);
        }
    }

    return (
        <FormGroup className="px-5 flex justify-center" row>
            {options.map((label) => (
                <FormControlLabel
                    key={label}
                    control={
                        <Checkbox
                            checked={value === label}
                            onChange={(event) => handleCheckboxChange(event, label) }
                        />
                    }
                    label={label.toString()}
                />
            ))}
        </FormGroup>
    )
}