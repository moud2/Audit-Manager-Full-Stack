import React from "react";
import { Link } from "react-scroll";
import {CategoryProgress} from "./CategoryProgress.jsx";

/**
 * AuditProgress Component
 *
 * Displays the progress of the current audit categories as a navigation menu.
 * Each category is rendered as a clickable link, allowing users to
 * navigate (scroll) to the respective category section smoothly.
 *
 * @param {Array} progress - Array of category progress data. Each element contains:
 *   - `categoryId` (number): The unique ID of the category.
 *   - `categoryName` (string): The name of the category.
 *   - `answeredQuestions` (number): The number of answered questions in the category.
 *   - `totalQuestions` (number): The total number of questions in the category.
 * @returns {JSX.Element} - A rendered navigation menu for audit progress.
 */
export function AuditProgress({ progress }) {

    return (
        <div>
            <nav>
                {progress.map((category) => (
                    <Link key={category.categoryId} to={"category-3"} smooth={true} containerId="scroll-container" onClick={() => console.log(`Click to jump to: category-${category.categoryId}`)}>
                        <div key={category.categoryId}
                             className="block p-1 mt-2.5 text-gray-700 hover:text-black hover:scale-105 transition-transform transform border border-gray-400 rounded text-center">
                            {category.categoryId}
                            <CategoryProgress
                                name={category.categoryName}
                                answeredQuestions={category.answeredQuestions}
                                totalQuestions={category.totalQuestions}
                            />
                        </div>
                    </Link>
                ))}
            </nav>
        </div>
    );
}