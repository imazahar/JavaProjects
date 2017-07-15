## Problem Statement:
Multiply two numbers. Assume each can have 30-40 digits. Numbers will be positive.

## Solution:

I have enhanced the solution such that each number can have number of digits <= integer max limit.

This program will calculate multiplication of two numbers. Two numbers can have N number of digits

where N <= integer max limit. It means number of digit in the number should be less than or equal to

Integer.MAX_VALUE.

Program has been tested for numbers who has 100 digits.

E.g.

Number 1=

99977777777777777777777779999999999999999999999999999999999999999999999999999999999

999999999999999999999999999

Number 2 =

99977777777777777777777779999999999999999999999999999999999999999999999999999999999

999999999999999999999999999

But it will work for more than 100 and less than or equal to Integer.MAX_VALUE.

Bare minimum number of list has been used and they are cleaned and reused to save memory space.

You can provide numbers in file folder:

number1.txt and number2.txt

Folder structure:

• bin : It has runnable jar of the game: multiply.jar

• config: It has config.properties file to provide path of files having numbers. Do not change the

property. Just change its value if you want.

• file: It has files which will have numbers.

• doc: It has documentation.

• src: Folder having source code. Comments have been provided in codes. If any portion is not

• clear please let me know.

• Log4j: Log4j plugin xml configuration file

• multiply.bat: Run this batch to multiply two numbers given in files.

Program can be enhanced to support multiplications of numbers which can have any number of digits.

For that each input has to be broken into multiple linked list and then process them accordingly just like

big data system. Map, calculate and then reduce. Please let me know to clarify what approach it means.

## Deployment and Run

To run the multiplication please edit multiply.bat. Change the path of jdk8 (64 bit) bin to the path on your system. Save and close the startgame.bat.
Run multiply.bat !!

## Built With

* Eclipse Java IDE, Java 8

## Deployment and Run
To run the game please edit startgame.bat. Change the path of jdk8 (64 bit) bin to the path on your system. Save and close the startgame.bat.
Run startgame.bat to play knight rider game. Enjoy !!

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
