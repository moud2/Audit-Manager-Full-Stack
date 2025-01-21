import { LayoutDefault } from "../layouts/LayoutDefault.jsx";
import { Button } from "@mui/material";
import Title from "../components/Textareas/Title.jsx";
import {useState} from "react";
import {AlertWithMessage} from "../components/ErrorHandling/index.js";

/**
 * ManageCategoriesAndQuestions Component
 *
 * This component provides a user interface for managing categories and questions.
 * It includes functionality for exporting and importing questions.
 *
 * @returns {JSX.Element} The rendered component
 * @constructor
 */
export function ManageCategoriesAndQuestions() {

    /**
     * Handle the export questions button click.
     *
     * Creates an anchor element, sets the download attribute to trigger the download
     * of the exported questions as a CSV file, and simulates a click to start the download.
     */
    const handleExportQuestionsClick = () => {
        const link = document.createElement('a');
        link.target = "_blank";
        link.href = (import.meta.env.VITE_BACKEND_URL || "/api") + "/v1/database/export";
        link.setAttribute('download', 'DatabaseExport.csv');
        document.body.appendChild(link);
        link.click();
        link.remove();
    }

    /**
     * Handle the import questions button click.
     *
     * Placeholder for functionality to import questions.
     * To be implemented in the future.
     */
    const handleImportQuestionsClick = async () => {
        // ToDo: implement functionality
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