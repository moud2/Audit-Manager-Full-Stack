import {Button} from "@mui/material";
import React from "react";
import Text from "../Textareas/Text.jsx";

export function CategoryDeleteForm({category, onSubmit = () => {} }) {

    return (
        <form className="flex flex-col gap-2">
            <Text data-cy="category-name">Kategorie: {category?.name}</Text>
            <Button data-cy="category-form-submit-button" onClick={onSubmit}>Delete</Button>
        </form>
    )
}