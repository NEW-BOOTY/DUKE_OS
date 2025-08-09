/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

import React from 'react';

const NotFound = () => {
  return (
    <div className="flex items-center justify-center min-h-screen bg-black text-white">
      <div className="text-center">
        <h1 className="text-6xl font-bold mb-4">404</h1>
        <p className="text-xl mb-6">The page you're looking for doesn't exist.</p>
        <a href="/" className="text-blue-500 underline hover:text-blue-300">
          Go back to homepage
        </a>
      </div>
    </div>
  );
};

export default NotFound;
