

function CategoryProgress(props) {
    const { progress, name } = props;

    return (
        <div className="mb-1">
            {/* Obere Zeile mit Name und Fortschritt */}
            <div className="flex justify-between items-center">
                {/* Kategorie-Name */}
                <div
                    className="text-gray-900 text-base font-medium overflow-hidden text-ellipsis whitespace-nowrap"
                    title={name}
                >
                    {name}
                </div>

                {/* Prozentanzeige */}
                <div className="text-sm text-gray-600">{`${progress}%`}</div>
            </div>

            {/* Fortschrittsbalken */}
            <div className="mt-2 h-2 w-full bg-gray-200 rounded">
                <div
                    className="h-2 bg-blue-500 rounded"
                    style={{ width: `${progress}%` }}
                ></div>
            </div>
        </div>
    );
}

export default CategoryProgress;