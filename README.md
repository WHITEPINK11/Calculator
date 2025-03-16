Description
This is a Java-based console calculator that supports basic arithmetic operations, advanced functions, and input from both user and files. It also keeps track of calculation history. The program provides three main options for calculations:

Manual expression input – Users can manually input whatever expression they want to.
File-Read based expression input – Users can input file with pre-defined expressions to be calculated.
History of previously calculated expressions – Users can check on their prevously calculations.
Features
Supports basic operations: +, -, *, /, %
Includes additional functions:
abs(number) - Returns absolute value.
sqrt(number) - Returns square root.
power(base, exponent) - Computes exponentiation.
round(number) - Rounds to the nearest integer.
Supports both manual input and file input.
Maintains a calculation history.
Validates expressions and ensures balanced brackets.




How to Use
Running the Calculator
Compile the program:
javac Calculator.java


Run the program:
java Calculator



Choose an option from the menu:
1 - Enter expressions manually
2 - Read expressions from a file
3 - View calculation history
4 - Exit
Example Usage
Manual Input
Hello and welcome! This is your calculator! 

Here are your options:
1) Enter expressions manually.
2) Read expressions from a file.
3) Check calculation history.
4) Exit.

Choose one of the options: 1

Enter your expression (´｡• ᵕ •｡): 34+(76-45)*2 - abs(-5)
 Result: 91
File Input
Create a text file (e.g., input.txt) with expressions, and choose option 2 and enter input.txt when prompted.
Hello and welcome! This is your calculator! 

Here are your options:
1) Enter expressions manually.
2) Read expressions from a file.
3) Check calculation history.
4) Exit.

Choose one of the options: 2
Enter the file name (e.g., input.txt): input.txt

Reading your file: input.txt 

Processing your expression: 5 + 3
Result: 8

Processing your expression: 34+(76-45)*2 - abs(-5)
Result: 91

Processing your expression: 9+1*(12*12)-100
Result: 53
Error Handling
Detects unbalanced brackets.
Handles division by zero.
Catches invalid inputs and returns error messages.



Dependencies
Java SE (JDK 8+)
License



This project is open-source under the MIT License.
