import {BaseDialog} from "./BaseDialog.jsx";
import {CategoryDeleteForm} from "../CategoryForm/CategoryDeleteForm.jsx";

/**
 * Dialog for deleting a category
 *
 * @param onClose - function to call when the dialog is closed
 * @param open - boolean to determine if the dialog is open
 * @param onSubmit - function to call when the form is submitted
 * @param deleteCategory - the category to delete
 * @returns {JSX.Element} - the react element
 */
export default function DeleteCategoryDialog ({onClose, open, onSubmit, deleteCategory}) {
    return (
        <BaseDialog title="Kategorie löschen" onClose={onClose} open={open}>
            <CategoryDeleteForm 
                category={deleteCategory} 
                onSubmit={onSubmit}>
            </CategoryDeleteForm>
        </BaseDialog>
    )
}