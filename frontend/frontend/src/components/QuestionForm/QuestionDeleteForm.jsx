import {Button} from "@mui/material";
import React from "react";
import Text from "../Textareas/Text.jsx";

/**
 * Form for deleting a category
 *
 * @param question - the question to delete
 * @param onSubmit - function to call when the form is submitted
 * @returns {Element} - the react element
 */
export function QuestionDeleteForm({question, onSubmit = () => {}}) {
    return (
        <form className="flex flex-col gap-2" data-cy="question-form">
            <Text data-cy="question-name">Frage: {question?.name}</Text>
            <Text data-cy="category-name">Kategorie: {question?.category.name}</Text>
            <Button data-cy="question-form-submit-button" onClick={onSubmit}>Delete</Button>
        </form>)
}