import { useNavigate } from 'react-router-dom';

/**
 * CompareAudit component renders a button for navigating to CompareAudits page.
 * @param {Object} props - The props passed to the component.
 * @param {Object} props.selectedAudit - The selected audit data.
 * @returns {JSX.Element}
 */
export function CompareAudit({ selectedAudit }) {
    const navigate = useNavigate();

    const handleNavigate = () => {
        if (selectedAudit) {
            navigate('/compare-audits', { state: { selectedAudit } });
        } else {
            console.error("Kein Audit ausgewählt");
        }
    };

    return (
        <div className="flex justify-center mt-6">
            <button
                className="fixed right-16 p-2 bottom-20 mb-12 bg-blue-500 text-white rounded"
                onClick={handleNavigate}
            >
                Audit vergleichen
            </button>
        </div>
    );
}
