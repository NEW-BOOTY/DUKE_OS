/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

import React from 'react';
import { Link } from 'react-router-dom';

const Navbar: React.FC = () => {
  return (
    <nav className="bg-black border-b border-gray-800 shadow-md px-8 py-4 flex justify-between items-center">
      <div className="text-white font-bold text-2xl tracking-wide">
        <Link to="/">Java 1 KIND</Link>
      </div>
      <div className="flex space-x-6 text-white font-medium text-sm">
        <Link to="/" className="hover:text-purple-400 transition duration-200">
          Home
        </Link>
        <Link to="/programs" className="hover:text-purple-400 transition duration-200">
          Programs
        </Link>
        <Link to="/about" className="hover:text-purple-400 transition duration-200">
          About
        </Link>
        <Link to="/contact" className="hover:text-purple-400 transition duration-200">
          Contact
        </Link>
        <Link to="/admin" className="hover:text-red-400 transition duration-200">
          Admin
        </Link>
      </div>
    </nav>
  );
};

export default Navbar;
