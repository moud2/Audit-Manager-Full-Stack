import {LayoutDefault} from "../layouts/LayoutDefault.jsx";
import AuditGrid from "../components/AuditGrid/AuditGrid.jsx";
import {useState, useEffect, useMemo} from "react";
import api from "../api.js";
import {Input, debounce, TextField} from "@mui/material";

export function Dashboard() {
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [customerName, setCustomerName] = useState("");

    const [debouncedCustomerName, setDebouncedCustomerName] = useState(customerName);

    const debouncedCustomerUpdate = useMemo(
        () =>
            debounce((value) => {
                setDebouncedCustomerName(value);
            }, 300),
        [setDebouncedCustomerName],
    );

    const handleCustomerFilterChange = (e) => {
        setCustomerName(e.target.value);
        debouncedCustomerUpdate(e.target.value);
    }

  /* fetching data from backend */
  useEffect(() => {
    setLoading(true); // Set loading to true at the start of the request
    api
      .get("/v1/audits", {
        params: {
          customer: debouncedCustomerName?.length ? debouncedCustomerName : undefined,
        }
      })
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
  }, [debouncedCustomerName]); // `debouncedCustomerName` hinzugefügt


  if (loading) {
    return <p>Loading...</p>; // Display loading message while data is being fetched
  }

  if (error) {
    return <p className="text-red-500">{error}</p>; // Display error message if fetch fails
  }

    return (
        <LayoutDefault>
            <div className="w-full h-full p-5">
                <h1 className="text-center text-2xl mb-6">Dashboard</h1>
                <h2 className="font-bold">Filter</h2>
                <TextField id="outlined-basic" label="Kundenname" variant="outlined" onChange={handleCustomerFilterChange}/>
                <AuditGrid data={data} loading={loading} error={error}/>
            </div>
        </LayoutDefault>
    );
}
