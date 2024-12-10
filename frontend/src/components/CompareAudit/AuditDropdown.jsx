import React, {useState, useEffect} from "react";
import Text from "../Textareas/Text.jsx";
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';

/**
 * AuditDropdown component renders a dropdown menu for selecting an audit.
 * Allows users to select a second audit to compare with the selected one.
 *
 * @param {Object} props - The props passed to the component.
 * @param {Array} props.audits - The list of available audits.
 * @param {Function} props.onAuditSelect - Callback function to handle the audit selection.
 * @returns {JSX.Element} A dropdown component for selecting an audit.
 */
export function AuditDropdown({ audits, onAuditSelect }) {
    const [isOpen, setIsOpen] = useState(false);
    const [searchTerm, setSearchTerm] = useState(""); 
    const [filteredAudits, setFilteredAudits] = useState(audits);

    useEffect(() => {
        setFilteredAudits(
            audits.filter((audit) =>
                audit.name.toLowerCase().includes(searchTerm)
            )
        );
    }, [audits, searchTerm]);

    const toggleDropdown = () => setIsOpen(!isOpen);

    const handleSearch = (event) => {
        const search = event.target.value.toLowerCase();
        setSearchTerm(search);
        setFilteredAudits(
            audits.filter((audit) => audit.name.toLowerCase().includes(search))
        );
    };

    return (
        <div className="mb-6">
            {/* Label for the dropdown */}
            <Text htmlFor="auditDropdown" className="block text-gray-700 font-bold mb-2">
                Zweites Audit auswählen:
            </Text>
            
            <button
                onClick={toggleDropdown}
                className="inline-flex justify-between w-full px-4 py-2 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-gray-100 focus:ring-blue-500"
            >
                <span>Bitte Auswählen</span>
                <KeyboardArrowDownIcon
                    className={`transform transition-transform ${
                        isOpen ? "rotate-180" : "rotate-0"
                    }`}
                />
            </button>

            {isOpen && (
                <div className="absolute z-10 w-full mt-2 bg-white rounded-md shadow-lg ring-1 ring-black ring-opacity-5">
                    <input
                        type="text"
                        value={searchTerm}
                        onChange={handleSearch}
                        placeholder="Search audits"
                        className="w-full px-4 py-2 text-gray-800 border-b border-gray-300 focus:outline-none"
                    />

                    <div className="max-h-60 overflow-y-auto">
                        {filteredAudits.map((audit) => (
                            <div
                                key={audit.id}
                                className="block px-4 py-2 text-gray-700 hover:bg-gray-100 cursor-pointer"
                                onClick={() => {
                                    onAuditSelect(audit); 
                                    setIsOpen(false); 
                                }}
                            >
                                {audit.name}
                            </div>
                        ))}

                        {filteredAudits.length === 0 && (
                            <div className="px-4 py-2 text-gray-500">Keine Ergebnisse gefunden</div>
                        )}
                    </div>
                </div>
            )}
        </div>
    );
}
