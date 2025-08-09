/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

import React from 'react';

function Notification({ message, type }) {
  const base = 'p-4 rounded text-sm font-medium';
  const color = type === 'success'
    ? 'bg-green-700 text-white'
    : type === 'error'
    ? 'bg-red-700 text-white'
    : 'bg-yellow-700 text-white';

  return (
    <div className={`${base} ${color} my-2`}>
      {message}
    </div>
  );
}

export default Notification;
