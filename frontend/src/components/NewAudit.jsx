import React, { useState } from "react";
import PropTypes from "prop-types";
import { useNavigate } from "react-router-dom";
import SearchIcon from "@mui/icons-material/Search";

// Initial card data
const DEFAULT_CARDS = [
  { title: "kategorie 1", id: "1", column: "Verfügbar kategorien" },
  { title: "kategorie 2", id: "2", column: "Verfügbar kategorien" },
  { title: "kategorie 3", id: "3", column: "Verfügbar kategorien" },
  { title: "kategorie 4", id: "4", column: "Verfügbar kategorien" },
  { title: "kategorie 5", id: "5", column: "Verfügbar kategorien" },
  { title: "kategorie 6", id: "6", column: "Ausgewählte kategorien" },
  { title: "kategorie 7", id: "7", column: "Ausgewählte kategorien" },
];

export const NewAudit = () => {
  const navigate = useNavigate();
  const handleCreateAuditClick = () => {
    navigate("/performAudit");
  };
  return (
    <div className="min-h-screen overflow-hidden p-4">
      <div className="flex flex-col items-start mb-3"> 
        <input
          type="text"
          placeholder="Auditname"
          className="p-2 rounded-t-lg rounded-b-lg bg-neutral-200 shadow-inner w-1/3"
        />
      </div>
      <Board />
      <div style={{ display: 'flex', justifyContent: 'flex-end', padding: '10px' }}>
        <button
          onClick={handleCreateAuditClick}
          className="mt-1 p-2 bg-blue-500 text-white rounded"
        >
          Audit erstellen
        </button>
      </div>
    </div>
  );
};

// The Board component renders two columns and manages the state of the cards
const Board = () => {
  const [cards, setCards] = useState(DEFAULT_CARDS);

  return (
    <div className="flex flex-col items-start gap-4 w-full p-1"> 
      <div className="flex gap-4 w-full">
        <Column
          title="Verfügbar kategorien"
          column="Verfügbar kategorien"
          headingColor="text-neutral-700"
          cards={cards}
          setCards={setCards}
        />
        <Column
          title="Ausgewählte kategorien"
          column="Ausgewählte kategorien"
          headingColor="text-neutral-700"
          cards={cards}
          setCards={setCards}
        />
      </div>
    </div>
  );
};

// The Column component represents each column in the board and handles drag-and-drop functionality
const Column = ({ title, headingColor, column, cards, setCards }) => {
  const [active, setActive] = useState(false);
  const filteredCards = cards.filter((c) => c.column === column);

  // This function is called when a drag event starts
  const handleDragStart = (e, card) => {
    e.dataTransfer.setData("cardID", card.id);
  };

  // This function is called when a card is dragged over the column
  const handleDragOver = (e) => {
    e.preventDefault();
    highlightIndicator(e);
    setActive(true);
  };

  // Highlights the nearest drop indicator based on the current drag position
  const highlightIndicator = (e) => {
    const indicators = getIndicators();
    clearHighlights();
    const el = getNearestIndicator(e, indicators);
    if (el) el.element.style.opacity = "1";
  };

  // Clears the highlight from all drop indicators
  const clearHighlights = () => {
    const indicators = getIndicators();
    indicators.forEach((i) => {
      i.style.opacity = "0";
    });
  };

  // Finds the nearest drop indicator based on the drag position
  const getNearestIndicator = (e, indicators) => {
    const DISTANCE_OFFSET = 50;

    return indicators.reduce(
      (closest, child) => {
        const box = child.getBoundingClientRect();
        const offset = e.clientY - (box.top + DISTANCE_OFFSET);

        if (offset < 0 && offset > closest.offset) {
          return { offset: offset, element: child };
        } else {
          return closest;
        }
      },
      {
        offset: Number.NEGATIVE_INFINITY,
        element: indicators[indicators.length - 1],
      }
    );
  };

  // Gets all the drop indicators in the current column
  const getIndicators = () => {
    return Array.from(document.querySelectorAll(`[data-column="${column}"]`));
  };

  // This function is called when the drag leaves the column
  const handleDragLeave = () => {
    setActive(false);
    clearHighlights();
  };

  // This function is called when a card is dropped into the column
  const handleDrop = (e) => {
    setActive(false);
    clearHighlights();

    const cardID = e.dataTransfer.getData("cardID");
    const indicators = getIndicators();
    const { element } = getNearestIndicator(e, indicators);

    const before = element.dataset.before || "-1";

    if (before !== cardID) {
      let copy = [...cards];
      let cardToTransfer = copy.find((c) => c.id === cardID);
      if (!cardToTransfer) return;

      cardToTransfer = { ...cardToTransfer, column };
      copy = copy.filter((c) => c.id !== cardID);

      if (before === "-1") {
        copy.push(cardToTransfer);
      } else {
        const insertAtIndex = copy.findIndex((el) => el.id === before);
        if (insertAtIndex === undefined) return;

        copy.splice(insertAtIndex, 0, cardToTransfer);
      }
      setCards(copy);
    }
  };

  return (
    <div className="w-1/3 shrink-0 bg-white rounded-lg shadow-md overflow-hidden flex flex-col">
      <div className="mb-3 flex items-center justify-between p-4 border-b border-neutral-200">
        <h3 className={`font-medium ${headingColor}`}>{title}</h3>
        <span className="rounded text-sm text-neutral-500">
          {filteredCards.length}
        </span>
      </div>
      <div
        onDragOver={handleDragOver}
        onDragLeave={handleDragLeave}
        onDrop={handleDrop}
        className="flex-grow p-4 transition-colors overflow-auto"
      >
        {filteredCards.map((c) => (
          <Card key={c.id} {...c} handleDragStart={handleDragStart} />
        ))}
        <DropIndicator beforeId="-1" column={column} />
      </div>
    </div>
  );
};

Column.propTypes = {
  title: PropTypes.string.isRequired,
  headingColor: PropTypes.string.isRequired,
  column: PropTypes.string.isRequired,
  cards: PropTypes.arrayOf(
    PropTypes.shape({
      title: PropTypes.string.isRequired,
      id: PropTypes.string.isRequired,
      column: PropTypes.string.isRequired,
    })
  ).isRequired,
  setCards: PropTypes.func.isRequired,
};

// The Card component represents a draggable card in the column
const Card = ({ title, id, column, handleDragStart }) => {
  return (
    <>
      <DropIndicator beforeId={id} column={column} />
      <div
        draggable="true"
        onDragStart={(e) => handleDragStart(e, { title, id, column })}
        className="cursor-grab rounded border border-neutral-700 bg-neutral-700 p-3 active:cursor-grabbing"
      >
        <p className="text-sm text-neutral-100">{title}</p>
      </div>
    </>
  );
};

Card.propTypes = {
  title: PropTypes.string.isRequired,
  id: PropTypes.string.isRequired,
  column: PropTypes.string.isRequired,
  handleDragStart: PropTypes.func.isRequired,
};

// The DropIndicator component represents the drop target indicator
const DropIndicator = ({ beforeId, column }) => {
  return (
    <div
      data-before={beforeId || "-1"}
      data-column={column}
      className="my-0.5 h-0.5 w-full bg-red-400 opacity-0"
    />
  );
};

DropIndicator.propTypes = {
  beforeId: PropTypes.string,
  column: PropTypes.string.isRequired,
};

export default NewAudit;
