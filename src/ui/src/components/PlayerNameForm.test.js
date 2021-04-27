import { render, screen } from "@testing-library/react";
import PlayerNameForm from "./PlayerNameForm";

test("renders Player Name text", () => {
  render(<PlayerNameForm />);
  const linkElement = screen.getByText(/Player Name/i);
  expect(linkElement).toBeInTheDocument();
});
