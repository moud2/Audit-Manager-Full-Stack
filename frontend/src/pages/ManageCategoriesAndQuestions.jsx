import { LayoutDefault } from "../layouts/LayoutDefault.jsx";
import { Button, Input } from "@mui/material";
import Title from "../components/Textareas/Title.jsx";
import { useState } from "react";
import { CustomAlert } from "../components/ErrorHandling/CustomAlert";

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
    const [errorMessage, setErrorMessage] = useState(null);
    const [successMessage, setSuccessMessage] = useState(null);

    /**
     * Handle the export questions button click.
     */
    const handleExportQuestionsClick = async () => {
        try {
            const response = await fetch((import.meta.env.VITE_BACKEND_URL || "/api") + "/v1/database/export");
    
            if (!response.ok) {
                throw new Error("Fehler beim Export der Daten. Der Server hat nicht erfolgreich geantwortet.");
            }
    
            const blob = await response.blob();
            const link = document.createElement("a");
            link.href = window.URL.createObjectURL(blob);
            link.setAttribute("download", "DatabaseExport.csv");
            document.body.appendChild(link);
            link.click();
            link.remove();
    
            setSuccessMessage("Daten wurden erfolgreich exportiert.");
            setErrorMessage(null); 
        } catch (error) {
            setErrorMessage(error.message || "Ein unbekannter Fehler ist aufgetreten.");
            setSuccessMessage(null); 
        }
    };
    

    /**
     * Handles the file selection event and sets the selected file to the state.
     */
    const handleFileChange = (e) => {
        setFile(e.target.files[0]);
    };

    /**
     * Uploads the selected file to the backend using a POST request.
     */
    const handleFileUpload = async () => {
        if (!file) {
            setErrorMessage("Bitte wählen Sie eine Datei aus!");
            setSuccessMessage(null);
            return;
        }

        const formData = new FormData();
        formData.append("file", file);

        try {
            const response = await fetch(((import.meta.env.VITE_BACKEND_URL || "/api") + "/v1/database/import"), {
                method: "POST",
                body: formData,
            });

            if (response.ok) {
                setSuccessMessage("Upload erfolgreich!");
                setErrorMessage(null);
            } else {
                setErrorMessage("Upload fehlgeschlagen.");
                setSuccessMessage(null);
            }
        } catch (error) {
            setErrorMessage("Ein Fehler ist beim Hochladen aufgetreten.");
            setSuccessMessage(null);
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
            {/* Fehler-Alert */}
            <CustomAlert
                show={!!errorMessage}
                severity="error"
                message={errorMessage}
                onClose={() => setErrorMessage(null)}
                sx={{
                    backgroundColor: "#f8d7da",
                    color: "#721c24",
                    border: "1px solid #f5c6cb",
                }}
            />
            {/* Erfolgs-Alert */}
            <CustomAlert
                show={!!successMessage}
                severity="success"
                message={successMessage}
                onClose={() => setSuccessMessage(null)}
            />
            <div className="flex flex-col items-center space-y-4">
                <div className="flex justify-center space-x-4">
                    <Button
                        data-cy="ExportQuestionsButton"
                        onClick={handleExportQuestionsClick}
                    >
                        Daten exportieren
                    </Button>
                    <Button
                        data-cy="ImportQuestionsButton"
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
            </div>
        </LayoutDefault>
    );
}
