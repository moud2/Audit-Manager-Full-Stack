
function CategoryProgress({ name, answeredQuestions, totalQuestions}) {
    return (
        <div className="mb-1">
            {/* Obere Zeile mit Name und Fortschritt */}
            <div className="flex justify-between items-center">

                {/* Kategorie-Name */}
                <div className="text-gray-900 text-base font-medium overflow-hidden text-ellipsis whitespace-nowrap">
                    {name}
                </div>

                {/* Anzeige beantwortete Fragen / Fragen insgesamt */}
                <div className="text-sm text-gray-600">
                    {`${answeredQuestions}/${totalQuestions}`}
                </div>
            </div>

            {/* Fortschrittsbalken in % */}
            <div className="mt-2 h-2 w-full bg-gray-200 rounded">
                <div
                    className="h-2 bg-red-600 rounded"
                    style={{ width: `${(answeredQuestions/totalQuestions)*100}%` }}
                />
            </div>
        </div>
    );
}

export default CategoryProgress;