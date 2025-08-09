/**

File: types/index.ts
Location: DUKE_OS/java1kind_frontend/src/types/
*/
/*
Copyright © 2025 Devin B. Royal.
All Rights Reserved.
*/
export interface AuthState {
  isAuthenticated: boolean;
  token: string | null;
  isAdmin: boolean;
  loading: boolean;
  error: string | null;
}

export interface Program {
  id: string;
  title: string;
  description: string;
  imageUrl: string;
  filename: string;
  priceInUSD: number;
  uploadedAt: string;
  previewSnippet?: string;
}

export interface ProgramState {
  programs: Program[];
  loading: boolean;
  error: string | null;
  selectedProgram: Program | null;
}

export interface UploadPayload {
  title: string;
  description: string;
  image: File;
  file: File;
  priceInUSD: number;
}

export interface PreviewResponse {
  snippet: string;
  lines: number;
  filename: string;
}

export interface LoginPayload {
  email: string;
  password: string;
}