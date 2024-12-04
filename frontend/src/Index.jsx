import React from 'react';
import ReactDOM from 'react-dom';
import App from './App.jsx';
import './index.css'
import {Footer} from "./components/Layout/Footer.jsx";

ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        {/*<Header/>*/}
        <App className="pb-16"/>
        <Footer/>
    </React.StrictMode>
);