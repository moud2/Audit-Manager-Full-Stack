import React, { useEffect, useState } from "react";
import api from "../../api";
import { AuditGridItem } from "./AuditGridItem";
import { NewAuditGridItem } from "./NewAuditGridItem";

/**
 * AuditGrid component fetches audit data from the backend and displays
 * them in a grid format, including a button to create a new audit.
 *
 * @returns {JSX.Element} The rendered audit grid component.
 * @constructor
 */
export function AuditGrid() {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    api
      .get("/v1/audits")
      .then((response) => {
        setData(response.data);
        setLoading(false);
      })
      .catch((err) => {
        console.error("Error fetching data:", err);
        setError(err);
        setLoading(false);
      });
  }, []);

  if (loading) {
    return <p>Laden...</p>;
  }

  return (
    <div>
      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-3 xl:grid-cols-4 2xl:grid-cols-5 gap-10 justify-center">
        {/* PlusIcon */}
        <NewAuditGridItem />

        {/* Existing Audits */}
        {data.map((audit) => (
          <AuditGridItem key={audit.id} audit={audit} />
        ))}
      </div>
    </div>
  );
}

export default AuditGrid;
