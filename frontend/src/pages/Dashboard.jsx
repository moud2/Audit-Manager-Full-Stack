import { LayoutDefault } from "../layouts/LayoutDefault.jsx";
import AuditGrid from "../components/AuditGrid/AuditGrid.jsx";
import {useState, useEffect, useMemo} from "react";
import  api  from "../api.js";
import { Input, debounce, TextField } from "@mui/material";
import { LoadingScreen } from "../components/LoadingState";
import { AlertWithMessage } from "../components/ErrorHandling";
import { handleApiError } from "../utils/handleApiError";

export function Dashboard() {
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(false);
    const [loadingProgress, setLoadingProgress] = useState(0);
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
    };

    // Simulate loading progress
    useEffect(() => {
        let interval = null;

        if (loading) {
            interval = setInterval(() => {
                setLoadingProgress((prev) => (prev < 100 ? prev + 10 : 100));
            }, 300);
        }

        return () => {
            if (interval) clearInterval(interval);
        };
    }, [loading]);

    // Fetch data from backend
    useEffect(() => {
        setLoading(true);
        api
            .get("/v1/audits", {
                params: {
                    customer: debouncedCustomerName?.length ? debouncedCustomerName : undefined,
                },
            })
            .then((response) => {
                setData(response.data);
                setError(null);
            })
            .catch((err) => {
                // Use the helper function
                const errorMessage = handleApiError(err);
                setError(errorMessage);
            })
            .finally(() => setLoading(false));
    }, [debouncedCustomerName]);

    if (loading) {
      return <LoadingScreen progress={loadingProgress} message="Loading, please wait..." />;
  }

  if (error) {
      return <AlertWithMessage severity="error" title="Error" message={error} />;
  }

    return (
        <LayoutDefault>
            <div className="w-full h-full p-5">
                <h1 className="text-center text-2xl mb-6">Dashboard</h1>
                <h2 className="font-bold">Filter</h2>
                <TextField id="outlined-basic" label="Kundenname" variant="outlined" onChange={handleCustomerFilterChange} />
                <AuditGrid data={data} loading={loading} error={error} />
            </div>
        </LayoutDefault>
    );
  }    