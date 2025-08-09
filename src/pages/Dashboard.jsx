/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

function Dashboard() {
  const [files, setFiles] = useState([]);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (!token) return navigate('/login');

    async function fetchUploads() {
      try {
        const res = await fetch('/api/admin/uploads', {
          headers: { Authorization: `Bearer ${token}` },
        });
        if (!res.ok) throw new Error('Unauthorized or failed request.');
        const data = await res.json();
        setFiles(data);
      } catch (err) {
        console.error(err);
        setError('Failed to load uploaded programs.');
      }
    }

    fetchUploads();
  }, [navigate]);

  return (
    <div className="min-h-screen bg-black text-white p-8">
      <h2 className="text-3xl font-bold mb-6">Admin Dashboard</h2>
      {error && <p className="text-red-500 mb-4">{error}</p>}
      <div className="grid gap-4">
        {files.map((file) => (
          <div key={file.id} className="p-4 bg-gray-900 border border-gray-700 rounded">
            <p className="text-xl font-medium">{file.title}</p>
            <p className="text-sm text-gray-400">{file.filename}</p>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Dashboard;
