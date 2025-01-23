import { LayoutDefault } from "../layouts/LayoutDefault.jsx";
import { Button } from "@mui/material";
import Title from "../components/Textareas/Title.jsx";
import {useState} from "react";


export function ManageCategoriesAndQuestions() {
    const handleExportQuestionsClick = () => {

    }

    const [file, setFile] = useState(null);
    const [uploadStatus, setUploadStatus] = useState('');


    const handleFileAuswahl = (e) => {
        console.log(e.target.files[0]);
        setFile(e.target.files[0]);
    }

    const handleFileUpload = async () => {
        const formData = new FormData();
        formData.append('file', file);

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
                    // onClick={handleImportQuestionsClick}
                    variant="outlined"
                    color="error"
                >
                    Daten importieren
                </Button>
                <input type="file" accept=".csv" onChange={e => handleFileAuswahl(e)}/>
                <button onClick={handleFileUpload}>Hochladen</button>
                {uploadStatus && <p>{uploadStatus}</p>}
            </div>
        </LayoutDefault>
    )
}
