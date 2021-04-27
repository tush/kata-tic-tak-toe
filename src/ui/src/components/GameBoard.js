import Square from "./Square";
import Button from "./Button";

function GameBoard({ game, onSquareClick, onJoinGame, player }) {
  const handleClick = (posX, posY) => {
    if (
      game.status !== "FINISHED" &&
      game.status !== "DRAW" &&
      game["player" + game.nextPlayer]?.id === player.id &&
      game.board[posX][posY] === 0
    ) {
      onSquareClick(posX, posY, game.id, game.nextPlayer);
    }
  };

  const renderSquare = (posX, posY) => {
    return (
      <Square
        value={game.board[posX][posY]}
        onClick={() => handleClick(posX, posY)}
      />
    );
  };

  return (
    <div className="game-board">
      {game.status === "NEW" && player.id !== game.playerX.id && (
        <Button text="Join" onClick={() => onJoinGame(game.id)} />
      )}
      <div className="status">
        {game.status === "IN_PROGRESS" && (
          <p>Next Player: {game["player" + game.nextPlayer]?.name}</p>
        )}
        {game?.winner && <p>Winner: {game?.winner}</p>}
        {(game.status === "FINISHED" || game.status === "DRAW") && (
          <p>Status: {game?.status}</p>
        )}
      </div>
      <div className="board-row">
        {renderSquare(0, 0)}
        {renderSquare(0, 1)}
        {renderSquare(0, 2)}
      </div>
      <div className="board-row">
        {renderSquare(1, 0)}
        {renderSquare(1, 1)}
        {renderSquare(1, 2)}
      </div>
      <div className="board-row">
        {renderSquare(2, 0)}
        {renderSquare(2, 1)}
        {renderSquare(2, 2)}
      </div>
    </div>
  );
}

export default GameBoard;
