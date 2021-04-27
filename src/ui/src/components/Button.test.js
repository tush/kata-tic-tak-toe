import { render, screen } from "@testing-library/react";
import Button from "./Button";

test("renders Create text", () => {
  render(<Button text="Create" />);
  const linkElement = screen.getByText(/Create/i);
  expect(linkElement).toBeInTheDocument();
});
