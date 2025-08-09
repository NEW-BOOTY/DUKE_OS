/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

function Programs() {
  const [programs, setPrograms] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    async function fetchPrograms() {
      try {
        const response = await fetch('/api/programs');
        if (!response.ok) throw new Error('Failed to load programs.');
        const data = await response.json();
        setPrograms(data);
      } catch (err) {
        console.error(err);
        setError('Error loading programs. Please try again later.');
      }
    }

    fetchPrograms();
  }, []);

  return (
    <div className="min-h-screen bg-black text-white p-8">
      <h2 className="text-4xl font-bold mb-6">Available Programs</h2>
      {error && <p className="text-red-500 mb-4">{error}</p>}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
        {programs.map((program) => (
          <div key={program.id} className="border border-gray-700 rounded-lg p-5 bg-gray-900 hover:shadow-lg">
            <h3 className="text-2xl font-semibold mb-2">{program.title}</h3>
            <p className="text-sm text-gray-300 mb-4">{program.description}</p>
            <Link
              to={`/programs/${program.id}`}
              className="inline-block bg-blue-600 hover:bg-blue-800 text-white font-medium py-2 px-4 rounded"
            >
              View & Download
            </Link>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Programs;
