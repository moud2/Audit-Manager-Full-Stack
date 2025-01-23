import {LayoutDefault} from "../layouts/LayoutDefault.jsx";
import {Button} from "@mui/material";
import Title from "../components/Textareas/Title.jsx";
import ExpandableCard from "../components/CategoryQuestionCard/ExpandableCard.jsx";
import CategoryQuestionCard from "../components/CategoryQuestionCard/CategoryQuestionCard.jsx";
import {useCallback, useEffect, useState} from "react";
import api from "../api.js";

const LazyCategoryQuestionCard = ({category}) => {
    const [questions, setQuestions] = useState([]);
    const [loading, setLoading] = useState(true);
    const [openedOnce, setOpenedOnce] = useState(false);

    const handleOpen = async () => {
        if (!openedOnce) {
            await fetchQuestions();
            setOpenedOnce(true);
        }
    }

    const fetchQuestions = useCallback(() => {
        setLoading(true);
        return api.get(`/v1/categories/${category.id}/questions`).then((response) => {
            setQuestions(response.data);
        }).finally(() => {
            setLoading(false)
        })
    }, [category])

    const handleAddQuestion = () => {
        alert('Add question for category ' + category.name);
    }

    const handleEditQuestion = (question) => {
        alert('Edit Question with id ' + question.id);
    }

    const handleDeleteQuestion = (question)=>{
        alert('Delete Question with id ' + question.id);
    }

    return (
        <CategoryQuestionCard category={category} questions={questions} onOpen={handleOpen}
                              onAddQuestion={handleAddQuestion} onEditQuestion={handleEditQuestion}
                                onDeleteQuestion={handleDeleteQuestion}>
            {loading ? <div>Loading...</div> : undefined}
        </CategoryQuestionCard>
    )
}

export function ManageCategoriesAndQuestions() {
    const handleExportQuestionsClick = () => {

    }

    const handleImportQuestionsClick = () => {

    }

    const [categories, setCategories] = useState([]);
    const [categoriesLoading, setCategoriesLoading] = useState(true);

    const fetchCategories = useCallback(() => {
        setCategoriesLoading(true);
        return api.get('/v1/categories').then((response) => {
            setCategories(response.data);
        }).finally(() => {
            setCategoriesLoading(false);
        })
    }, [])

    useEffect(() => {
        fetchCategories()
    }, [fetchCategories]);


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
            <section className="flex flex-col gap-2 max-w-6xl mx-auto">
                {categoriesLoading ? <div>Loading...</div> : categories.map((category, index) =>
                    <LazyCategoryQuestionCard key={category.id} category={category}/>)}
            </section>
        </LayoutDefault>
    )
}