import React from "react";
import { Link as ScrollLink } from "react-scroll";
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
                    <ScrollLink
                        key={category.categoryId}
                        to={`category-${category.categoryId}`}
                        data-to={`category-${category.categoryId}`}
                        smooth={true}
                        containerId="scroll-main"
                        >
                        <div className="block p-1 m-2.5 text-gray-700 hover:text-black hover:scale-105 transition-transform transform border border-gray-400 rounded text-center">
                            <CategoryProgress
                                name={category.categoryName}
                                answeredQuestions={category.answeredQuestions}
                                totalQuestions={category.totalQuestions}
                            />
                        </div>
                    </ScrollLink>
                ))}
            </nav>
        </div>
    );
}