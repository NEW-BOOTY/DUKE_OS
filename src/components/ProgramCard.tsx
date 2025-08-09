/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

import React from 'react';
import { Link } from 'react-router-dom';

function ProgramCard({ program }) {
  return (
    <div className="border border-gray-700 bg-gray-900 rounded-lg p-5 text-white shadow hover:shadow-lg transition-shadow duration-300">
      <h3 className="text-2xl font-bold mb-2">{program.title}</h3>
      <p className="text-sm text-gray-300 mb-4">{program.description}</p>
      <Link to={`/programs/${program.id}`} className="text-blue-400 hover:underline">
        View Details
      </Link>
    </div>
  );
}

export default ProgramCard;
