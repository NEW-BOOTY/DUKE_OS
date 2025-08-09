/** Copyright © 2024 Devin B. Royal. All rights reserved. */

import { describe, it, expect } from "vitest";
import { render, screen } from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";
import App from "../App";

describe("App Component", () => {
  it("renders without crashing", () => {
    render(
      <BrowserRouter>
        <App />
      </BrowserRouter>
    );
    expect(screen.getByText(/Home/i)).toBeTruthy();
  });

  it("has navigation links", () => {
    render(
      <BrowserRouter>
        <App />
      </BrowserRouter>
    );
    expect(screen.getByRole("link", { name: /Upload/i })).toBeTruthy();
    expect(screen.getByRole("link", { name: /Home/i })).toBeTruthy();
  });
});

/** Copyright © 2024 Devin B. Royal. All rights reserved. */
