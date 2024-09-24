# Blackjack Game in Java

Welcome to the Blackjack game implemented in Java! This program allows you to play Blackjack against a dealer, with features for betting and managing player funds.

## Overview 

The Blackjack game is designed to simulate the card game where the objective is to beat the dealer by having a hand value that is as close to 21 as you can get without going over. The player can choose to hit (be dealt another card) or stand (keep their current hand). 

### Features
- Player and dealer gameplay
- Betting system to manage player money
- Automatic hand evaluation
- User-friendly command-line interface

### How to Play
1. Upon starting the game, you will be prompted to enter your name and the initial amount of money you wish to start with.
2. Place your bets for each round.
3. Choose to either hit or stand during your turn.
4. The dealer will play after you, following standard Blackjack rules (explained in Gameplay Rules).
5. After each round, your total money will be updated based on the outcome.

### Gameplay Rules
- The player can hit or stand.
- The dealer must hit until their hand value is 17 or higher.
- Aces can be worth 1 or 11, based on the context of the hand.
- If either player or dealer exceeds 21, they bust and lose the round.

#### Code Structure
- Game.java: Contains the main logic for the Blackjack game, including methods for player and dealer actions, hand evaluation, and determining the winnter.
- Card.java (nested within Game.java): Represents individual playing cards.
- Deck.java (nested within Game.java): Represents a standard deck of 52 cards and includes methods for shuffling and drawing cards.
- Main.java: The entry point of the application athat handles user interaction and game flow.

#### Author
Tristan Cravens
