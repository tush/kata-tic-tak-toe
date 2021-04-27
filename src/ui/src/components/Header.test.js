import { render, screen } from "@testing-library/react";
import Header from "./Header";

test("renders Tic Tak Toe Game text", () => {
  render(<Header />);
  const linkElement = screen.getByText(/Tic Tak Toe Game/i);
  expect(linkElement).toBeInTheDocument();
});
