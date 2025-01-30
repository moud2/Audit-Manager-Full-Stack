import {Button} from "@mui/material";
import React from "react";
import Text from "../Textareas/Text.jsx";

/**
 * Form for deleting a category
 *
 * @param category - the category to delete
 * @param onSubmit - function to call when the form is submitted
 * @returns {Element} - the react element
 */
export function CategoryDeleteForm({category, onSubmit = () => {} }) {

    return (
        <form className="flex flex-col gap-2" data-cy="category-form">
            <Text>Kategorie: {category?.name}</Text>
            <Button data-cy="category-form-submit-button" onClick={onSubmit}>Delete</Button>
        </form>
    )
}