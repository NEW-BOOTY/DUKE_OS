/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

import React from 'react';
import axios from 'axios';

interface SecureDownloadButtonProps {
  fileId: string;
  filename: string;
  jwtToken: string;
}

const SecureDownloadButton: React.FC<SecureDownloadButtonProps> = ({
  fileId,
  filename,
  jwtToken,
}) => {
  const handleSecureDownload = async () => {
    try {
      const response = await axios.get(
        `/api/download/${fileId}`,
        {
          headers: {
            Authorization: `Bearer ${jwtToken}`,
          },
          responseType: 'blob',
        }
      );

      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', filename);
      document.body.appendChild(link);
      link.click();
      link.remove();
      window.URL.revokeObjectURL(url);
    } catch (error) {
      console.error('Secure download failed:', error);
      alert('Download failed. Please ensure you are authorized.');
    }
  };

  return (
    <button
      onClick={handleSecureDownload}
      className="bg-green-600 hover:bg-green-800 text-white font-semibold px-5 py-2 rounded shadow-md"
    >
      Secure Download
    </button>
  );
};

export default SecureDownloadButton;
