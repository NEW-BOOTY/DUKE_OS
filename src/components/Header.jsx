/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

import React from 'react';
import { Link } from 'react-router-dom';

function Header() {
  return (
    <header className="bg-black border-b border-gray-800 py-4 px-6 flex justify-between items-center text-white">
      <Link to="/" className="text-2xl font-bold">Java 1 KIND</Link>
      <nav className="flex gap-6">
        <Link to="/programs" className="hover:underline">Programs</Link>
        <Link to="/about" className="hover:underline">About</Link>
        <Link to="/contact" className="hover:underline">Contact</Link>
        <Link to="/login" className="hover:underline">Admin</Link>
      </nav>
    </header>
  );
}

export default Header;
