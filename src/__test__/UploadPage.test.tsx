/** Copyright © 2024 Devin B. Royal. All rights reserved. */

import { describe, it, expect, vi } from "vitest";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import UploadPage from "../pages/UploadPage";
import { BrowserRouter } from "react-router-dom";

describe("UploadPage Component", () => {
  it("renders the upload form", () => {
    render(
      <BrowserRouter>
        <UploadPage />
      </BrowserRouter>
    );
    expect(screen.getByText(/Upload File/i)).toBeTruthy();
    expect(screen.getByLabelText(/Select File/i)).toBeTruthy();
  });

  it("shows error when submitting without file", async () => {
    render(
      <BrowserRouter>
        <UploadPage />
      </BrowserRouter>
    );

    fireEvent.click(screen.getByText(/Submit/i));

    await waitFor(() => {
      expect(screen.getByText(/Please select a file/i)).toBeTruthy();
    });
  });

  it("accepts file input", async () => {
    const mockFile = new File(["test content"], "test.txt", { type: "text/plain" });

    render(
      <BrowserRouter>
        <UploadPage />
      </BrowserRouter>
    );

    const fileInput = screen.getByLabelText(/Select File/i) as HTMLInputElement;
    fireEvent.change(fileInput, { target: { files: [mockFile] } });

    await waitFor(() => {
      expect(fileInput.files?.[0].name).toBe("test.txt");
    });
  });
});

/** Copyright © 2024 Devin B. Royal. All rights reserved. */
