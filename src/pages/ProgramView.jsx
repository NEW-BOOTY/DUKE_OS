/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";

const ProgramView = () => {
  const { id } = useParams();
  const [program, setProgram] = useState(null);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchProgram = async () => {
      try {
        const res = await axios.get(
          `${process.env.REACT_APP_API_BASE_URL}/api/programs/${id}`
        );
        setProgram(res.data);
      } catch (err) {
        setError("Failed to load program details. Please try again.");
      }
    };

    fetchProgram();
  }, [id]);

  if (error) {
    return (
      <div className="min-h-screen bg-gray-900 text-white flex items-center justify-center">
        <p className="text-red-400">{error}</p>
      </div>
    );
  }

  if (!program) {
    return (
      <div className="min-h-screen bg-gray-900 text-white flex items-center justify-center">
        <p className="text-gray-400">Loading program details...</p>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-900 text-white flex justify-center px-4 py-8">
      <div className="w-full max-w-3xl">
        <h1 className="text-3xl font-bold text-lime-300 mb-4">
          {program.title}
        </h1>
        <p className="text-gray-400 mb-6">{program.description}</p>

        <div className="bg-gray-800 p-4 rounded-lg">
          <h2 className="text-xl font-semibold mb-2">Code Preview</h2>
          <pre className="bg-black text-green-400 p-4 rounded overflow-x-auto">
            {program.code}
          </pre>
        </div>
      </div>
    </div>
  );
};

export default ProgramView;

/*
 * Displays details of a single program from backend.
 * Secured API fetch based on program ID from URL params.
 * Copyright © 2025 Devin B. Royal. All Rights Reserved.
 */
