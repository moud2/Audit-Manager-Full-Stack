import React from 'react';
import {BrowserRouter as Router, Link, Route, Routes} from 'react-router-dom';
import Dashboard from './components/Dashboard.jsx';
import NewAudit from './components/NewAudit.jsx';
import PerformAudit from './components/PerformAudit.jsx';
import Evaluation from './components/Evaluation.jsx';
import './index.css';

function App() {
    return (
        <Router>
            <div>
                <nav>
                    <ul>
                        <li>
                            <Link to="/">Dashboard</Link>
                        </li>
                        <li>
                            <Link to="/newAudit">NewAudit</Link>
                        </li>
                        <li>
                            <Link to="/performAudit">PerformAudit</Link>
                        </li>
                        <li>
                            <Link to="/evaluation">Evaluation</Link>
                        </li>
                    </ul>
                </nav>

                <Routes>
                    <Route path="/" element={<Dashboard/>}/>
                    <Route path="/newAudit" element={<NewAudit/>}/>
                    <Route path="/performAudit" element={<PerformAudit/>}/>
                    <Route path="/evaluation" element={<Evaluation/>}/>
                    <Route path="/audit/:id" component={PerformAudit}/>
                </Routes>
            </div>
        </Router>
    );
}

export default App;
