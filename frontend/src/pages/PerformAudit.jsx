import {LayoutDefault} from "../layouts/LayoutDefault.jsx";
import {useEffect, useState} from "react";
import {QuestionList} from "../components/QuestionList/QuestionList.jsx";
import api from "../api.js";
import {useParams} from "react-router-dom";

/**
 * PerformAudit Component
 *
 * This component is responsible for displaying a list of questions retrieved from the backend
 * for a specific audit. It renders a `QuestionList` component that allows users to view, edit,
 * and submit responses for each question. It handles updating questions by passing modified data
 * back to the backend via a PATCH request.
 *
 * @component
 * @returns {JSX.Element} - The rendered `PerformAudit` component wrapped within `LayoutDefault`.
 */
export function PerformAudit() {
    // Extracting the audit ID from the URL parameters using React Router's `useParams` hook
    const { auditId } = useParams();
    const [questions, setQuestions] = useState([]);
    const labels = [0, 1, 2, 3, 4, 5, "N/A"];

    /**
     * Fetches questions from the backend for the current audit on component mount
     * or when the audit ID changes.
     * It updates the `questions` state with the retrieved data.
     */    useEffect(() => {
        api.get(`/v1/audits/${auditId}/ratings`)
            .then(response => {
                setQuestions(response.data);
                console.log(questions);
            })
            .catch(err => {
                console.error('Error fetching data:', err);
            });
    }, [auditId]);

    /**
     * Handles the update of a question in the list. This function is triggered when a question's
     * rating, comment, or applicability is modified. It updates the question locally and sends
     * the change to the backend.
     *
     * @param {question[]} updatedQuestions - The updated array of questions.
     * @param {question} updatedQuestion - The specific question that was modified.
     * @returns {Promise<void>} - A promise resolving once the backend update is complete.
     */
    const handleQuestionUpdate = async (updatedQuestions, updatedQuestion) => {
        setQuestions(updatedQuestions);
        await patchQuestion(updatedQuestion.id, [
            {path: "/na", value: updatedQuestion.nA},
            {path: "/points", value: updatedQuestion.points},
            {path: "/comment", value: updatedQuestion.comment}
        ]);
    }

    /**
     * Sends a PATCH request to update a specific question's fields in the backend.
     *
     * @param {number} questionID - The ID of the question to update.
     * @param {rating[]} newRatings - An array of fields to update with their new values.
     * @returns {Promise<void>} - A promise resolving once the backend update is complete.
     */
    const patchQuestion = async (questionID, newRatings) => {
        // Transforming the ratings into a format suitable for a JSON Patch request
        const patchData = newRatings.map((destination) => ({
            op: "replace",
            path: `${destination.path}`,
            value: destination.value,
        }));
        api.patch(`/v1/ratings/${questionID}`, patchData)
            .then(response => {
                console.log(response);
            })
            .catch(err => {
                console.error('Error fetching data:', err);
            });
    };

    return (
        <LayoutDefault>
            {/*ToDo: Überschrift einfügen*/}
            <QuestionList
                questions={questions}
                options={labels}
                onChange={handleQuestionUpdate}
            />
        </LayoutDefault>
    )
}
