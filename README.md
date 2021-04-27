# Read Me First
Tik Tac Toe Game:

* Built Using Java 8, Spring Boot 2.4.5.

# Getting Started

### Running Project Locally

For further reference, please consider the following sections:

* Prerequisites
  * [Apache Maven](https://maven.apache.org/) is installed and *mvn* command is available
  * [Java 1.8](https://www.oracle.com/be/java/) or above is installed
  * *OPTIONAL: For building UI:* [Yarn](https://classic.yarnpkg.com/en/) or [Node](https://nodejs.org/en/) is installed

* Build Steps
  * Clone [https://github.com/tush/kata-tic-tak-toe.git](https://github.com/tush/kata-tic-tak-toe.git)
  * Using Terminal / CMD navigate to the project directory
    * UI Build Steps: (NOTE: Building UI is not mandatory as Build artifact is available in *"resources/static"*)
      * Using Terminal / CMD navigate to the UI directory *"cd src/ui/"*
        * On the *ui* directory, fire *"yarn install"* or *"npm install"* command using Terminal / CMD
        * On the *ui* directory, fire *"yarn build"* or *"npm run build"* command using Terminal / CMD
  * Spring Boot Application Build Steps:
    * Using Terminal / CMD navigate to the root directory *"kata-tic-tak-toe"*
    * On the project (kata-tic-tak-toe) root directory, fire *"mvn package"* command using Terminal / CMD
    * On the project (kata-tic-tak-toe) root directory, fire *"mvn spring-boot:run"* command using Terminal / CMD
  * If above commands are successful, server should be up and running on port 8080: [http://localhost:8080/](http://localhost:8080/)

  
### Guides
The following API's are available:
* [localhost:8080/player/add](localhost:8080/player/add) POST Request to add a new Player.
  * JSON Body Example:
  ```javascript
  { "player": { "name": "PlayerX" } }
  ```
  * API requires Player Information (Player object with name value)
  * *RESPONSE:* Player object is returned as JSON Response
  
* [localhost:8080/game/games](localhost:8080/game/games) GET Request to get list of games already created.
  * *RESPONSE:* List of Game object is returned as JSON Response
  
* [localhost:8080/game/start](localhost:8080/game/start) POST Request to create a new game as Player X.
  * JSON Body Example:
  ```javascript
  {player: {name: "PLayer1", id: "1f1ed296-f6bc-462c-b9a9-91a4c85e36da"}}
  ```
  * API requires Player Information (Player object with name value)
  * *RESPONSE:* Game object is returned as JSON Response
  * *ERROR RESPONSE:* Error object with error message
  
* [localhost:8080/game/connect](localhost:8080/game/connect) POST Request to create a new game as Player O.
  * JSON Body Example:
  ```javascript
  { 
    "gameId": "dcd9b5e8-49ee-47e9-bd53-8a5c5a11f02a",
    "player": {name: "Player2", id: "973966ac-55ba-4e03-81d3-990efa079ddc"}
  }
  ```
  * API requires PlayerO Information (Player object with name value) and a valid GameID
  * *RESPONSE:* Game object is returned as JSON Response
  * *ERROR RESPONSE:* Error object with error message
  
* [localhost:8080/game/play](localhost:8080/game/play) POST Request to play game step.
  * JSON Body Example:
  ```javascript
  { 
    "gameId": "dcd9b5e8-49ee-47e9-bd53-8a5c5a11f02a",
    "type": "X",
    "posX": "2",
    "posY": "2"
  }
  ```
  * *RESPONSE:* Game object is returned as JSON Response
  * *ERROR RESPONSE:* Error object with error message

### Playing Game via UI

* Once Server is up and running, open two browser windows and visit URL [localhost:8080/](localhost:8080/)
* A screen with form to enter Player name will be shown
  ![Login Screen](./src/assets/Screen1.png?raw=true "Login Screen")
* Provide Player name and click *"Submit"*
* Once player is added successfully, a screen with *"Create"* button and *"Game List"* along with *"Refresh"* button will be shown
  ![Game List Screen](./src/assets/Screen2.png?raw=true "Game List Screen")
* Click *"Create"* button. Once Game is created successfully, game list will be refreshed to show new game
* Use *"Expand"* *"Collapse"* buttons to toggle view of *Game Board*
  ![Game List Screen](./src/assets/Screen3.png?raw=true "Game List Screen")
* In another player's browser window, once logged in by providing Player name, same game list will be shown. 
* Click *"Join"* button to join the game. Once joined successfully, Game Board will be refreshed. Use *Refresh* button in other player's browser window to refresh the game data
  ![Join Game Screen](./src/assets/Screen4.png?raw=true "Join Game Screen")
* Click *"Board Cell"* to make a move. Use *Refresh* button in other player's browser window to refresh the game data. Repeat this step for performing game moves.
  ![Game Play Screen](./src/assets/Screen5.png?raw=true "Game Play Screen")
  ![Game Play Screen](./src/assets/Screen6.png?raw=true "Game Play Screen")
* Once game is finished or drawn, appropriate game status and winner information will be shown
  ![Game Finished Screen](./src/assets/Screen7.png?raw=true "Game Finished Screen")