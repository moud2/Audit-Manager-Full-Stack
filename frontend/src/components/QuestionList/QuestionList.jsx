import {QuestionListItem} from "./QuestionListItem.jsx";

export function QuestionList({ questions, options, onChange }){
    console.log('QuestionList Aufruf mit Fragen:', questions);
    console.log(options);

    const handleQuestionChange = (id, updatedQuestion) => {
        console.log(id);
        const newQuestions = questions.map((question) => id === question.id ? {... question, ...updatedQuestion} : question);
        console.log('neue Fragen:' + questions);
        onChange(newQuestions);
    }

    return (                                                                  
        <div>
            {questions.map((question) => (
                <div key={question.id}>
                    <QuestionListItem
                        question={question}
                        options={options}
                        onChange={handleQuestionChange}
                    />
                </div>
            ))}
        </div>
    )
}