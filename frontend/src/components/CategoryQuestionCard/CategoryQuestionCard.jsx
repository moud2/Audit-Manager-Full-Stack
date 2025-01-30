import ExpandableCard from "./ExpandableCard.jsx";
import QuestionTable from "./QuestionTable.jsx";

/**
 * A card that displays a category and its questions
 *
 * @param onOpen - function to call when the card is expanded
 * @param children - the children to render when expanded
 * @param questions - the questions to display
 * @param category - the category to display
 * @param onAddQuestion - function to call when a new question is added
 * @param onDeleteQuestion - function to call when a question is deleted
 * @param onDeleteCategory - function to call when a category is deleted
 * @returns {JSX.Element} - the react element
 */
export default function CategoryQuestionCard({onOpen, children, questions, category, onAddQuestion, onDeleteQuestion, onDeleteCategory}) {

    const handleExpandChange = (expanded) => {
        if(expanded) {
            onOpen?.();
        }
    }

    const handleDeleteCategory = ()=>{
        onDeleteCategory?.(category)
    }

    const handleNew = () => {
        onAddQuestion?.();
    }

    const handleDelete = (question)=>{
        onDeleteQuestion?.(question)
    }


    return (
        <ExpandableCard onExpandChange={handleExpandChange} title={category?.name} onDelete={handleDeleteCategory}>
            {children ? children : <QuestionTable questions={questions} onNew={handleNew} onDelete={handleDelete}></QuestionTable>}
        </ExpandableCard>
    )
}