import { LayoutDefault } from "../layouts/LayoutDefault.jsx";
import { Button } from "@mui/material";
import Title from "../components/Textareas/Title.jsx";

export function ManageCategoriesAndQuestions() {
    const handleExportQuestionsClick = () => {

    }

    const handleImportQuestionsClick = () => {

    }

    return (
        <LayoutDefault>
            <Title>Kategorien und Fragen verwalten</Title>
            <div className="flex justify-center space-x-4">
                <Button
                    data-cy="ExportQuestionsButton"
                    onClick={handleExportQuestionsClick}
                    color="primary"
                >
                    Daten exportieren
                </Button>
                <Button
                    data-cy="ImportQuestionsButton"
                    onClick={handleImportQuestionsClick}
                    color="primary"
                >
                    Daten importieren
                </Button>
            </div>
        </LayoutDefault>
    )
}