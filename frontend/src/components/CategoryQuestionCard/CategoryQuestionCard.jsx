import ExpandableCard from "./ExpandableCard.jsx";
import QuestionTable from "./QuestionTable.jsx";


export default function CategoryQuestionCard({onOpen, children, questions, category, onAddQuestion, onDeleteQuestion}) {

    const handleExpandChange = (expanded) => {
        if(expanded) {
            onOpen?.();
        }
    }

    const handleNew = () => {
        onAddQuestion?.();
    }

    return (
        <ExpandableCard onExpandChange={handleExpandChange} title={category?.name}>
            {children ? children : <QuestionTable questions={questions} onNew={handleNew} onDelete={onDeleteQuestion}></QuestionTable>}
        </ExpandableCard>
    )
}