/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import UploadPage from './pages/UploadPage.jsx';
import DownloadPage from './pages/DownloadPage.jsx';
import ProgramView from './pages/ProgramView.jsx';
import Home from './pages/Home.jsx';
import Navbar from './components/Navbar.jsx';

export default function App() {
  return (
    <Router>
      <div className="app dark">
        <Navbar />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/upload" element={<UploadPage />} />
          <Route path="/download/:id" element={<DownloadPage />} />
          <Route path="/program/:id" element={<ProgramView />} />
        </Routes>
      </div>
    </Router>
  );
}
