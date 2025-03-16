import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Calculator {

    // ArrayList for storing calculation history
    private static final ArrayList<String> calculationHistory = new ArrayList<>();

    // Method to check if brackets are balanced
    private static boolean checkBrackets(String userExpression) {
        ArrayDeque<Character> bracketStack = new ArrayDeque<>();
        for (char c : userExpression.toCharArray()) {
            if (c == '(') {
                bracketStack.addLast(c);
            }
            else if (c == ')') {
                if (!bracketStack.isEmpty()) {
                    bracketStack.removeLast();
                }
                else {
                    return false;
                }
            }
        }
        return bracketStack.isEmpty();
    }

    // Method to define operator priority
    private static int getOperatorPriority(char operator) {
        if (operator == '+' || operator == '-') {
            return 1;
        }
        else if (operator == '*' || operator == '/' || operator == '%') {
            return 2;
        }
        return 0;
    }

    // Method to convert expression to postfix notation
    private static ArrayDeque<String> convertToPostfix(String userExpression) {
        ArrayDeque<String> postfixOutput = new ArrayDeque<>();
        ArrayDeque<Character> operatorStack = new ArrayDeque<>();

        StringBuilder currentNumber = new StringBuilder();
        int index = 0;

        while (index < userExpression.length()) {
            char currentChar = userExpression.charAt(index);

            // Skipping whitespace
            if (Character.isWhitespace(currentChar)) {
                index++;
                continue;
            }

            // Collecting numbers including negatives
            if (Character.isDigit(currentChar) || currentChar == '.' || (currentChar == '-' && (index == 0 || userExpression.charAt(index - 1) == '('))) {
                currentNumber.append(currentChar);
                index++;

                while (index < userExpression.length() && (Character.isDigit(userExpression.charAt(index)) || userExpression.charAt(index) == '.')) {
                    currentNumber.append(userExpression.charAt(index));
                    index++;
                }

                postfixOutput.addLast(currentNumber.toString());
                currentNumber = new StringBuilder();
                continue;
            }

            // Handling functions
            if (currentChar == 'a' && userExpression.startsWith("abs(", index)) {
                // If we see abs, then its function abs
                operatorStack.addLast('A');
                index += 4; // Skipping 'abs(' to get numbers inside
                continue;
                // The same operations in here, but with different functions (sqrt, power, round)
            }
            else if (currentChar == 's' && userExpression.startsWith("sqrt(", index)) {
                operatorStack.addLast('S');
                index += 5;
                continue;
            }
            else if (currentChar == 'p' && userExpression.startsWith("power(", index)) {
                operatorStack.addLast('P');
                index += 6;
                continue;
            }
            else if(currentChar == 'r' && userExpression.startsWith("round(", index)) {
                operatorStack.addLast('R');
                index += 6;
                continue;
            }

            // Handling operators and brackets
            if (currentChar == '(') {
                operatorStack.addLast(currentChar);
            }
            else if (currentChar == ')') {
                while (!operatorStack.isEmpty() && operatorStack.getLast() != '(') {
                    postfixOutput.addLast(String.valueOf(operatorStack.removeLast()));
                }
                if (!operatorStack.isEmpty()){
                    operatorStack.removeLast();
                }

                if (!operatorStack.isEmpty() && (operatorStack.getLast() == 'A' || operatorStack.getLast() == 'S' || operatorStack.getLast() == 'P')) {
                    postfixOutput.addLast(String.valueOf(operatorStack.removeLast()));
                }
            }
            else if (currentChar == '+' || currentChar == '-' || currentChar == '*' || currentChar == '/' || currentChar == '%') {
                while (!operatorStack.isEmpty() && operatorStack.getLast() != '(' && getOperatorPriority(operatorStack.getLast()) >= getOperatorPriority(currentChar)) {
                    postfixOutput.addLast(String.valueOf(operatorStack.removeLast()));
                }
                operatorStack.addLast(currentChar);
            }
            else if (currentChar == ',') {
                while (!operatorStack.isEmpty() && operatorStack.getLast() != '(') {
                    postfixOutput.addLast(String.valueOf(operatorStack.removeLast()));
                }
            }

            index++; // Moving index for the next symbols

        }

        while (!operatorStack.isEmpty()) {
            postfixOutput.addLast(String.valueOf(operatorStack.removeLast()));
        }
        return postfixOutput;
    }

    // Method to calculate result from postfix notation
    private static String calculateFromPostfix(ArrayDeque<String> postfixExpression) {
        ArrayDeque<Double> numberStack = new ArrayDeque<>();

        for (String operator : postfixExpression) {
            try {
                if (operator.equals("+")) {
                    double secondNum = numberStack.removeLast();
                    double firstNum = numberStack.removeLast();
                    numberStack.addLast(firstNum + secondNum);
                }
                else if (operator.equals("-")) {
                    double secondNum = numberStack.removeLast();
                    double firstNum = numberStack.removeLast();
                    numberStack.addLast(firstNum - secondNum);
                }
                else if (operator.equals("*")) {
                    double secondNum = numberStack.removeLast();
                    double firstNum = numberStack.removeLast();
                    numberStack.addLast(firstNum * secondNum);
                }
                else if (operator.equals("/")) {
                    double secondNum = numberStack.removeLast();
                    double firstNum = numberStack.removeLast();
                    double result = firstNum / secondNum;
                    if (Double.isInfinite(result) || Double.isNaN(result)) {
                        return "Error: Division by zero is not allowed!!! (o_o)";
                    }
                    numberStack.addLast(result);
                }
                else if (operator.equals("%")) {
                    double secondNum = numberStack.removeLast();
                    double firstNum = numberStack.removeLast();
                    double result = firstNum % secondNum;
                    if (Double.isInfinite(result) || Double.isNaN(result)) {
                        return "Error: Division by zero is not allowed!!! (o_o)";
                    }
                    numberStack.addLast(result);
                }
                else if (operator.equals("A")) {
                    double num = numberStack.removeLast();
                    numberStack.addLast(Math.abs(num));
                }
                else if (operator.equals("S")) {
                    double num = numberStack.removeLast();
                    if (num < 0){
                        return "Error: Square root of negative number!!!";
                    }
                    numberStack.addLast(Math.sqrt(num));
                }
                else if (operator.equals("P")) {
                    double exponent = numberStack.removeLast();
                    double base = numberStack.removeLast();
                    numberStack.addLast(Math.pow(base, exponent));
                }
                else if (operator.equals("R")) {
                    double num = numberStack.removeLast();
                    numberStack.addLast((double)Math.round(num));
                }
                else {
                    numberStack.addLast(Double.parseDouble(operator));
                }
            } catch (Exception e) {
                return "Error: Invalid input detected!!!";
            }
        }
        // Getting final result
        double finalResult = numberStack.removeLast();
        if (finalResult == Math.floor(finalResult)) {
            return String.valueOf((int) finalResult);
        }
        return String.format("%.2f", finalResult);
    }

    // Main calculation method
    private static String calculateExpression(String userExpression) {
        if (!checkBrackets(userExpression)) {
            return "Error: Unbalanced brackets!!!";
        }

        ArrayDeque<String> postfix = convertToPostfix(userExpression);
        return calculateFromPostfix(postfix);
    }

    // Method for manual expression input
    public static void calculateManually() {
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.print("\nEnter your expression (＾▽＾): ");
            String userExpression = input.nextLine().trim();

            if (userExpression.isEmpty()) {
                System.out.println("Error: Empty input is not allowed!!!");
                continue;
            }

            String result = calculateExpression(userExpression);
            System.out.println("(o_o) Result: " + result);
            calculationHistory.add(userExpression + " = " + result);

            System.out.print("\nDo you want to continue? (y/n): ");
            String userChoice = input.nextLine().trim();

            if (userChoice.equalsIgnoreCase("n")) {
                System.out.println("Thanks for calculating!ヾ(･ω･｡)");
                break;
            }
            else if (!userChoice.equalsIgnoreCase("y")) {
                System.out.println("Please enter 'y' or 'n'!");
            }
        }
    }

    // Method for calculating from file input
    public static void calculateFromFile() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the file name (e.g., input.txt): ");
        String fileName = input.nextLine().trim();

        try {
            File inputFile = new File(fileName);
            Scanner fileScanner = new Scanner(inputFile);
            System.out.println("\nReading your file: " + fileName + " (＾▽＾)");

            while (fileScanner.hasNextLine()) {
                String userExpression = fileScanner.nextLine().trim();
                if (userExpression.isEmpty()){
                    continue;
                }

                System.out.println("\nProcessing your expression: " + userExpression);
                String result = calculateExpression(userExpression);

                System.out.println("Result: " + result);
                calculationHistory.add(userExpression + " = " + result);
            }
            fileScanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("Error: File '" + fileName + "' not found!!!");
        }
    }
}