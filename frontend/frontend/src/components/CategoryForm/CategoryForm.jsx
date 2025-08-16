import {Button, FormControl, TextField} from "@mui/material";
import React from "react";

/**
 * The form for creating a new category
 *
 * @param value - the initial value of the form
 * @param onChange - function to call when the form value changes
 * @param onSubmit - function to call when the form is submitted
 * @returns {Element} - the react element
 */
export function CategoryForm({value, onChange = (_value) => {}, onSubmit = () => {} }) {

    const setCategory = (name) => {
        onChange({...value, name: name})
    }

    return (
        <form className="flex flex-col gap-2">
            <FormControl fullWidth>
                <TextField
                    data-cy="category-form-category-name"
                    label="Kategorie"
                    value={value.name}
                    onChange={(e) => setCategory(e.target.value)}
                    multiline
                    minRows={1}
                    fullWidth
                />
            </FormControl>
            <Button data-cy="category-form-submit-button" onClick={onSubmit}>Submit</Button>
        </form>
    )
}