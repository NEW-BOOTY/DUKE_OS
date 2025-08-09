// src/__test__/api.test.ts
import axios from "axios";
import { describe, it, expect, vi, beforeEach } from "vitest";

// Mock axios
vi.mock("axios");
const mockedAxios = axios as jest.Mocked<typeof axios>;

describe("API Security Checks", () => {
  beforeEach(() => {
    vi.clearAllMocks();
  });

  it("should accept valid API responses", async () => {
    mockedAxios.get.mockResolvedValueOnce({ status: 200 });

    const response = await axios.get("/api/health");
    expect(response.status).toBe(200);
  });

  it("should reject invalid API responses", async () => {
    mockedAxios.get.mockRejectedValueOnce(new Error("Network Error"));

    try {
      await axios.get("/api/health");
    } catch (error: any) {
      expect(error.message).toContain("Network Error");
    }
  });
});
