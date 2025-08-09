/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

import React from 'react';

function LoadingSpinner() {
  return (
    <div className="flex justify-center items-center py-12">
      <div className="animate-spin rounded-full h-12 w-12 border-b-4 border-blue-500"></div>
    </div>
  );
}

export default LoadingSpinner;
