import React from 'react';
import logo from '../logo-softline.svg';

function Header() {
    return (
      <>
        <link href="./output.css" rel="stylesheet"></link>
        <header className="fixed top-1 left-1 w-full bg-white shadow-md flex items-center justify-between p-4 z-10">
            <div className="flex items-center">
                <img id="softline-logo" src={logo} alt="Softline Logo" className="h-15 w-20 mr-5" />
                <h1 className="text-xl font-bold">InSight</h1>
            </div>
        </header>
      </>
    )
  }

export default Header
