import React from 'react';
import ReactDOM from 'react-dom';
import App from './App.jsx';
import { SidebarProvider } from "./layouts/SidebarContext";
import './index.css'

ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <SidebarProvider>
        <App />
        </SidebarProvider>
    </React.StrictMode>
);