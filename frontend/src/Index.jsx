import React from 'react';
import ReactDOM from 'react-dom';
import App from './App.jsx';
import Header from './components/Header.jsx'
import './index.css'

ReactDOM.render(
  <React.StrictMode>
    <Header />
    <App />
  </React.StrictMode>,
  document.getElementById('root')
);