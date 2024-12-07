import React from "react";
import { Link } from "react-scroll";
import CategoryProgress from "./CategoryProgress.jsx";

export default function AuditProgress({ progress }) {
    // const dummyData = [
    //     { name: "Netzwerk", id: 1,  progress: 20 },
    //     { name: "Server Administration", id: 3, progress: 75 },
    //     { name: "Datenbanken", id: 5, progress: 50 },
    //     { name: "Server Administration", progress: 75 },
    //     { name: "Datenbanken", progress: 50 },
    // ];


    return (
        <div>
            <nav>
                {progress.categoryProgress.map((category) => (
                    <Link key={category.categoryId} to={`category-${category.categoryId}`} smooth={true}>
                        <div key={category.categoryId}
                             className="block p-1 mt-2.5 text-gray-700 hover:text-black hover:scale-105 transition-transform transform border border-gray-400 rounded text-center">
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