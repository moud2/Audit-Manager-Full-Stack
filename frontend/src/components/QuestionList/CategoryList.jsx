import {QuestionListItem} from "./QuestionListItem.jsx";
import {useState} from "react";
import {CategoryListItem} from "./CategoryListItem.jsx";

export function CategoryList({ categories, options, onChange }) {
    const labels = [0, 1, 2, 3, 4, 5, "N/A"];
    // const [categories, setCategories] = useState([
    //     {
    //         name: 'VPN',
    //         id: 1,
    //         questions: [
    //             { id: '1', question: 'Frage 1?', points: 3, nA: false, comment: 'Ok' },
    //             { id: '2', question: 'Frage 2?', points: null, nA: true, comment: '' },
    //         ]
    //     },
    //     {
    //         name: 'Network',
    //         id: 2,
    //         questions: [
    //             { id: '3', question: 'Frage 3?', points: 2, nA: false, comment: '' },
    //             { id: '4', question: 'Frage 4?', points: null, nA: null, comment: 'Schlecht' },
    //         ]
    //     }
    // ]);

    const handleCategoryChange = (newCategory, updatedQuestion) => {
        const newCategories = categories.map((category) => newCategory.id === category.id? {...category, ...newCategory} : category);
        console.log("CategoryList: newCategories = ", newCategories);
        onChange(newCategories, updatedQuestion);
    }

    return (
        <div>
            {categories.map((category) => (
                <div key={category.id}>
                    <CategoryListItem
                        category={category}
                        options={options}
                        onChange={handleCategoryChange}
                    />
                </div>
            ))};
        </div>
    )
}
