import {LayoutDefault} from "../layouts/LayoutDefault.jsx";
import {QuestionListItem} from "../components/QuestionList/QuestionListItem.jsx";
import {useState} from "react";
import {QuestionList} from "../components/QuestionList/QuestionList.jsx";

export function PerformAudit() {
    const [exampleQuestions, setExampleQuestions] = useState(
        [{
            question: 'Hier könnte ihre Frage stehen',
            id: 1,
            comment: 'hi',
            points: 3,
            nA: false,
        },{
            question: '2. sehr wichtige Frage',
            id: 2,
            comment: 'wichtiger Kommentar',
            points: 1,
            nA: false,
        }
        ]
    );
    const labels = [0, 1, 2, 3, 4, 5, "N/A"];

    const handleQuestionUpdate = (updatedQuestions) => {
        setExampleQuestions(updatedQuestions)
    }


    return (
        <LayoutDefault>
            <QuestionList
                questions={exampleQuestions}
                options={labels}
                onChange={handleQuestionUpdate}
            />
        </LayoutDefault>
    )
}