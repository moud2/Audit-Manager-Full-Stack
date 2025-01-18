import { LayoutDefault } from "../layouts/LayoutDefault.jsx";
import { Button } from "@mui/material";
import Title from "../components/Textareas/Title.jsx";
import {useState} from "react";
import {AlertWithMessage} from "../components/ErrorHandling/index.js";

export function ManageCategoriesAndQuestions() {
    const [error, setError] = useState(null);

    if (error) {
        return <AlertWithMessage severity="error" title="Fehler" message={error}/>;
    }

    const handleExportQuestionsClick = () => {
        try {
            const link = document.createElement('a');
            link.href = (import.meta.env.VITE_BACKEND_URL || "/api") + "/v1/database/export";
            link.setAttribute('download', 'DatabaseExport.csv');
            document.body.appendChild(link);
            link.click();
            link.remove();
        } catch (error) {
            console.error('Error exporting questions:', error);
            setError(error);
        }
    }

    const handleImportQuestionsClick = async () => {

    }

    return (
        <LayoutDefault>
            <Title>Kategorien und Fragen verwalten</Title>
            <div className="flex justify-center space-x-4">
                <Button
                    data-cy="ExportQuestionsButton"
                    onClick={handleExportQuestionsClick}
                    variant="outlined"
                    color="error"
                >
                    Daten exportieren
                </Button>
                <Button
                    data-cy="ImportQuestionsButton"
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