import React from 'react';
import {BrowserRouter as Router, Link, Route, Routes} from 'react-router-dom';
import {createTheme, ThemeProvider} from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import Dashboard from './components/Dashboard.jsx';
import NewAudit from './components/NewAudit.jsx';
import PerformAudit from './components/PerformAudit.jsx';
import Evaluation from './components/Evaluation.jsx';
import './index.css';

const theme = createTheme({
    components: {
        MuiPaper: {
            styleOverrides: {
                root: {
                    backgroundColor: '#c4171f',
                    padding: '16px',
                    textAlign: 'center',
                },
            },
        },
    },
});

function App() {
    return (
        <ThemeProvider theme={theme}>
            <CssBaseline/>
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
                    <div className="flex">
                        {/*<Sidebar/>*/}
                        <div className="ml-64 p-4 w-full">
                            <Routes>
                                <Route path="/" element={<Dashboard/>}/>
                                <Route path="/newAudit" element={<NewAudit/>}/>
                                <Route path="/performAudit" element={<PerformAudit/>}/>
                                <Route path="/evaluation" element={<Evaluation/>}/>
                                <Route path="/audit/:id" element={<PerformAudit/>}/>
                            </Routes>
                        </div>
                    </div>
                </div>
            </Router>
        </ThemeProvider>
    );
}

export default App;
