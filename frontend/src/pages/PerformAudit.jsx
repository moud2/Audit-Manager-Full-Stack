import {LayoutDefault} from "../layouts/LayoutDefault.jsx";
import {useEffect, useState} from "react";
import {QuestionList} from "../components/QuestionList/QuestionList.jsx";
import api from "../api.js";
import {useParams, useSearchParams} from "react-router-dom";


export function PerformAudit() {
    const labels = [0, 1, 2, 3, 4, 5, "N/A"];
    const [questions, setQuestions] = useState([]);


    // const [searchParams] = useSearchParams();
    // const auditId = searchParams.get("id");
    // console.log("Audit ID:", auditId);
    // const auditId = 54;
    const { auditId } = useParams();


    /*fetching data from the backend*/
    useEffect(() => {
        api.get(`/v1/audits/${auditId}/ratings`)
            .then(response => {
                setQuestions(response.data);
            })
            .catch(err => {
                console.error('Error fetching data:', err);
            });
    }, [auditId]);

    const handleQuestionUpdate = async (updatedQuestions, updatedQuestion) => {
        setQuestions(updatedQuestions);
        //ToDo: PatchQuestion mit Funktion versehen
        patchQuestion(updatedQuestion.id, [
            {path: "/na", value: updatedQuestion.nA},
            {path: "/points", value: updatedQuestion.points},
            {path: "/comment", value: updatedQuestion.comment}
        ]);
    }

    const patchQuestion = async (questionID, newRatings) => {
        const patchData = newRatings.map((destination) => ({
            op: "replace",
            path: `${destination.path}`,
            value: destination.value,
        }));
        console.log('hier', patchData);
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
