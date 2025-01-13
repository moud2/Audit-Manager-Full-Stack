import React from "react";

// ExportButton-Komponente
const ExportButton = ({ onClick }) => {
  return (
    <button
      onClick={onClick} // Funktion, die beim Klick aufgerufen wird
      style={{
        padding: "10px 20px",
        backgroundColor: "#4CAF50",
        color: "white",
        border: "none",
        borderRadius: "5px",
        cursor: "pointer",
        fontSize: "14px",
      }}
    >
      CSV exportieren
    </button>
  );
};

export default ExportButton; // Exportieren der Komponente
