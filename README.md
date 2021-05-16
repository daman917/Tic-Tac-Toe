# My Personal Project

## Tic-Tac-Toe Game

The application will:
- run a 2 player Tic-Tac-Toe game
- allow you to register as a unique player with a name and record
- have a leaderboard of players with best records

This game is for any **2 or more** people who want to play Tic-Tac-Toe 
against each other.

This project is of interest to me because I have played lots of computer games
and want to see how they work on the *inside*. Such as what is necessary to
make it all work and how they make the games run so smoothly.

## User Stories

As a user, I want to be able to:
- create a new player that goes on the leaderboard
- have my player's wins and losses recorded for display on leaderboard
- play another player in tic-tac-toe
- be able to see the state of the tic-tac-toe board as the game progresses
- save the current leaderboard to file
- load an earlier leaderboard from file

## Phase 4: Task 2

I made a bi-directional association. This association is between the 
Leaderboard and Player classes. Leaderboard has a field of type list of
Players and Player has a field of type Leaderboard. Every method in the
Leaderboard class uses the list of Players, and the method getRank() in
Player uses its Leaderboard field.

## Phase 4: Task 3

I would have:
- refactored the TicTacToe class into multiple classes to better follow
the Single Responsibility Principle
- one of these classes would perform all necessary functions for the 
leaderboard in the game, so the Leaderboard class would just be a list
of players in it, to make things more cohesive
