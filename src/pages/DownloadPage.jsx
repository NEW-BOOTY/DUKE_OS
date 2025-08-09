/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

import React, { useState, useEffect } from "react";
import axios from "axios";

const DownloadPage = () => {
  const [programs, setPrograms] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchPrograms = async () => {
      try {
        const res = await axios.get(
          `${process.env.REACT_APP_API_BASE_URL}/api/programs`
        );
        setPrograms(res.data);
      } catch (err) {
        setError("Failed to load programs. Please try again later.");
      }
    };

    fetchPrograms();
  }, []);

  const handleDownload = async (programId) => {
    try {
      const token = localStorage.getItem("token");
      const res = await axios.get(
        `${process.env.REACT_APP_API_BASE_URL}/api/download/${programId}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
          responseType: "blob",
        }
      );

      // Create a download link
      const url = window.URL.createObjectURL(new Blob([res.data]));
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", `${programId}.zip`);
      document.body.appendChild(link);
      link.click();
      link.remove();
    } catch (err) {
      setError("Download failed. Please try again.");
    }
  };

  return (
    <div className="min-h-screen bg-gray-900 text-white flex justify-center px-4 py-8">
      <div className="w-full max-w-4xl">
        <h1 className="text-3xl font-bold text-lime-300 mb-6 text-center">
          Download Programs
        </h1>

        {error && <p className="text-red-400 mb-4 text-center">{error}</p>}

        <div className="space-y-4">
          {programs.length === 0 && (
            <p className="text-gray-400 text-center">No programs available.</p>
          )}

          {programs.map((program) => (
            <div
              key={program.id}
              className="bg-gray-800 p-4 rounded-lg flex justify-between items-center"
            >
              <div>
                <h2 className="text-xl font-semibold">{program.title}</h2>
                <p className="text-gray-400">{program.description}</p>
              </div>
              <button
                onClick={() => handleDownload(program.id)}
                className="bg-lime-500 text-black px-4 py-2 rounded hover:bg-lime-600 transition"
              >
                Download
              </button>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default DownloadPage;

/*
 * Secure file download page for authenticated users.
 * Fetches program list from backend and downloads via JWT-protected route.
 * Copyright © 2025 Devin B. Royal. All Rights Reserved.
 */
