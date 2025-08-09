/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

import React from 'react';
import { useParams } from 'react-router-dom';
import PreviewPage from '../pages/PreviewPage';

const Preview = () => {
  const { id } = useParams();

  return <PreviewPage fileId={id || ''} />;
};

export default Preview;
