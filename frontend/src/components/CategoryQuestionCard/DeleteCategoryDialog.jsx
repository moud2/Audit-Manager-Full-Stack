import {BaseDialog} from "./BaseDialog.jsx";
import {CategoryDeleteForm} from "../CategoryForm/CategoryDeleteForm.jsx";

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