import React from 'react';
import './index.css';
import {Route, HashRouter, Routes} from "react-router-dom";
import {Index} from "./pages/Index.jsx";
import {Dashboard} from "./pages/Dashboard.jsx";
import {Page} from "./pages/my/example/nested/Page.jsx";

function App() {
    return (
        <HashRouter>
            <Routes>
                <Route path="/" element={<Index />}></Route>
                <Route path="/dashboard" element={<Dashboard />}></Route>
                <Route path="/my/example/nested/page" element={<Page />}></Route>
            </Routes>
        </HashRouter>
    );
}

export default App;
