import {LayoutDefault} from "../layouts/LayoutDefault.jsx";
import {QuestionListItem} from "../components/QuestionList/QuestionListItem.jsx";
import {useState} from "react";

export function PerformAudit() {
    const [exampleQuestion, setExampleQuestion] = useState(
        {
            question: 'Hier könnte ihre Frage stehen',
            id: 1,
            comment: 'hi',
            points: 3,
            nA: false,
        }
    );
    const labels = [0, 1, 2, 3, 4, 5, "N/A"];

    const handleQuestionUpdate = (updatedQuestion) => {
        setExampleQuestion(updatedQuestion)
    }


    return (
        <LayoutDefault>
            <QuestionListItem
                question={exampleQuestion}
                options={labels}
                onChange={handleQuestionUpdate}
            />
        </LayoutDefault>
    )
}