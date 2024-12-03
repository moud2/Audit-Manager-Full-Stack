import React from "react";
import Title from "../Textareas/Title";

/**
 * A reusable Header component.
 *
 * @returns {JSX.Element}
 * @constructor
 */
export function Header() {
    return (
      <header className="fixed top-0 left-0 w-full bg-white shadow-md items-center justify-between p-4 z-10">
        <div className="flex items-center justify-start">
          <img
            src="/logo-insight.png"
            alt="InSight Logo"
            className="w-8 h-8 mr-2"
          />
          <Title className="text-4xl font-medium">InSight</Title>
        </div>
      </header>
    );
}
