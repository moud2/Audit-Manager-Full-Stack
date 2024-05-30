import React, { useEffect, useState } from 'react';
import axios from 'axios';

const App = () => {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    axios.post('http://localhost:8080/api/hw',{name:"Merlin"})
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
      <h1>{data.topic}</h1>
      <p>{data.message}</p>
    </div>
  );
};

export default App;
