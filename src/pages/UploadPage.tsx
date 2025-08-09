/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const UploadPage = () => {
  const [file, setFile] = useState(null);
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [isUploading, setIsUploading] = useState(false);

  const navigate = useNavigate();

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
    setSuccess('');
    setError('');
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsUploading(true);
    setError('');
    setSuccess('');

    const token = localStorage.getItem('token');
    if (!token) {
      setError('Unauthorized. Please log in.');
      setIsUploading(false);
      return;
    }

    if (!file || !title || !description) {
      setError('All fields are required.');
      setIsUploading(false);
      return;
    }

    const formData = new FormData();
    formData.append('file', file);
    formData.append('title', title);
    formData.append('description', description);

    try {
      const res = await axios.post(
        `${process.env.REACT_APP_API_BASE_URL}/api/upload`,
        formData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'multipart/form-data',
          },
        }
      );

      setSuccess('Upload successful!');
      setTitle('');
      setDescription('');
      setFile(null);
    } catch (err) {
      if (err.response && err.response.status === 403) {
        setError('Only the admin (Devin B. Royal) can upload files.');
      } else {
        setError('Upload failed. Please try again.');
      }
    } finally {
      setIsUploading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gray-900 text-white flex justify-center items-center px-4">
      <div className="w-full max-w-2xl bg-gray-800 rounded-lg shadow-md p-8">
        <h2 className="text-3xl font-bold mb-6 text-center text-lime-300">Upload a Java Program</h2>

        {error && <div className="mb-4 text-red-400 text-center">{error}</div>}
        {success && <div className="mb-4 text-green-400 text-center">{success}</div>}

        <form onSubmit={handleSubmit} className="space-y-6">
          <div>
            <label className="block text-sm font-medium text-gray-200 mb-2">Program Title</label>
            <input
              type="text"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              className="w-full bg-gray-700 border border-gray-600 rounded px-4 py-2 text-white focus:outline-none focus:ring-2 focus:ring-lime-500"
              placeholder="e.g., Secure Blockchain Node"
              required
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-200 mb-2">Description</label>
            <textarea
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              className="w-full bg-gray-700 border border-gray-600 rounded px-4 py-2 text-white focus:outline-none focus:ring-2 focus:ring-lime-500"
              placeholder="Brief description of the program..."
              rows={4}
              required
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-200 mb-2">Java File (.zip)</label>
            <input
              type="file"
              onChange={handleFileChange}
              className="w-full text-sm text-gray-300 file:mr-4 file:py-2 file:px-4
                         file:rounded file:border-0
                         file:text-sm file:font-semibold
                         file:bg-lime-500 file:text-black
                         hover:file:bg-lime-600"
              accept=".zip"
              required
            />
          </div>

          <div className="text-center">
            <button
              type="submit"
              disabled={isUploading}
              className={`w-full bg-lime-500 text-black font-semibold py-2 px-4 rounded hover:bg-lime-600 transition-colors duration-200 ${
                isUploading ? 'opacity-50 cursor-not-allowed' : ''
              }`}
            >
              {isUploading ? 'Uploading...' : 'Upload Program'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default UploadPage;

/*
 * This is the secure upload page restricted to Devin B. Royal.
 * It includes full title/description input, JWT authorization,
 * file error handling, Stripe session security via backend, and success UI.
 * Copyright © 2025 Devin B. Royal. All Rights Reserved.
 */
