import PropTypes from "prop-types";
import Button from "./Button";

const Header = ({ title, addGame, player }) => {
  return (
    <header className="header">
      <h1>{title}</h1>
      {player !== null && <Button text="Create" onClick={addGame} />}
    </header>
  );
};

Header.defaultProps = {
  title: "Tic Tak Toe Game",
};

Header.propTypes = {
  title: PropTypes.string.isRequired,
};

export default Header;
