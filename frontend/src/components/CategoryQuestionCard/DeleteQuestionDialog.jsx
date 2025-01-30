import {BaseDialog} from "./BaseDialog.jsx";
import {QuestionDeleteForm} from "../QuestionForm/QuestionDeleteForm.jsx";
import {useState} from "react";

/**
 * The dialog for deleting a question
 *
 * @param onClose - function to call when the dialog is closed
 * @param open - boolean to determine if the dialog is open
 * @param onSubmit - function to call when the form is submitted
 * @param deleteQuestion - the question to delete
 * @returns {JSX.Element} - the react element
 */
export default function DeleteQuestionDialog ({onClose, open, onSubmit, deleteQuestion}) {
    return (
        <BaseDialog title="Frage löschen" onClose={onClose} open={open}>
            <QuestionDeleteForm 
                question={deleteQuestion} 
                onSubmit={()=>onSubmit(deleteQuestion)}>
            </QuestionDeleteForm>
        </BaseDialog>
    )
}