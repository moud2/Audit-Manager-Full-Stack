import { LayoutDefault } from "../layouts/LayoutDefault.jsx";
import {Button, Input} from "@mui/material";
import Title from "../components/Textareas/Title.jsx";
import {useState} from "react";

export function ManageCategoriesAndQuestions() {
    const [file, setFile] = useState(null);
    const [showFileInput, setShowFileInput] = useState(false);
    const [uploadStatus, setUploadStatus] = useState('');

    const handleExportQuestionsClick = () => {

    }

    const handleFileChange = (e) => {
        console.log(e.target.files[0]);
        setFile(e.target.files[0]);
    }

    const handleFileUpload = async () => {
        const formData = new FormData();
        formData.append('file', file);

        if (!file) {
            alert("Bitte wähle eine Datei aus!");
            return;
        }

        try {
            const response = await fetch('/api/database/upload', {
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
                    <Button
                        data-cy="ExportQuestionsButton"
                        variant="outlined"
                        color="error"
                        onClick={handleExportQuestionsClick()}
                    >
                        Daten exportieren
                    </Button>

                    <Button
                        data-cy="ImportQuestionsButton"
                        variant="outlined"
                        color="error"
                        onClick={handleButtonClick}
                    >
                        {showFileInput ? "Hochladen" : "Daten importieren"}
                    </Button>
                </div>

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
