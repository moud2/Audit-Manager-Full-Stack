import React, { useState } from "react";
import SearchIcon from '@mui/icons-material/Search';

export const NewAudit = () => {
  return (
    <>
      <form className="w-[240px] flex justify-center items-center mx-auto m-5">
        <div className="absolute pl-100px">
          <input type="search" placeholder="Name" className="w-full p-4 rounded-t-lg rounded-b-lg bg-neutral-200 shadow-inner" />
            <button className="absolute right-1 top-1/2 -translate-y-1/2 p-3 bg-neutral-400 rounded-t-lg rounded-b-lg shadow-md">
              <SearchIcon />
            </button>
        </div>
      </form>
      <Board />
    </>
  )
}

export default NewAudit

const Board = () => {
  const [cards, setCards] = useState(DEFAULT_CARDS);
  return ( <div className='flex justify-center gap-10 h-full w-full overflow-scroll p-12'>
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
  )
}


const Column = ({title, headingColor, column, cards, setCards }) => {

  const [active, setActive] = useState(false); 
  const filteredCards = cards.filter((c) => c.column === column)
  const handleDragsStart = (e, card) => {
    e.dataTransfer.setData("cardID", card.id)
  }
  const handleDragOver = (e) => {
    e.preventDefault(); 
    hightlightIndicator(e);
    setActive(true)
  }

  const hightlightIndicator = (e) => {
    const indicators = getIndicators();  
    clearHighlights()
    const el = getNearestIndicator(e, indicators);
    el.element.style.opacity = "1" 
  }

  const clearHighlights = (els) => {
    const indicators = els || getIndicators()


    indicators.forEach((i) => {
      i.style.opacity = "0"
    })
  }


  const getNearestIndicator = (e, indicators) => {
     const DISTANCE_OFFSET = 50

    const el = indicators.reduce(
      (closest, child) => {
        const box = child.getBoundingClientRect(); 
        const offset = e.clientY - (box.top + DISTANCE_OFFSET)

        if (offset < 0 && offset > closest.offset){
          return { offset: offset, element: child}
        } else {
          return closest
        }
      }, 
      {
        offset: Number.NEGATIVE_INFINITY,
        element: indicators[indicators.length - 1]
      }
    )
    return el 
  }
  const getIndicators = () => {
    return Array.from(document.querySelectorAll(`[data-column="${column}"]`))
  }

  const handleDragLeave = () => {
    setActive(false)
    clearHighlights()
  }

  const handleDragEnd = (e) => {
    setActive(false)
    clearHighlights()

    const cardID = e.dataTransfer.getData("cardID")

    const indicators = getIndicators();
    const { element } = getNearestIndicator(e, indicators)

    const before = element.dataset.before || "-1"

    if (before !== cardID) {
      let copy = [...cards]

      let cardToTransfer = copy.find((c) => c.id === cardID)
      if(!cardToTransfer) return

      cardToTransfer = { ...cardToTransfer, column}

      copy = copy.filter((c) => c.id !== cardID)

      const moveToBack = before === "-1"

      if (moveToBack) {
        copy.push(cardToTransfer)
      } else {
        const insertAtIndex = copy.findIndex((el) => el.id === before)
        if (insertAtIndex === undefined) return 

        copy.splice(insertAtIndex, 0, cardToTransfer)
      }
      setCards(copy)
    }
  }

  return ( 
    
    <div className="w-1/3 shrink-0 bg-white rounded-lg shadow-md">
    <div className="mb-3 flex items-center justify-between p-4 border-b border-neutral-200">
      <h3 className={`font-medium ${headingColor}`}>{title}</h3>
      <span className="rounded text-sm text-neutral-500">{filteredCards.length}</span>
    </div>
    <div
      onDragOver={handleDragOver}
      onDragLeave={handleDragLeave}
      onDrop={handleDragEnd}
      className="h-[400px] w-full p-4 transition-colors"
    >
      {filteredCards.map((c) => {
        return (
          <Card
            key={c.id}
            {...c}
            handleDragsStart={handleDragsStart}
          />
        );
      })}
      <DropIndicator beforeId="-1" column={column} />
    </div>
  </div>
  )
}



const Card = ({title, id, column, handleDragsStart}) => {
  return (
    <>
    <DropIndicator beforeId={id} column={column}/>
    <div
      layout
      layoutId={id}
      draggable="true" 
      onDragStart={(e) => handleDragsStart(e, {title, id, column})}
      className="cursor-grab rounded border border-neutral-700 bg-neutral-700 p-3 active:cursor-grabbing">
      <p className="text-sm text-neutral-100">{title}</p>
    </div>
    </>
  )
}

const DropIndicator = ({beforeId, column}) => {
  return (
    <div
      data-before={beforeId || "-1"}
      data-column={column}
      className="my-0.5 h-0.5 w-full bg-red-400 opacity-0"
    />
  )
}



const DEFAULT_CARDS = [
  // Verfügbar kategorien
  { title: "kategorie 1", id: "1", column: "Verfügbar kategorien" },
  { title: "kategorie 2", id: "2", column: "Verfügbar kategorien" },
  { title: "kategorie 3", id: "3", column: "Verfügbar kategorien" },
  { title: "kategorie 4", id: "4", column: "Verfügbar kategorien" },
  // Ausgewählte kategorien
  {
    title: "kategorie 5",
    id: "5",
    column: "todo",
  },
  { title: "kategorie 6", id: "6", column: "Ausgewählte kategorien" },
  { title: "kategorie 7", id: "7", column: "Ausgewählte kategorien" }

]