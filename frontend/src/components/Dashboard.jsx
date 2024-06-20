import React, { useEffect, useState } from 'react';
import Paper from '@mui/material/Paper';
import AddIcon from '@mui/icons-material/Add';
import { Link } from 'react-router-dom';
import axios from 'axios';

function Dashboard() {
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);


    /* fetching data from backend */
    useEffect(() => {
        axios.get('http://localhost:8080/api/v1/audits')
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
    }, []);

    if (loading) {
        return <p>Laden...</p>;
    }

    if (error) {
        return <p>Fehler: {error.message}</p>;
    }

    return (
        <div>
            <div>
                <h1 className="text-center text-4xl m-10">Dashboard</h1>
            </div>

            {/* First box including plus icon */}
            <div className="grid gap-4 grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 justify-center">
                <Link to="/newAudit" className="flex justify-center items-center h-full mx-10">
                    <Paper elevation={8} className="w-full aspect-square flex justify-center items-center">
                        <AddIcon sx={{ fontSize: '40vw', maxWidth: 140, maxHeight: 140 }} />
                    </Paper>
                </Link>

                {/* boxes to perform audit, TODO get the right url */}
                {data.map(audit => (
                    <Link key={audit.id} to={`/audit/${audit.id}`}
                          className="flex justify-center items-center h-full mx-10">
                        <Paper elevation={8} className="w-full aspect-square flex justify-center items-center">
                            <p className="text-center">{audit.name}</p>
                        </Paper>
                    </Link>
                ))}

                {/* example boxes, TODO delete later */}
                {[...Array(6)].map((_, index) => (
                    <Link key={`example-${index}`} to="/"
                          className="flex justify-center items-center h-full mx-10">
                        <Paper elevation={8} className="w-full aspect-square flex justify-center items-center">
                            <p className="text-center">Beispiel</p>
                        </Paper>
                    </Link>
                ))}
            </div>
        </div>
    );
}

export default Dashboard;
