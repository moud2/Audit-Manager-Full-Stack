import {QuestionList} from "./QuestionList.jsx";
import Title from "../Textareas/Title.jsx";


export function CategoryListItem({ category, options, onChange }) {
    // const labels = [0, 1, 2, 3, 4, 5, "N/A"];
    // const [category, setCategory] = useState([
    //     {
    //         name: 'VPN',
    //          id: 1,
    //         questions: [
    //             { id: '1', question: 'Frage 1?', points: 3, nA: false, comment: 'Ok' },
    //             { id: '2', question: 'Frage 2?', points: null, nA: true, comment: '' },
    //         ]
    //     }
    // ]);

    const handleQuestionChange = (newQuestionList, updatedQuestion) => {
        const newCategory = {...category, questions: newQuestionList};
        console.log(newCategory);
        onChange(newCategory, updatedQuestion);
    }

    return (
        <div key={category.id}>
            <Title>{category.name}</Title>
            <QuestionList
                questions={category.questions}
                options={options}
                onChange={handleQuestionChange}
            />
        </div>
    );
}
