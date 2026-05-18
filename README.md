# BlackJack

[🇧🇷 Versão em Português](README-pt-BR.md)

This project simulates the Blackjack game, also known as 21. There are 4 players: 1 human player and 3 CPUs. Whoever gets the score closest to 21 wins. If two or more players tie for the best score, it is a draw. If all players exceed 21, it is also considered a draw.

## Installation

Installation is straightforward — just download the APK available at `app/release/app-release.apk`.

Night mode is recommended if your device supports it, as the layout was not designed for light mode. The experience will be better in dark mode.

The target API is set to 31, but no resources were used that would compromise compatibility with older APIs.

## About the App

- **Initial cards** — Two cards are dealt to each player at the start;
- **Player order** — The order is random, so sometimes you go first, and sometimes one of the three CPUs starts;
- **Points** — Each participant has partial points (sum of cards drawn after the initial deal) and total points (sum of all cards);
- **Mandatory draw** — Every player must draw another card if their total points are below 16;
- **Bust or 21** — A message is shown when any player busts or reaches 21.

## Examples

Below, the stop button is black, meaning it is disabled. This happens because the player has fewer than 16 total points and must keep drawing.

<p align="center">
<img height="400" src="imgs_readme/menor_16.jpeg" width="200"/>
</p>

Next, CPU-3 busted, so a warning message was shown.

<p align="center">
<img height="400" src="imgs_readme/info.jpeg" width="200"/>
</p>

After the game ends, you are taken to the winners screen, where you can see who won or if it was a draw, along with each player's total score and the cards they drew.

<p align="center">
<img height="400" src="imgs_readme/tela_vencedores.jpeg" width="200"/>
</p>

Clicking on a player reveals all the cards they received.

<p align="center">
<img height="400" src="imgs_readme/todas_as_cartas.jpeg" width="200"/>
</p>

Finally, the **Finish** button closes the app and the **Play Again** button starts a new match.
