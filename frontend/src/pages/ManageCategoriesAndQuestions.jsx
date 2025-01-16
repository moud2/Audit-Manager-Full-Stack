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
                    onClick={handleExportQuestionsClick}
                    variant="outlined"
                    color="error"
                >
                    Daten exportieren
                </Button>
                <Button
                    onClick={handleImportQuestionsClick}
                    variant="outlined"
                    color="error"
                >
                    Daten importieren
                </Button>
            </div>
        </LayoutDefault>
    )
}