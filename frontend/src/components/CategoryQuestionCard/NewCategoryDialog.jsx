import {BaseDialog} from "./BaseDialog.jsx";

export default function NewCategoryDialog ({onClose, open}) {
    return (
        <BaseDialog title="Kategorie anlegen" onClose={onClose} open={open}>
            form hier
        </BaseDialog>
    )
}