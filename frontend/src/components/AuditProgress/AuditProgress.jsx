import React from "react";
import { Link } from "react-scroll";
import CategoryProgress from "./CategoryProgress.jsx";
import {useAuditData} from "../../context/AuditContext.jsx";

export default function AuditProgress() {
    const dummyData = [
        { name: "Netzwerk", id: 1,  progress: 20 },
        { name: "Server Administration", id: 3, progress: 75 },
        { name: "Datenbanken", id: 5, progress: 50 },
        { name: "Server Administration", progress: 75 },
        { name: "Datenbanken", progress: 50 },
    ];

    const { auditData } = useAuditData();
    console.log(auditData);

    return (
        <div>
            <nav>
                {dummyData.map((category) => (
                    <Link key={category.id} to={`category-${category.id}`} smooth={true}>
                        <div key={category.id}
                             className="block p-1 mt-2.5 text-gray-700 hover:text-black hover:scale-105 transition-transform transform border border-gray-400 rounded text-center">
                            <CategoryProgress
                                key={category.id}
                                name={category.name}
                                progress={category.progress}
                            />
                        </div>
                    </Link>
                ))}
            </nav>
        </div>
    );
}