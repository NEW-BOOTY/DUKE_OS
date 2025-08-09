/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

import { createSlice, PayloadAction } from '@reduxjs/toolkit';

interface Program {
  id: string;
  title: string;
  description: string;
  filename: string;
  uploadedAt: string;
  size: number;
}

interface ProgramState {
  programs: Program[];
  loading: boolean;
  error: string | null;
}

const initialState: ProgramState = {
  programs: [],
  loading: false,
  error: null,
};

const programSlice = createSlice({
  name: 'program',
  initialState,
  reducers: {
    fetchProgramsStart: (state) => {
      state.loading = true;
      state.error = null;
    },
    fetchProgramsSuccess: (state, action: PayloadAction<Program[]>) => {
      state.programs = action.payload;
      state.loading = false;
    },
    fetchProgramsFailure: (state, action: PayloadAction<string>) => {
      state.loading = false;
      state.error = action.payload;
    },
  },
});

export const {
  fetchProgramsStart,
  fetchProgramsSuccess,
  fetchProgramsFailure,
} = programSlice.actions;

export default programSlice.reducer;
