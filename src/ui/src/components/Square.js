function Square({ value, onClick }) {
  const renderSwitch = (param) => {
    switch (param) {
      case 1:
        return "X";
      case 2:
        return "O";
      default:
        return "";
    }
  };

  return (
    <button className="square" onClick={onClick}>
      {renderSwitch(value)}
    </button>
  );
}

export default Square;
