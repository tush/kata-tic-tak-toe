import Game from "./Game";

function Games({ games, onJoinGame, onSquareClick, player }) {
  return (
    <>
      {games.map((game) => (
        <Game
          key={game.id}
          game={game}
          onJoinGame={onJoinGame}
          onSquareClick={onSquareClick}
          player={player}
        />
      ))}
    </>
  );
}

export default Games;
