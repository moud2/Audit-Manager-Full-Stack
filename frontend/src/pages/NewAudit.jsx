import React, { useState, useEffect } from "react";
import api from "../api.js";
import { LayoutDefault } from "../layouts/LayoutDefault.jsx";
import { Table } from "../components/Table/Table.jsx";
import { useNavigate } from "react-router-dom";
import { TextField } from "@mui/material";

export function NewAudit() {
  const [cards, setCards] = useState([]);
  const [selectedCategories, setSelectedCategories] = useState([]);
  const [name, setName] = useState("");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const navigate = useNavigate();

  const handleCreateAuditClick = () => {
    api
      .post("http://localhost:8080/api/v1/audits/new", {
        name: name,
        categories: selectedCategories,
      })
      .then((response) => {
        navigate("/perform-audit/" + response.data.id);
      })
      .catch((err) => {
        console.error("Error creating audit:", err);
        alert("Fehler beim Erstellen des Audits.");
      });
  };

  const handleNameChange = (e) => {
    setName(e.target.value);
  };

  useEffect(() => {
    api
      .get("http://localhost:8080/api/v1/categories")
      .then((response) => {
        const categories = response.data.map((category) => ({
          id: category.id.toString(),
          title: category.name,
        }));
        setCards(categories);
        setLoading(false);
      })
      .catch((err) => {
        console.error("Error fetching categories:", err);
        setError(err);
        setLoading(false);
      });
  }, []);


  if (loading) {
    return <p>Loading...</p>; // Display loading message while data is being fetched
  }

  if (error) {
    return <p>Fehler: {error.message}</p>; // Display error message if fetch fails
  }

  return (
    <LayoutDefault>
      <div>
        <h1 className="text-center text-4xl m-6">Neues Audit anlegen</h1>
        <form className="w-[240px] flex justify-center items-center mx-auto m-8">
          <div className="relative flex w-full justify-center">
            <TextField
              label="Audit Name"
              variant="outlined"
              value={name}
              onChange={handleNameChange}
            />
          </div>
        </form>
        <Table
          value={selectedCategories}
          options={cards}
          onChange={setSelectedCategories}
        />
        <div className="flex justify-center mt-6">
          <button
            onClick={handleCreateAuditClick}
            className="fixed right-16 p-2 bottom-20 mb-12 bg-blue-500 text-white rounded"
          >
            Audit erstellen
          </button>
        </div>
      </div>
    </LayoutDefault>
  );
}
