/*Justin Hinze
 *csc4380
 *9-4-15
 *Assignment1 - calculator
 */
package calculator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Stack;


public class InfixEvaluator {

    private final ArrayList<String> tokens = new ArrayList();
    private final Stack<String> opStack = new Stack();
    private final Stack<Double> valStack = new Stack();
    private final ArrayList<String> postfix = new ArrayList();
    private int index = 0;
    private boolean parseCorrect = true;
    private boolean isop = false;
    private final DecimalFormat df = new DecimalFormat("0");

    public InfixEvaluator(int n) {
        df.setMaximumFractionDigits(n);
    }

    //increments index, returns true if tokens left, false otherwise
    private boolean nextToken() {
        index++;
        return !(index >= tokens.size());
    }

    //returns current token
    private String currentToken() {
        return tokens.get(index);
    }

    //start root node of recursive descent parser
    //returns true if parse was a success
    //false otherwise or false if all tokens not parsed
    //a tree can not have more than 1 root node
    private boolean parse() {
        parseCorrect = true;
        index = 0;
        expr();
        if (nextToken()) {
            return false;
        }
        return parseCorrect;
    }

    //returns true if expected token is current token
    //returns false otherwise or if no tokens are left
    private boolean expect(String s) {
        if (index >= tokens.size()) {
            return false;
        } else {
            return currentToken().equals(s);
        }
    }
    
    //grammar for expr
    private void expr() {
        isop = false;
        term();
        while (expect("+") || expect("-")) {
            isop = true;
            nextToken();
            term();
        }
    }

    //grammar for term
    private void term() {
        isop = false;
        factor();
        while (expect("*") || expect("/") || expect("^")) {
            isop = true;
            nextToken();
            factor();
        }
    }

    //grammar for factor
    private void factor() {
        if (expect("√")) {
            nextToken();
        }
        if (value()) {
            nextToken();
        } else if (expect("(")) {
            nextToken();
            expr();
            if (expect(")")) {
                nextToken();
            } else {
                parseCorrect = false;
            }
        } else {
            parseCorrect = false;
        }
    }

    //grammar for value
    private boolean value() {
        if (index >= tokens.size()) {
            return false;
        }
        if (isDouble(tokens.get(index))) {
            return true;
        }
        if (expect("-")) {
            nextToken();
            if (index >= tokens.size()) {
                return false;
            }
            if (expect("√")) {
                nextToken();
            }
            if (isDouble(tokens.get(index))) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    //performs recursive calculations of properly formatted post fix equation
    //equation seperated into tokens in postix array list
    //returns value after math is performed
    private double doMath() {
        double x, y;
        if (postfix.isEmpty()) {
            return valStack.pop();
        }
        String s = postfix.remove(0);
        switch (s) {
            case "+":
                x = valStack.pop();
                y = valStack.pop();
                valStack.push(y + x);
                return doMath();
            case "-":
                x = valStack.pop();
                y = valStack.pop();
                valStack.push(y - x);
                return doMath();
            case "*":
                x = valStack.pop();
                y = valStack.pop();
                valStack.push(y * x);
                return doMath();
            case "/":
                x = valStack.pop();
                y = valStack.pop();
                valStack.push(y / x);
                return doMath();
            case "^":
                x = valStack.pop();
                y = valStack.pop();
                valStack.push(Math.pow(y, x));
                return doMath();
            case "√":
                valStack.push(Math.sqrt(valStack.pop()));
                return doMath();
            case "_":
                valStack.push(-1 * valStack.pop());
                return doMath();
            default:
                valStack.push(Double.parseDouble(s));
                return doMath();
        }
    }

    //takes a properly formatted infix equation and creates an array list of tokens
    //in post fix format. Shunting-yard algorithm
    private String toPostFix() {
        postfix.clear();
        opStack.clear();
        boolean operand = true;
        String ps = "";
        for (String s : tokens) {
            if (isDouble(s)) {
                postfix.add(s);
                operand = false;
                continue;
            }
            if (isLeftParen(s)) {
                opStack.push(s);
                operand = true;
                continue;
            }
            if (s.equalsIgnoreCase("-") && operand) {
                opStack.push("_");
                continue;
            }
            if (isOperator(s)) {
                operand = true;
                while (!opStack.isEmpty() && !isLeftParen(opStack.peek())) {
                    if (precedence(s) <= precedence(opStack.peek())) {
                        postfix.add(opStack.pop());
                    } else {
                        break;
                    }
                }
                opStack.push(s);
                continue;
            }
            if (isRightParen(s)) {
                operand = false;
                while (!opStack.isEmpty() && !isLeftParen(opStack.peek())) {
                    postfix.add(opStack.pop());
                }

                opStack.pop();
                continue;
            }
        }
        while (!opStack.isEmpty()) {
            postfix.add(opStack.pop());
        }
        for (String str : postfix) {
            ps += str;
            ps += " ";
        }
//        System.out.println(ps);
        return ps;

    }

    //returns true if string is an operator, false otherwise
    private boolean isOperator(String s) {
        switch (s) {
            case "+":
            case "-":
            case "*":
            case "/":
            case "^":
            case "√":
            case "_":
                return true;
            default:
                return false;
        }
    }

    //returns true if string is a double, false otherwise
    private boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //returns true if string is sqrt symbol, false otherwise
    private boolean isSqrt(String s) {
        return s.equals("√");
    }

    //returns true if string is "(", false otherwise
    private boolean isLeftParen(String s) {
        return s.equals("(");
    }

    //returns true if string is ")", false otherwise
    private boolean isRightParen(String s) {
        return s.equals(")");
    }

    //takes a string and performs the necessary steps for calculation
    //1. resets variables from last calculation.
    //2. divids the string into tokens
    //3. uses the lexical analiser to check format, returns "lexical error" upon failure
    //4. converts the string to post fix notation
    //5. perfoms the calculations in the equation.
    //6. returns the value in string form formatted to set digits upon success,
    //      returns computational error if invalid action performed.
    public String evaluate(String s) {
        valStack.clear();
        buildTokens(s);
        String output = "";
        if (!parse()) {
            return "Lexical error";
        }
        toPostFix();
        try {
            output = df.format(doMath());
        } catch (Exception e) {
            return "Computational error";
        }
        if (output.equals("�")) {
            return "Computational error";
        }
        return output;
    }

    //takes a string of an operator and returns its precedence level
    //high precedence has priority, returns -1 for unrecognised character
    private int precedence(String s) {
        switch (s) {
            case "+":
                return 10;
            case "-":
                return 10;
            case "*":
                return 20;
            case "/":
                return 20;
            case "^":
                return 30;
            case "_":
                return 10;
            case "√":
                return 40;
            default:
                return -1;
        }
    }

    //takes a string of an infix equation and builds the tokens array
    //numbers are collected to a single token in between operators
    private void buildTokens(String s) {
        tokens.clear();
        StringBuilder in = new StringBuilder();
        String operator = "";
        for (char c : s.toCharArray()) {
            if (c == 46 || (c > 47 && c < 58)) {
                in.append(c);
                continue;
            }
            switch (c) {
                case '+':
                    operator = "+";
                    break;
                case '-':
                    operator = "-";
                    break;
                case '*':
                    operator = "*";
                    break;
                case '/':
                    operator = "/";
                    break;
                case '^':
                    operator = "^";
                    break;
                case '(':
                    operator = "(";
                    break;
                case ')':
                    operator = ")";
                    break;
                case '√':
                    operator = "√";
                    break;
            }
            if (in.length() > 0) {
                tokens.add(in.toString());
                in.delete(0, in.length());
            }
            tokens.add(operator);
        }
        if (in.length() > 0) {
            tokens.add(in.toString());
            in.delete(0, in.length());
        }
    }
}
