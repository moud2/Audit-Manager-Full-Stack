import React, { createContext, useContext, useState } from "react";

// Context erstellen
const AuditContext = createContext();

// Custom Hook zum einfachen Zugriff
export const useAuditData = () => useContext(AuditContext);

// Provider-Komponente
export const AuditProvider = ({ children }) => {
    const [auditData, setAuditData] = useState(null);

    return (
        <AuditContext.Provider value={{ auditData, setAuditData }}>
            {children}
        </AuditContext.Provider>
    );
};