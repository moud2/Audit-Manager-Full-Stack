import React from "react";
import { TableItem } from "./TableItem.jsx";

export function TableColumn({ title, items, onDropItem }) {
  const handleDragStart = (e, id) => {
    e.dataTransfer.setData("cardID", id);
  };

  const handleDrop = (e) => {
    e.preventDefault();
    const cardID = e.dataTransfer.getData("cardID");
    onDropItem(cardID);
  };

  return (
    <div
      className="w-1/3 bg-white rounded-lg shadow-md flex flex-col p-4"
      onDragOver={(e) => e.preventDefault()}
      onDrop={handleDrop}
    >
      <h3 className="mb-4">{title} ({items.length})</h3>
      <div className="overflow-y-auto h-72">
        {items.map((item) => (
          <TableItem
            key={item.id}
            id={item.id}
            title={item.title}
            onDragStart={handleDragStart}
          />
        ))}
      </div>
    </div>
  );
}
