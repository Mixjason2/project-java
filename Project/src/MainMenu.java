import javax.swing.*;
import java.util.ArrayDeque;
import java.util.Deque;

public class MainMenu {
    private static IntegerStack integerData;
    private static OperationStack operationData;
    private static StatementStack statementData;
    private static IterationStack iterationData;
    private static InfixToPostfixStack postfixData;
    private static final String Line_Dash = "-------------------------------------------------------";
    private static String statement , abc = "";
    private static int statementSolution , iterationSolution;

    public static void main(String[] args) throws Exception {
        statementData = new ProjectStack();
        iterationData = new ProjectStack();
        integerData = new ProjectStack();
        operationData = new ProjectStack();
        postfixData = new ProjectStack();
        showmenu();
    }

    private static void showmenu() throws Exception {
        String prompt = String.format(
                "%s\n %30s\n %s\n %s\n %s\n %30s\n %s\n %s\n %s\n %s\n %s\n %30s\n %s\n %s\n %s\n %s",
                "Main Menu",
                Line_Dash,
                "--> Enter Statement Method <--",
                "1) Enter the entire statement",
                "2) Enter one by one",
                Line_Dash,
                "--> Get Solution Method <--",
                "3) Solution of the entire statement",
                "4) Solution of one by one method",
                "5) Check the summary",
                "6) Infix Notation - to - Postfix Notation",
                Line_Dash,
                "--> Other Method <--",
                "7) Clear Statement Data",
                "8) Clear Iteration Data",
                "9) Exit Program"
        );
        String selectedchoice = JOptionPane.showInputDialog(null, prompt, "Input Your Choice", JOptionPane.QUESTION_MESSAGE);
        try {
            int option = Integer.parseInt(selectedchoice);
            switch (option) {
                case 1 -> enterStatement();
                case 2 -> enterIteration();
                case 3 -> getStatementSolution();
                case 4 -> getIterationSolution();
                case 5 -> checkSummary();
                case 6 -> getInfixToPostfixSolution();
                case 7 -> clearStatement();
                case 8 -> clearIteration();
                case 9 -> {
                    statementData.clear();
                    iterationData.clear();
                    System.exit(0);
                }
                default -> {
                    JOptionPane.showMessageDialog(null, "Operation Not Found.", "Message", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            showmenu();
        }
    }
    public static void enterStatement() throws Exception {
        statement = JOptionPane.showInputDialog(null, "Input your Statement :");
        boolean checkStatement = balancingSymbol(statement);
        if (!checkStatement) {
            JOptionPane.showMessageDialog(null, "Please type correct equation.", "Message", JOptionPane.INFORMATION_MESSAGE);
        }
        showmenu();
    }

    private static void enterIteration() throws Exception {
        if (iterationData.empty()) {
            int number1 = Integer.parseInt(JOptionPane.showInputDialog(null, "Input your First Number :"));
            if (number1 != 0 && number1 != 1 && number1 != 3 && number1 != 4 && number1 != 5 && number1 != 6 && number1 != 7 && number1 != 8 && number1 != 9 && number1 != 2) {
                JOptionPane.showMessageDialog(null, "Please type correct operation.", "Message", JOptionPane.INFORMATION_MESSAGE);
                abc = "";
                iterationData.clear();
                showmenu();
            }
            abc += number1;
            iterationData.push(number1);
        }
        String operation = JOptionPane.showInputDialog(null, "Input your Operation :");
        char op = operation.charAt(0);
        abc += op;
        if (op != '+' && op != '-' && op != '*' && op != '/' && op != '^') {
            JOptionPane.showMessageDialog(null, "Please type correct operation.", "Message", JOptionPane.INFORMATION_MESSAGE);
            abc = "";
            iterationData.clear();
            showmenu();
        } else {
            iterationData.push(op);
            int number2 = Integer.parseInt(JOptionPane.showInputDialog(null, "Input your Second Number :"));
            if (number2 != 0 && number2 != 1 && number2 != 3 && number2 != 4 && number2 != 5 && number2 != 6 && number2 != 7 && number2 != 8 && number2 != 9 && number2 != 2) {
                JOptionPane.showMessageDialog(null, "Please type correct operation.", "Message", JOptionPane.INFORMATION_MESSAGE);
                abc = "";
                iterationData.clear();
                showmenu();
            }
            abc += number2;
            iterationData.push(number2);
            showmenu();
        }
    }
    private static void getStatementSolution() throws Exception {
        if (statementData.empty()){
            JOptionPane.showMessageDialog(null , "Please input Statement Equation first.");
            showmenu();
        }
        char[] charStatement = statement.toCharArray();

        for (int i = 0 ; i < statement.length() ; i++) {
            if (charStatement[i] >= '0' && charStatement[i] <= '9') {
                StringBuffer stbf = new StringBuffer();
                while (i < charStatement.length && charStatement[i] >= '0' && charStatement[i] <= '9') {
                    stbf.append(charStatement[i++]);
                }
                integerData.push(Integer.parseInt(stbf.toString()));
                i--;
            } else if (charStatement[i] == '(') {
                operationData.push(charStatement[i]);
            } else if (charStatement[i] == '+' || charStatement[i] == '-' || charStatement[i] == '*' || charStatement[i] == '/') {
                while (!operationData.empty() && checkPrecedence(charStatement[i], (Character) operationData.top())) {
                    integerData.push(calculate((Character) operationData.pop(), (Integer) integerData.pop(), (Integer) integerData.pop()));
                }
                operationData.push(charStatement[i]);
            } else if (charStatement[i] == ')') {
                while ((Character) operationData.top() != '(') {
                    integerData.push(calculate((Character) operationData.pop(), (Integer) integerData.pop(), (Integer) integerData.pop()));
                    if ((Character) operationData.pop() == '(') {
                        break;
                    }
                }
            }
        }
        while (!operationData.empty()) {
            integerData.push(calculate((Character) operationData.pop(), (Integer) integerData.pop(), (Integer) integerData.pop()));
        }
        statementSolution = (int) integerData.pop();
        JOptionPane.showMessageDialog(null , "Statement Solution : " + statement + " = " + statementSolution);
        showmenu();
    }
    public static boolean checkPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') {
            return false;
        }
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            return false;
        } else {
            return true;
        }
    }
    public static int calculate(char op, int b, int a) {
        switch (op) {
            case '+':   return a + b;
            case '-':   return a - b;
            case '*':   return a * b;
            case '/':   if (b == 0) {
                            throw new UnsupportedOperationException("Cannot divide by zero");
                        }
                        return a / b;
        }
        return 0;
    }

    private static void getIterationSolution() throws Exception {
        if (iterationData.empty()){
            JOptionPane.showMessageDialog(null , "Please input Statement Equation first.");
            showmenu();
        }
        int i = 1;
        while (i > 0) {
            iterationSolution = (int) iterationData.pop();
            if (iterationData.empty()) {
                break;
            }
            char op = (char) iterationData.pop();
            int a = (int) iterationData.pop();
            switch (op) {
                case ('+') -> {
                    iterationSolution += a;
                    iterationData.push(iterationSolution);
                    break;
                }
                case ('-') -> {
                    iterationSolution -= a;
                    iterationData.push(iterationSolution);
                    break;
                }
                case ('*') -> {
                    iterationSolution *= a;
                    iterationData.push(iterationSolution);
                    break;
                }
                case ('/') -> {
                    iterationSolution /= a;
                    iterationData.push(iterationSolution);
                    break;
                }
            }
        }
        JOptionPane.showMessageDialog(null , "Iteration Solution : " + abc + " = " + iterationSolution);
        showmenu();
    }
    private static boolean balancingSymbol(String a) throws Exception {
        for (int i = 0; i < a.length(); i++) {
            char c = a.charAt(i);
            if (c != ')') {
                statementData.push(c);
            } else {
                if (statementData.empty()) {
                    return false;
                }
                char check;
                for (int j = a.indexOf(c); j > 0; j--) {
                    check = (char) statementData.pop();
                    if (check == '(') {
                        break;
                    } else if (statementData.empty()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    private static int PreScript(char checkInfixPostfix)
    {
        switch (checkInfixPostfix) {
            case '+':
            case '-':
                return 1;

            case '*':
            case '/':
                return 2;

            case '^':
                return 3;
        }
        return -1;
    }
    private static String infixToPostfix(String eq) throws Exception {
        String result = new String("");
        for (int i = 0; i < eq.length(); ++i) {
            char c = eq.charAt(i);
            if (Character.isLetterOrDigit(c))
                result += c;
            else if (c == '(')
                postfixData.push(c);
            else if (c == ')') {
                while (!postfixData.empty()
                        && (char) postfixData.top() != '(') {
                    result += postfixData.top();
                    postfixData.pop();
                }
                postfixData.pop();
            }
            else
            {
                while (!postfixData.empty()
                        && PreScript(c) <= PreScript((char) postfixData.top())) {

                    result += postfixData.top();
                    postfixData.pop();
                }
                postfixData.push(c);
            }
        }
        while (!postfixData.empty()) {
            if ((char) postfixData.top() == '(')
                return "Invalid Expression";
            result += postfixData.top();
            postfixData.pop();
        }
        return result;
    }
    private static void getInfixToPostfixSolution() throws Exception {
        if (statementData.empty()){
            JOptionPane.showMessageDialog(null , "Please input Statement Equation first.");
            showmenu();
        }
        JOptionPane.showMessageDialog(null , infixToPostfix(statement));
        showmenu();
    }
    private static void clearStatement() throws Exception {
        statementData.clear();
        showmenu();
    }

    private static void clearIteration() throws Exception {
        iterationData.clear();
        showmenu();
    }

    private static void checkSummary() throws Exception {
        String summary;
        if (statementSolution == iterationSolution){
            summary = "Equal";
        } else {
            summary = "Unequal";
        }
        String prompt = String.format(
                "%s\n %s\n %30s\n %s",
                "Statement Solution : " + statement + " = " + statementSolution,
                "Iteration Solution : " + abc + " = " + iterationSolution,
                Line_Dash,
                "Summary of Two Method is : "
        );
        JOptionPane.showMessageDialog(null , prompt + summary);
        showmenu();
    }
}