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
        className="rounded-md border border-slate-300 py-2 px-4 text-center text-base transition-all shadow-sm hover:shadow-lg text-slate-600 hover:text-white hover:bg-blue-800 hover:border-blue-800 focus:text-white focus:bg-blue-800 focus:border-blue-800 active:border-slate-300 active:text-slate-600 active:bg-transparent disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
        onClick={() => navigate("/compare-column")}
      >
        Vergleich Alle Audit
      </button>
    </div>
  );
}
