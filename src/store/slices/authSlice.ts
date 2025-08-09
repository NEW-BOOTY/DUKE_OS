/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

import { createSlice, PayloadAction } from '@reduxjs/toolkit';

interface AuthState {
  isAuthenticated: boolean;
  token: string | null;
  userEmail: string | null;
}

const initialState: AuthState = {
  isAuthenticated: false,
  token: null,
  userEmail: null,
};

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    loginSuccess: (state, action: PayloadAction<{ token: string; email: string }>) => {
      state.isAuthenticated = true;
      state.token = action.payload.token;
      state.userEmail = action.payload.email;
    },
    logout: (state) => {
      state.isAuthenticated = false;
      state.token = null;
      state.userEmail = null;
    },
  },
});

export const { loginSuccess, logout } = authSlice.actions;
export default authSlice.reducer;
