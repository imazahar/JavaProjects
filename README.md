# Project
Write a gaming program to move a knight to the bottom right cell of a chess board from a given

position. The rules of the game are

1. Number of players = 2

2. Each user chooses to place the knight in a cell of his choice through a simple command line

interface

3. User should not be allowed to place the knight on the winning position (at the start of the game).

4. The Knights should move programmatically one after another (1 move for the first knight followed

by 1 move for the second knight and so on) on the board.

5. Each movement of the knight has to be valid knight movement according to Chess (The knight

moves two squares horizontally and one square vertically or 1 square horizontally and 2 squares

vertically)

6. Display each movement on the command line by indicating the start and the end cell.

7. The game ends when one of the knights reaches the END position (bottom right corner) or it can

be a draw based on condition chosen by you.

Optionally, you can choose to ensure that each move is geared towards the corresponding knight

winning (Reaching the end first ).

The game has been named as Knight Rider (2 Player each having 1 knight).

Chessboard has been assumed to be 8 X 8 matrix board. Winning coordinate is 8,8.

All requirement points mentioned in Problem statement has been implemented.

## Folder/File Structure:

• bin : It has runnable jar of the game: knightrider.jar

• config: It has config.properties file to configure language and language string xml. Language

string xml is also in same folder.

• doc: It has documentation.

• src: Folder having source code. Comments have been provided in codes. If any portion is not

clear please let me know.

• Log4j: Log4j plugin xml configuration file

• startgame.bat: Run this batch to start game. Before running this batch file please read

readme.txt.

• readme.txt: Please read this file before launching startgame.bat.

## Additionally,

• Game system intelligence will tell player whether knight with given starting position will ever

result in reaching destination 8,8.

• At each movement game system intelligence will tell whether that move will result in quick win.

• Game system intelligence might offer help to Player for next movement based on situation of

the game.

• Log4j plug-in has been used to generate log. Logs will be generated in log folder.

• There is result folder in which result log will be generated. Both log and result will be auto

generated.

• Old log and result will be saved in Year-Month folder inside each folder.

• Interface has been provided to work in different languages. In cofig.properties change language

and corresponding string_<lang>.xml. Place string_<lang>.xml in same folder.

## Built With

* Eclipse Java IDE, Java 8

## Contributing

TBD

## Versioning

TBD

## Authors

* **Azaharuddin**

## License

This project is not licensed yet, once I will submitt, I will put note about license. Feel free to use it.

## Acknowledgments

* Java 8 collection APIs
