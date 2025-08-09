/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

import { configureStore } from '@reduxjs/toolkit';
import authReducer from './slices/authSlice';
import programReducer from './slices/programSlice';

const store = configureStore({
  reducer: {
    auth: authReducer,
    program: programReducer,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({ serializableCheck: false }),
  devTools: process.env.NODE_ENV !== 'production',
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export default store;
