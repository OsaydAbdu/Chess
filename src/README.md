## Source code files structure

This code base uses Model-View-Controller (MVP) design as follows:  
### chess
This the model of the MVP where it contains all the code that describe a chess game (pieces, actions, games, boards, ... etc). 

### gui
This the view of the MVP where it contains all the code that is responsible for drawing on the screen (includeing drawing pieces, moving pices as actions,
drawing boarda, ... etc). 

### gameController
This the controller of the MVP where it contains all the code that is responsible for communication between the model and the view. 

## Game logic workfolw

At the start of the game the `controller` telse the `view` to draw a basic Chess board and tells the `model` to create a basic Chess board representation.  
Upon any action taken from the user, the `view` detecst the action and passes it to the `controllor` which convert it into a move that the model would underand 
and peroform it. Following that the `controller` tells the view what to draw next based on the result of the move the `model` performed. And this continues until 
end of the game.
