/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

import React from "react";
import { Link } from "react-router-dom";

const Home = () => {
  return (
    <div className="min-h-screen bg-gray-900 text-white flex flex-col items-center justify-center px-4">
      <h1 className="text-4xl font-bold text-lime-300 mb-6">
        Welcome to Java1Kind
      </h1>
      <p className="text-lg text-gray-400 mb-8 text-center max-w-2xl">
        Upload, view, and download Java programs securely with advanced encryption.
      </p>
      <div className="flex gap-4">
        <Link
          to="/upload"
          className="px-6 py-3 bg-lime-500 hover:bg-lime-600 text-black font-semibold rounded-lg transition"
        >
          Upload Program
        </Link>
        <Link
          to="/download"
          className="px-6 py-3 bg-blue-500 hover:bg-blue-600 text-white font-semibold rounded-lg transition"
        >
          Download Programs
        </Link>
      </div>
    </div>
  );
};

export default Home;

/*
 * Home page for the Java1Kind frontend.
 * Acts as a navigation hub for upload/download program features.
 * Copyright © 2025 Devin B. Royal. All Rights Reserved.
 */
