import { useState } from "react";
import { FaSyncAlt } from "react-icons/fa";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import "./App.css";
import Header from "./components/Header";
import Games from "./components/Games";
import PlayerNameForm from "./components/PlayerNameForm";

function App() {
  const [player, setPlayer] = useState(null);
  const [games, setGames] = useState([]);

  const getGames = async () => {
    const games = await fetchGames();
    setGames(games);
  };

  const fetchGames = async () => {
    try {
      const rs = await fetch(`/game/games`, {
        headers: {
          "Content-type": "application/json",
        },
      });
      const data = await rs.json();
      return data;
    } catch (error) {
      toast.error(error.message);
    }
  };

  const onAddPlayer = async (name) => {
    const connectionRequest = {
      player: { name },
    };
    try {
      const resp = await fetch(`/player/add`, {
        method: "POST",
        headers: {
          "Content-type": "application/json",
        },
        body: JSON.stringify(connectionRequest),
      });
      const newPlayer = await resp.json();
      setPlayer(newPlayer);
      getGames();
    } catch (error) {
      toast.error(error.message);
    }
  };

  const addGame = async () => {
    const connectionRequest = {
      player: player,
    };
    try {
      const resp = await fetch(`/game/start`, {
        method: "POST",
        headers: {
          "Content-type": "application/json",
        },
        body: JSON.stringify(connectionRequest),
      });
      const newGame = await resp.json();
      setGames([...games, newGame]);
    } catch (error) {
      toast.error(error.message);
    }
  };

  const onJoinGame = async (gameId) => {
    const connectionRequest = {
      gameId: gameId,
      player: player,
    };
    try {
      const resp = await fetch(`/game/connect`, {
        method: "POST",
        headers: {
          "Content-type": "application/json",
        },
        body: JSON.stringify(connectionRequest),
      });
      const newGame = await resp.json();
      setGames(
        games.map((game) => (game.id === newGame.id ? { ...newGame } : game))
      );
    } catch (error) {
      toast.error(error.message);
    }
  };

  const onSquareClick = async (posX, posY, gameId, playerType) => {
    const connectionRequest = {
      gameId: gameId,
      type: playerType,
      posX: posX,
      posY: posY,
    };
    try {
      const resp = await fetch(`/game/play`, {
        method: "POST",
        headers: {
          "Content-type": "application/json",
        },
        body: JSON.stringify(connectionRequest),
      });
      const newGame = await resp.json();
      setGames(
        games.map((game) => (game.id === newGame.id ? { ...newGame } : game))
      );
    } catch (error) {
      toast.error(error.message);
    }
  };

  return (
    <div className="container">
      <Header player={player} addGame={addGame} />

      <>
        {!player && <PlayerNameForm onSetPlayerName={onAddPlayer} />}
        <div>
          {player && (
            <>
              <h3>
                Games List{" "}
                <FaSyncAlt
                  style={{ cursor: "pointer", float: "right" }}
                  onClick={() => getGames()}
                />
              </h3>

              {player && games?.length > 0 ? (
                <Games
                  games={games}
                  onJoinGame={onJoinGame}
                  onSquareClick={onSquareClick}
                  player={player}
                />
              ) : (
                "No games to show"
              )}
            </>
          )}
        </div>
        <ToastContainer />
      </>
    </div>
  );
}

export default App;
