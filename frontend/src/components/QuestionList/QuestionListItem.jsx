import { FormGroup} from "@mui/material";
import React, {useMemo} from "react";
import {CheckboxSelect} from "./CheckboxSelect.jsx";
import { TextareaAutosize as BaseTextareaAutosize } from '@mui/base/TextareaAutosize';
import { styled } from '@mui/system';

/**
 * QuestionListItem-Komponente
 *
 * Diese Komponente rendert eine Checkbox-Liste und ein Kommentarfeld, die auf den übergebenen Frage- und Optionsdaten basieren.
 * Die Komponente enthält Event-Handler, um Änderungen an den Checkboxen und dem Kommentarfeld zu erfassen und weiterzugeben.
 *
 * @component
 * @param {Object} props - Die Eigenschaften der Komponente.
 * @param {Object} props.question - Ein Objekt, das die Frage und zugehörige Daten wie Punkte und Kommentar enthält.
 * @param {Array.<string>} props.options - Eine Liste von Labeln für die Checkbox-Optionen.
 * @param {Function} props.onChange - Eine Callback-Funktion, die aufgerufen wird, wenn eine Änderung in der Checkbox oder im Kommentar erfolgt.
 * @returns {JSX.Element} - Die gerenderte QuestionListItem-Komponente.
 */
export function QuestionListItem({ question, options, onChange }) {
    //Styling for the Comment Sections
    const Textarea = useMemo(()=>styled(BaseTextareaAutosize)(
        ({ theme }) => `
    box-sizing: border-box;
    width: 95%;
    margin: 2.5%;
    font-family: 'IBM Plex Sans', sans-serif;
    font-size: 0.875rem;
    font-weight: 400;
    line-height: 1.5;
    padding: 12px;
    border-radius: 12px 12px 0 12px;
    color: ${theme.palette.mode === 'dark' ? '#fff' : '#000'};
    background: ${theme.palette.mode === 'dark' ? '#000' : '#fff'};
    border: 1px solid ${theme.palette.mode === 'dark' ? '#555' : '#ddd'};
    &:hover {
      border-color: #3399FF;
    }
    &:focus {
      outline: 0;
      border-color: #3399FF;
      box-shadow: 0 0 0 3px rgba(0, 123, 255, 0.25);
    }
    &:focus-visible {
      outline: 0;
    }
  `
    ), []);

    /**
     *
     * @param {string} value - Der Wert der ausgewählten Checkbox.
     * @param {boolean} isChecked - Gibt an, ob die Checkbox ausgewählt wurde oder nicht (Wert kommt von der CheckboxSelect Komponente bei Änderung).
     */
    const handleCheckboxChange = (value, isChecked) => {
        console.log("value: ", value, " isChecked: ", isChecked);

        if (isChecked) {
            if (value === 'N/A') {
                onChange({...question, points: null, nA: true});
            } else {
                onChange({...question, points: value, nA: false});
            }
        } else {
            onChange({...question, points: null, nA: null});
        }
    }

    /**
     * Event-Handler für Änderungen im Kommentarfeld.
     * Aktualisiert das Frageobjekt mit dem neuen Kommentartext.
     *
     * @param {Object} event - Das Eingabe-Ereignis.
     */
    const handleCommentInput = (event) => {
        const newComment = event.target.value;
        onChange({...question, comment: newComment});
    }

    /**
     * Ermittelt den Wert für die Checkbox-Auswahl.
     * Gibt 'N/A' zurück, wenn keine Auswahl vorhanden ist.
     *
     * @returns {string | null} - Die aktuelle Bewertung der Frage, Punktwert, 'N/A' oder null.
     */
    const insertValue = () => {
        if (question.nA === true) {
            return 'N/A';
        } else {
            return question.points;
        }
    }

    return (
        <div key={question.id}>
            <h2 className="px-10 py-5">{question.question}</h2>
            <FormGroup className="px-5 flex justify-center" row>
                <CheckboxSelect
                    value={insertValue()}
                    options={options}
                    onChange={handleCheckboxChange}
                />
            </FormGroup>
            <Textarea
                placeholder='Kommentar eingeben'
                value={question.comment}
                onChange={(event) => handleCommentInput(event)}
            />
        </div>
    )
}