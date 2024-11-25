import React from "react";
import { useNavigate, useLocation } from "react-router-dom";
//import { CompareColumn } from "../../pages/CompareColumn";

/**
 * CompareAudit component renders a button centered on the page.
 * The button includes interactive styles for hover, focus, and active states.
 *
 * @returns {JSX.Element} The rendered CompareAudit component.
 * @constructor
 */
export function CompareAudit() {
  const navigate = useNavigate();
  const location = useLocation();

  return (
    <div className="flex items-center justify-center">
      <button
        className="fixed right-16 p-2 bottom-20 mb-12 bg-blue-500 text-white rounded"
        onClick={() => navigate("/compare-audits")}
      >
        Audit vergleichen
      </button>
    </div>
  );
}
