# Simple Card Game

## Project

Game designed for NCVPS AP Computer Science course final project. Last edit 05/22/2019.

It uses NCVPS Activity Starter Code as boiler plate.

To start, run Runner.java and input a number from 2\~52 in the terminal. Then, a GUI will pop up for play.

## Rules

Cards A, 2\~10, J, Q, K have values 1\~13 respectively.

Define the value difference of card X and card Y as the value of Y subtracted by the value of X.

Define two exceptions:

- The value difference of a K card and an A card is 1.

- The value difference of an A card and a K card is -1.

The player chooses the difficulty of this game by defining the amount of cards to deal.

- Less cards means more difficult.

- Suggest number 20 which requires some luck and some thinking.

The player chooses a dealt card and replaces it with an undealt card.

Cards are repeatedly removed and replaced by an undealt card, satisfying one of the following:

- The value difference of the previously replaced card and the selected card to be replaced is 0.

- The value difference of the previously replaced card and the selected card to be replaced is 1.

- The selected card to be replaced has the same suit as the previously replaced card,
  and the value difference of the previously replaced card and the selected card is -1.

If no undealt card is left, the selected card to be replaced is removed without replacement.

The player wins when all cards are removed.

The player loses when there are remaining cards and cannot be removed.