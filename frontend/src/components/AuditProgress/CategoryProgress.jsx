import React from "react";

function CategoryProgress(props) {
    const { progress, name } = props;

    return (
        <div className="flex items-center mb-2">
            {/* Kategorie-Name */}
            <div
                className="flex-shrink w-2/5 overflow-hidden text-ellipsis whitespace-nowrap text-gray-900 text-base"
                title={name}
            >
                {name}
            </div>

            {/* Fortschrittsbalken */}
            <div className="flex-grow ml-2">
                <div className="h-2 w-full bg-gray-200 rounded">
                    <div
                        className="h-2 bg-blue-500 rounded"
                        style={{ width: `${progress}%` }}
                    ></div>
                </div>
            </div>

            {/* Prozentanzeige */}
            <div className="ml-2 text-sm text-gray-600">{`${progress}%`}</div>
        </div>
    );
}

export default CategoryProgress;