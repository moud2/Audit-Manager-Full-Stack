import { LayoutDefault } from "../layouts/LayoutDefault.jsx";
import AuditGrid from "../components/AuditGrid/AuditGrid.jsx";
import { useState, useEffect } from "react";
import api from "../api.js";

export function Dashboard() {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  /* fetching data from backend */
  useEffect(() => {
    setLoading(true); // Set loading to true at the start of the request
    api
      .get("/v1/audits")
      .then((response) => {
        console.log(response);
        setData(response.data);
        setError(null); // Clear any previous errors
      })
      .catch((err) => {
        if (err.response) {
          // Server responded with an error status code
          console.error(`Server error: ${err.response.status} - ${err.response.data?.message || "Unknown error"}`);
          setError(`Error fetching data: ${err.response.data?.message || "An unknown error occurred."}`);
        } else if (err.request) {
          // Request was made but no response was received
          console.error("Network error: No response received from the server.");
          setError("Network error: Please check your connection.");
        } else {
          // Any other errors (e.g., code issues)
          console.error(`Error: ${err.message}`);
          setError(`An error occurred: ${err.message}`);
        }
      })
      .finally(() => setLoading(false)); // Set loading to false after the request finishes
  }, []);

  if (loading) {
    return <p>Loading...</p>; // Display loading message while data is being fetched
  }

  if (error) {
    return <p className="text-red-500">{error}</p>; // Display error message if fetch fails
  }

  return (
    <LayoutDefault>
      <div className="bg-green-200 w-full h-full">
        <h1 className="text-center text-2xl mb-6">Dashboard</h1>
        <AuditGrid data={data} loading={loading} error={error} />
      </div>
    </LayoutDefault>
  );
}
