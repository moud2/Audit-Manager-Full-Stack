import React, { useState, useEffect } from "react";
import api from "../api.js";
import { LayoutDefault } from "../layouts/LayoutDefault.jsx";
import { Table } from "../components/Table/Table.jsx";
import { useNavigate } from "react-router-dom";
import { TextField, Button } from "@mui/material";
import Title from "../components/Textareas/Title.jsx";
import { handleApiError } from "../utils/handleApiError";
import { LoadingScreen } from "../components/LoadingState";
import { AlertWithMessage } from "../components/ErrorHandling";
import { useLoadingProgress } from "../components/LoadingState/useLoadingProgress";
import { CustomAlert } from "../components/ErrorHandling/CustomAlert";


export function NewAudit() {
    const [cards, setCards] = useState([]);
    const [selectedCategories, setSelectedCategories] = useState([]);
    const [name, setName] = useState("");
    const [customerName, setCustomerName] = useState("");
    const [loading, setLoading] = useState(true);
    const [processing, setProcessing] = useState(false);
    const [error, setError] = useState(null);
    const [validationError, setValidationError] = useState(null); // Validation messages
    const [successMessage, setSuccessMessage] = useState(null); // Success message
    const navigate = useNavigate();

    // Use the custom loading progress hook
    const loadingProgress = useLoadingProgress(loading);

    const handleCreateAuditClick = () => {
        // Validate inputs
        if (!name || !customerName) {
            setValidationError("Bitte geben Sie sowohl einen Audit-Namen als auch einen Firmennamen ein.");
            return;
        }

        if (selectedCategories.length === 0) {
            setValidationError("Bitte wählen Sie mindestens eine Kategorie aus.");
            return;
        }

        setProcessing(true);
        api
            .post("/v1/audits/new", {
                name: name,
                customer: customerName,
                categories: selectedCategories,
            })
            .then((response) => {
                setSuccessMessage(`Audit "${response.data.name}" wurde erfolgreich erstellt!`);

                setTimeout(() => {
                    setProcessing(false);
                    setLoading(true);
                }, 2000);

                setError(null); // Clear any previous errors

                // Navigate after timeout
                setTimeout(() => {
                    navigate("/perform-audit/" + response.data.id);
                    setLoading(false);
                }, 2500);
            })
            .catch((err) => {
                const errorMessage = handleApiError(err); // Use handleApiError
                setError(errorMessage);
                setLoading(false);
            })
    };

    const handleNameChange = (e) => {
        setName(e.target.value);
        setValidationError(null); // Clear validation error when user types
    };

    const handleCustomerNameChange = (e) => {
        setCustomerName(e.target.value);
        setValidationError(null); // Clear validation error when user types
    };

    useEffect(() => {
        setLoading(true); // Start loading when fetching categories
        api
            .get("/v1/categories")
            .then((response) => {
                const categories = response.data.map((category) => ({
                    id: category.id.toString(),
                    title: category.name,
                }));
                setCards(categories);
                setError(null); // Clear any previous errors
            })
            .catch((err) => {
                const errorMessage = handleApiError(err); // Use handleApiError
                setError(errorMessage);
            })
            .finally(() => setLoading(false)); // End loading
    }, []);

    if (loading) {
        return <LoadingScreen progress={loadingProgress} message="Daten werden geladen..." />;
    }


    return (
        <LayoutDefault>
            <div>
                {error && <AlertWithMessage severity="error" title="Fehler" message={error} />} {/* Fehleranzeige */}
                <Title>Neues Audit anlegen</Title>

                {/* Validation Alert */}
                <CustomAlert
                    show={!!validationError}
                    severity="error"
                    message={validationError}
                    onClose={() => setValidationError(null)}
                    sx={{
                        backgroundColor: "#f8d7da",
                        color: "#721c24",
                        border: "1px solid #f5c6cb",
                    }}
                />

                {/* Success Alert */}
                <CustomAlert
                    show={!!successMessage}
                    severity="success"
                    message={successMessage}
                    onClose={() => setSuccessMessage(null)}
                />  

                <form className="flex flex-col items-center w-full mb-8">
                    <div className="audit-name-field w-full max-w-xs ml-12">
                        <TextField
                            label="Audit Name"
                            value={name}
                            onChange={handleNameChange}
                        />
                    </div>
                    <div className="customer-name-field w-full max-w-xs mt-4 ml-12">
                        <TextField
                            label="Firmenname"
                            value={customerName}
                            onChange={handleCustomerNameChange}
                        />
                    </div>
                </form>
                <Table
                    value={selectedCategories}
                    options={cards}
                    onChange={(newSelectedCategories) => {
                        setSelectedCategories(newSelectedCategories);
                        if (newSelectedCategories.length > 0) {
                            setValidationError(null); // Ignore the error, when minimum one category is selected
                        }
                    }}
                />
                <div className="flex justify-end">
                    <Button
                        onClick={handleCreateAuditClick}
                        disabled={processing}
                    >
                        Audit erstellen
                    </Button>
                </div>
            </div>
        </LayoutDefault>
    );
}
