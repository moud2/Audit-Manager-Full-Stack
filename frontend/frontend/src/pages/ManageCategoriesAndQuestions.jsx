import { LayoutDefault } from "../layouts/LayoutDefault.jsx";
import { Button } from "@mui/material";
import Title from "../components/Textareas/Title.jsx";
import { CustomAlert } from "../components/ErrorHandling/CustomAlert";
import CategoryQuestionCard from "../components/CategoryQuestionCard/CategoryQuestionCard.jsx";
import { Fragment, useCallback, useEffect, useState } from "react";
import api from "../api.js";
import NewQuestionDialog from "../components/CategoryQuestionCard/NewQuestionDialog.jsx";
import AddIcon from "@mui/icons-material/Add";
import NewCategoryDialog from "../components/CategoryQuestionCard/NewCategoryDialog.jsx";
import LoadingScreen from "../components/LoadingState/LoadingScreen";
import { useLoadingProgress } from "../components/LoadingState/useLoadingProgress";
import { CircularProgress } from "@mui/material";
import DeleteQuestionDialog from "../components/CategoryQuestionCard/DeleteQuestionDialog.jsx";
import DeleteCategoryDialog from "../components/CategoryQuestionCard/DeleteCategoryDialog.jsx";


const LazyCategoryQuestionCard = ({ category, availableCategories = [], onDelete, setErrorMessage, setSuccessMessage }) => {
    const [questions, setQuestions] = useState([]);
    const [loading, setLoading] = useState(true);
    const [openedOnce, setOpenedOnce] = useState(false);
    const [newQuestionDialogOpen, setNewQuestionDialogOpen] = useState(false);
    const [deleteQuestionDialogOpen, setDeleteQuestionDialogOpen] = useState(false);
    const [deleteQuestion, setDeleteQuestion] = useState();
    const [deleteCategoryDialogOpen, setDeleteCategoryDialogOpen] = useState(false);
    const [deleteCategory, setDeleteCategory] = useState();
    

    const handleOpen = async () => {
        if (!openedOnce) {
            await fetchQuestions();
            setOpenedOnce(true);
        }
    };

    const fetchQuestions = useCallback(() => {
        setLoading(true);
        return api.get(`/v1/categories/${category.id}/questions`)
            .then((response) => {
                setQuestions(response.data);
            })
            .catch(() => {
                setErrorMessage("Fehler beim Laden der Fragen.");
            })
            .finally(() => {
                setLoading(false);
            });
    }, [category, setErrorMessage]);

    const handleAddQuestion = () => {
        setNewQuestionDialogOpen(true);
    };

    const handleDeleteQuestion = (question) => {
        setDeleteQuestionDialogOpen(true)
        setDeleteQuestion(question)
    }

    const handleDeleteCategory = (category) => {
        setDeleteCategoryDialogOpen(true)
        setDeleteCategory(category)
    }


    function handleCreate(newQuestion) {
        api.post("/v1/questions/new", {
            categoryId: newQuestion.category,
            name: newQuestion.name,
        })
            .then((response) => {
                if (newQuestion.category !== category.id) return;
                setQuestions?.((oldQuestions) => [...oldQuestions, response.data]);
                setSuccessMessage("Frage erfolgreich hinzugefügt.");
            })
            .catch(() => {
                setErrorMessage("Fehler beim Erstellen der Frage.");
            })
            .finally(() => {
                setNewQuestionDialogOpen(false);
            });
    }

    function handleDeleteQues(deleteQuestion) {
        api.delete(`/v1/questions/${deleteQuestion.id}`, {}).then(response => {
            setQuestions?.((oldQuestions) => oldQuestions.filter(question => question.id !== deleteQuestion.id));
            setSuccessMessage("Frage wurde erfolgreich gelöscht.");
        }).catch(err => {
            setErrorMessage("Fehler beim Löschen der Frage.");
        }).finally(() => {
            setDeleteQuestionDialogOpen(false);
        })
    }

    return (
        <Fragment>
            <NewQuestionDialog open={newQuestionDialogOpen} initialCategory={category.id}
                               availableCategories={availableCategories}
                               onSubmit={handleCreate}
                               onClose={() => setNewQuestionDialogOpen(false)}></NewQuestionDialog>
            <DeleteQuestionDialog open={deleteQuestionDialogOpen}
                                  deleteQuestion={deleteQuestion}
                                  onSubmit={handleDeleteQues}
                                  onClose={() => setDeleteQuestionDialogOpen(false)}></DeleteQuestionDialog>
            <DeleteCategoryDialog open={deleteCategoryDialogOpen}
                                  deleteCategory={deleteCategory}
                                  onSubmit={onDelete}
                                  onClose={() => setDeleteCategoryDialogOpen(false)}></DeleteCategoryDialog>
            <CategoryQuestionCard category={category} questions={questions} onOpen={handleOpen}
                                  onAddQuestion={handleAddQuestion}
                                  onDeleteCategory={handleDeleteCategory}
                                  onDeleteQuestion={handleDeleteQuestion}>
                {loading ? <CircularProgress size={24} color="error" /> : null}
            </CategoryQuestionCard>
        </Fragment>
    );
}

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
    const [categories, setCategories] = useState([]);
    const [categoriesLoading, setCategoriesLoading] = useState(true);
    const categoryLoadingProgress = useLoadingProgress(categoriesLoading);

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
            setErrorMessage("Ein unbekannter Fehler ist aufgetreten.");
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
            const response = await fetch((import.meta.env.VITE_BACKEND_URL || "/api") + "/v1/database/import", {
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
            setShowFileInput(false);
        } else {
            setShowFileInput(true);
        }
    };


    const fetchCategories = useCallback(() => {
        setCategoriesLoading(true);
        return api.get("/v1/categories")
            .then((response) => {
                setCategories(response.data);
            })
            .catch(() => {
                setErrorMessage("Fehler beim Laden der Kategorien.");
            })
            .finally(() => {
                setCategoriesLoading(false);
            });
    }, []);

    useEffect(() => {
        fetchCategories();
    }, [fetchCategories]);

    const handleAddedQuestion = () => {
        fetchCategories();
    };

    const [newCategoryDialogOpen, setNewCategoryDialogOpen] = useState(false);

    const handleNewCategory = (category) => {
        api.post("/v1/categories/new", {
            name: category.name,
        })
        .then((response) => {
            setCategories((oldCategories) => [...oldCategories, response.data]);
            setSuccessMessage("Kategorie erfolgreich hinzugefügt.");
        })
        .catch(() => {
            setErrorMessage("Fehler beim Erstellen der Kategorie.");
        })
        .finally(() => {
            setNewCategoryDialogOpen(false);
        });
    };

    const handleDeleteCategory = (category) => {
        api.delete(`/v1/categories/${category.id}`, {}).then(() => {
            setCategories?.((oldCategories) => oldCategories.filter(c => c.id !== category.id));
            setSuccessMessage("Kategorie wurde erfolgreich gelöscht.");
        }).catch(() => {
            setErrorMessage("Fehler beim Löschen der Kategorie.");
        })
    };

    return (
        <LayoutDefault>
            <Title>Kategorien und Fragen verwalten</Title>
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
                        <input
                        type="file"
                        accept=".csv"
                        onChange={handleFileChange}
                        />
                    </div>
                )}
            </div>
            <section className="flex flex-col gap-2 max-w-6xl mx-auto p-2">
                <NewCategoryDialog
                    open={newCategoryDialogOpen}
                    onClose={() => setNewCategoryDialogOpen(false)}
                    onSubmit={handleNewCategory}
                />
                <Button
                    fullWidth
                    startIcon={<AddIcon />}
                    onClick={() => setNewCategoryDialogOpen(true)}
                >
                    Kategorie hinzufügen
                </Button>
                {categoriesLoading ? (
                    <LoadingScreen progress={categoryLoadingProgress} message="Kategorien werden geladen..." small />
                ) : (
                    categories.map((category) => (
                        <LazyCategoryQuestionCard
                            key={category.id}
                            category={category}
                            availableCategories={categories}
                            onAddedQuestion={handleAddedQuestion}
                            onDelete={() => handleDeleteCategory(category)}
                            setErrorMessage={setErrorMessage}
                            setSuccessMessage={setSuccessMessage}
                        />
                    ))
                )}
            </section>
        </LayoutDefault>
    );
}
