import { LayoutDefault } from "../layouts/LayoutDefault.jsx";
import AuditGrid from "../components/AuditGrid/AuditGrid.jsx";
import { useState, useEffect } from "react";
// import api from "../api.js";

export function Dashboard() {
{/* 
    useEffect(() => {
        api.get('/v1/audits')
            .then(response => {
                console.log(response);
                setData(response.data);
                setLoading(false);
            })
            .catch(err => {
                console.error('Error fetching data:', err);
                setError(err);
                setLoading(false);
            });
    }, []);*/}

  const [data, setData] = useState([
    {
      id: 1, 
      name: "Audit1"
    },
    {
      id: 2, 
      name: "Audit2"
    },
    {
      id: 3, 
      name: "Audit3"
    },
    {
      id: 4, 
      name: "Audit4"
    },
    {
      id: 5, 
      name: "Audit5"
    }
  ]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);


  return (
    <LayoutDefault>
      <div className="bg-green-200 w-full h-full">
        <h1 className="text-center text-2xl mb-6">Dashboard</h1>
        <AuditGrid data={data} loading={loading} error={error} />
      </div>
    </LayoutDefault>
  );
}