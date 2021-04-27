import { useState } from "react";
import { FaAngleDown, FaAngleUp } from "react-icons/fa";
import GameBoard from "./GameBoard";

function Game({ game, onJoinGame, onSquareClick, player }) {
  const [showGameBoard, setShowGameBoard] = useState(false);

  const getStyle = (param) => {
    switch (param) {
      case "IN_PROGRESS":
        return "in-progress";
      case "FINISHED":
        return "finished";
      case "NEW":
        return "new";
      case "DRAW":
        return "draw";
      default:
        return "";
    }
  };
  return (
    <>
      <div className={`game ${getStyle(game.status)} `}>
        <p>
          <span>Player X: {game?.playerX?.name}</span>
          <span>Player O: {game?.playerO?.name}</span>

          {!showGameBoard ? (
            <FaAngleDown
              style={{ cursor: "pointer", float: "right" }}
              onClick={() => setShowGameBoard(!showGameBoard)}
            />
          ) : (
            <FaAngleUp
              style={{ cursor: "pointer", float: "right" }}
              onClick={() => setShowGameBoard(!showGameBoard)}
            />
          )}
        </p>
      </div>
      <div>
        {showGameBoard && (
          <GameBoard
            game={game}
            onSquareClick={onSquareClick}
            onJoinGame={onJoinGame}
            player={player}
          />
        )}
      </div>
    </>
  );
}

export default Game;
