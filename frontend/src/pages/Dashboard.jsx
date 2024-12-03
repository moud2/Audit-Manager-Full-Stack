import { LayoutDefault } from "../layouts/LayoutDefault.jsx";
import AuditGrid from "../components/AuditGrid/AuditGrid.jsx";
import { useState, useEffect } from "react";
import api from "../api.js";
import Title from "../components/Textareas/Title.jsx"

export function Dashboard() {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  /* fetching data from backend */
  useEffect(() => {
    api
      .get("/v1/audits")
      .then((response) => {
        console.log(response);
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
    <LayoutDefault>
      <div className="bg-white-200 w-full h-full">
        <Title>Dashboard</Title>
        <AuditGrid data={data} loading={loading} error={error} />
      </div>
    </LayoutDefault>
  );
}
