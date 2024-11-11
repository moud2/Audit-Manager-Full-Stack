import {QuestionListItem} from "./QuestionListItem.jsx";

/**
 * QuestionList Component
 *
 * This component is responsible for rendering a list of questions, each represented as a `QuestionListItem`.
 * It receives an array of questions and options for each question and handles updates through a callback function.
 * When a question is updated, it updates the question array and triggers the parent `onChange` handler to pass
 * the modified question list back up.
 *
 * @component
 * @param {Object[]} questions - Array of question objects to be displayed. * @param options
 * @param {Function} onChange - Callback function that triggers when a question is updated. This function takes an
 * updated array of questions and the question ID as its argument.
 * @returns {JSX.Element} - The rendered `QuestionList` component containing a list of `QuestionListItem` components.
 * @constructor
 */
export function QuestionList({ questions, options, onChange }){
    console.log('QuestionList Aufruf mit Fragen:', questions);
    console.log(options);

    /**
     * handleQuestionChange
     *
     * This function is triggered when a question is modified. It maps over the question array,
     * finds the question with the matching ID, and updates its values with the new data provided
     * in `updatedQuestion`. It then passes the modified question list back to the parent via `onChange`.
     *
     * @param {number} id - The unique ID of the question to update.
     * @param {Object} updatedQuestion - An object containing the fields to update for the question.
     */
    const handleQuestionChange = (id, updatedQuestion) => {
        console.log(id);
        const newQuestions = questions.map((question) => id === question.id ? {... question, ...updatedQuestion} : question);
        // console.log('neue Fragen:' + questions);
        onChange(newQuestions, updatedQuestion);
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