import React, {useEffect, useState} from 'react';
import Paper from '@mui/material/Paper';
import AddIcon from '@mui/icons-material/Add';
import {Link} from 'react-router-dom';
import axios from 'axios';

function Dashboard() {

    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        axios.get('api/v1/audits')
            .then(response => {
                setData(response.data);
                setLoading(false);
            })
            .catch(err => {
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

            <div className="grid grid-cols-4 gap-4 justify-center">
                <Link to="/newAudit" className="testbox flex justify-center items-center h-full">
                    <Paper elevation={8} className="w-60 h-60 flex justify-center items-center">
                        <AddIcon sx={{fontSize: 140}}/>
                    </Paper>
                </Link>

                {data.map(audit => (
                    <Link key={audit.id} to={`/audit/${audit.id}`}
                          className="testbox flex justify-center items-center h-full">
                        <Paper elevation={8} className="w-60 h-60 flex justify-center items-center m-10">
                            <p>{audit.name}</p>
                        </Paper>
                    </Link>
                ))}
            </div>
        </div>
    );
}

export default Dashboard;
