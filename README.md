# Read Me First
Tik Tac Toe Game:

* Built Using Java 8, Spring Boot 2.4.5.

# Getting Started

### Running Project Locally

For further reference, please consider the following sections:

* Prerequisites
  * [Apache Maven](https://maven.apache.org/) is installed and *mvn* command is available
  * [Java 1.8](https://www.oracle.com/be/java/) or above is installed

* Build Steps
  * Clone [https://github.com/tush/kata-tic-tak-toe.git](https://github.com/tush/kata-tic-tak-toe.git)
  * Using Terminal / CMD navigate to the project directory 
  * On the project root directory, fire *"mvn package"* command using Terminal / CMD
  * On the project root directory, fire *"mvn spring-boot:run"* command using Terminal / CMD
  * If above commands are successful, server should be up and running on port 8080: [http://localhost:8080/](http://localhost:8080/)

### Guides
The following API's are available:
* [localhost:8080/game/games](localhost:8080/game/games) GET Request to get list of games already created.
  * *RESPONSE:* List of Game object is returned as JSON Response
  
* [localhost:8080/game/start](localhost:8080/game/start) POST Request to create a new game as Player X.
  * JSON Body Example:
  ```javascript
  { "player": { "name": "PlayerX" } }
  ```
  * API requires Player Information (Player object with name value)
  * *RESPONSE:* Game object is returned as JSON Response
  * *ERROR RESPONSE:* Error object with error message
  
* [localhost:8080/game/connect](localhost:8080/game/connect) POST Request to create a new game as Player O.
  * JSON Body Example:
  ```javascript
  { 
    "gameId": "dcd9b5e8-49ee-47e9-bd53-8a5c5a11f02a",
    "player": { "name": "PlayerO" }
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

