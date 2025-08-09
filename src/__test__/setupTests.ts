// src/__test__/setupTests.ts
import { vi } from "vitest";
import axios from "axios";

// Automatically mock axios for all tests
vi.mock("axios");

// Make the mock available everywhere
const mockedAxios = axios as unknown as {
  get: ReturnType<typeof vi.fn>;
  post: ReturnType<typeof vi.fn>;
  put: ReturnType<typeof vi.fn>;
  delete: ReturnType<typeof vi.fn>;
};

// Default mock behavior
mockedAxios.get = vi.fn().mockResolvedValue({ status: 200, data: {} });
mockedAxios.post = vi.fn().mockResolvedValue({ status: 201, data: {} });
mockedAxios.put = vi.fn().mockResolvedValue({ status: 200, data: {} });
mockedAxios.delete = vi.fn().mockResolvedValue({ status: 200, data: {} });

export { mockedAxios };
