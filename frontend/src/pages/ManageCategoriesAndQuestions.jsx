import { LayoutDefault } from "../layouts/LayoutDefault.jsx";
import {Button, Input} from "@mui/material";
import Title from "../components/Textareas/Title.jsx";
import {useState} from "react";

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
    const [file, setFile] = useState(null);
    const [showFileInput, setShowFileInput] = useState(false);
    const [uploadStatus, setUploadStatus] = useState('');


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
     * Handles the file selection event and sets the selected file to the state.
     *
     * @param {Event} e - The event triggered by the file input.
     */
    const handleFileChange = (e) => {
        setFile(e.target.files[0]);
    }

    /**
     * Uploads the selected file to the backend using a POST request.
     *
     * @returns {Promise<void>} A promise that resolves when the upload is completed.
     */
    const handleFileUpload = async () => {
        const formData = new FormData();
        formData.append('file', file);

        if (!file) {
            alert("Bitte wähle eine Datei aus!");
            return;
        }

        try {
            const response = await fetch('http://localhost:8080/api/v1/database/import', {
                method: 'POST',
                body: formData,
            });

            if (response.ok) {
                setUploadStatus('Upload erfolgreich!');
            } else {
                setUploadStatus('Upload fehlgeschlagen.');
            }
        } catch (error) {
            console.error('Fehler beim Hochladen:', error);
            setUploadStatus('Ein Fehler ist aufgetreten.');
        }
    };

    /**
     * Toggles the file input visibility and triggers the file upload process
     * if the file input is already visible.
     */
    const handleButtonClick = () => {
        if (showFileInput) {
            handleFileUpload();
        } else {
            setShowFileInput(true);
        }
    };


    return (
        <LayoutDefault>
            <Title>Kategorien und Fragen verwalten</Title>
            <div className="flex flex-col items-center space-y-4">
                <div className="flex justify-center space-x-4">

                    {/*Daten exportieren Button*/}
                    <Button
                        data-cy="ExportQuestionsButton"
                        variant="outlined"
                        color="error"
                        onClick={handleExportQuestionsClick}
                    >
                        Daten exportieren
                    </Button>

                    {/*Button changes from "Daten importieren" to "Hochladen" after clicking */}
                    <Button
                        data-cy="ImportQuestionsButton"
                        variant="outlined"
                        color="error"
                        onClick={handleButtonClick}
                    >
                        {showFileInput ? "Hochladen" : "Daten importieren"}
                    </Button>
                </div>

                {/*File input is only shown after clicking the "Daten importieren" Button*/}
                {showFileInput && (
                    <div>
                        <Input
                            type="file"
                            accept=".csv"
                            onChange={handleFileChange}
                        />
                    </div>
                )}

                {uploadStatus && <p className="text-sm text-red-600">{uploadStatus}</p>}
            </div>
        </LayoutDefault>
    )
}