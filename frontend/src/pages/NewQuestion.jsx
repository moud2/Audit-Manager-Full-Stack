import {LayoutDefault} from "../layouts/LayoutDefault.jsx";
import React, {useEffect, useState} from "react";
import api from "../api.js";
import {QuestionForm} from "../components/QuestionForm/QuestionForm.jsx";

export function NewQuestion() {
    const [categoryOptions, setCategoryOptions] = useState([]);

    const [category, setCategory] = useState("");
    const [question, setQuestion] = useState("");

    const [loading, setLoading] = useState(true);

    useEffect(() => {
        setLoading(true)
        api.get('/v1/categories').then(response => {
            setCategoryOptions(response.data);
        }).finally(() => {
            setLoading(false)
        })
    }, []);


    const handleSubmit = () => {
        api.post('/v1/questions/new', {
            category: category,
            name: question
        }).then(response => {
            alert('Question created successfully')
            setQuestion("")
            setCategory("")
        }).catch(err => {
            alert('Error creating question')
        })
    }

    const handleChange = ({name, category}) => {
        setQuestion(name)
        setCategory(category)
    }

    return (
        <LayoutDefault>
            <div>
                <h1>New Question</h1>
                {!loading &&
                    <QuestionForm
                        value={{name: question, category: category}}
                        categoryOptions={categoryOptions}
                        onChange={handleChange}
                        onSubmit={handleSubmit}
                    >
                    </QuestionForm>
                }
                {loading && <p>Loading...</p>}
            </div>
        </LayoutDefault>
    )
}