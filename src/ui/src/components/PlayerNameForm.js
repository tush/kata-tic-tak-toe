import { useState } from "react";

function PlayerNameForm({ onSetPlayerName }) {
  const [text, setText] = useState("");

  const onSubmit = (e) => {
    e.preventDefault();
    if (!text) {
      alert("Add Text");
      return;
    }
    onSetPlayerName(text);
  };

  return (
    <form className="add-form" onSubmit={onSubmit}>
      <div className="form-control">
        <label>Player Name</label>
        <input
          type="text"
          placeholder="Add Name"
          onChange={(e) => setText(e.target.value)}
        />
      </div>
      <input type="submit" className="btn btn-block" value="Submit" />
    </form>
  );
}

export default PlayerNameForm;
