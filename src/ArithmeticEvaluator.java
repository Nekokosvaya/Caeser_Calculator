package src;

import java.util.*;

public class ArithmeticEvaluator {

    // Метод для вычисления выражения
    public double evaluate(String expression) throws Exception {
        if (expression == null || expression.trim().isEmpty()) {
            throw new Exception("Expression cannot be empty");
        }

        // Убираем пробелы
        expression = expression.replaceAll("\\s+", "");

        // Преобразуем в постфиксную запись (2 + 3 * 4 превращается в 2 3 4 * +)
        List<String> postfix = infixToPostfix(expression);

        // Вычисляем результат
        return evaluatePostfix(postfix);
    }

    // Преобразование из инфиксной в постфиксную запись (алгоритм сортировочной станции)
    private List<String> infixToPostfix(String expression) throws Exception {
        List<String> output = new ArrayList<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c) || c == '.') {
                // Читаем число (может быть десятичным)
                StringBuilder number = new StringBuilder();
                while (i < expression.length() &&
                        (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    number.append(expression.charAt(i));
                    i++;
                }
                i--; // Возвращаемся на один символ назад
                output.add(number.toString());

            } else if (c == '-' && (i == 0 || expression.charAt(i-1) == '(' || isOperator(expression.charAt(i-1)))) {
                // Унарный минус
                StringBuilder number = new StringBuilder("-");
                i++; // Переходим к следующему символу
                if (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    while (i < expression.length() &&
                            (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                        number.append(expression.charAt(i));
                        i++;
                    }
                    i--; // Возвращаемся на один символ назад
                    output.add(number.toString());
                } else {
                    throw new Exception("Invalid format for negative number");
                }

            } else if (isOperator(c)) {
                // Обрабатываем операторы с учетом приоритета
                while (!operators.isEmpty() && operators.peek() != '(' &&
                        getPriority(operators.peek()) >= getPriority(c)) {
                    output.add(String.valueOf(operators.pop()));
                }
                operators.push(c);

            } else if (c == '(') {
                operators.push(c);

            } else if (c == ')') {
                // Выталкиваем операторы до открывающей скобки
                while (!operators.isEmpty() && operators.peek() != '(') {
                    output.add(String.valueOf(operators.pop()));
                }
                if (operators.isEmpty()) {
                    throw new Exception("Unbalanced parentheses");
                }
                operators.pop(); // Убираем '('

            } else {
                throw new Exception("Unknown symbol: " + c);
            }
        }

        // Выталкиваем оставшиеся операторы
        while (!operators.isEmpty()) {
            char op = operators.pop();
            if (op == '(' || op == ')') {
                throw new Exception("Unbalanced parentheses");
            }
            output.add(String.valueOf(op));
        }

        return output;
    }

    // Вычисление постфиксного выражения
    private double evaluatePostfix(List<String> postfix) throws Exception {
        Stack<Double> stack = new Stack<>();

        for (String token : postfix) {
            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
            } else if (isOperator(token.charAt(0))) {
                if (stack.size() < 2) {
                    throw new Exception("Incorrect expression");
                }

                double b = stack.pop();
                double a = stack.pop();
                double result = performOperation(a, b, token.charAt(0));
                stack.push(result);
            }
        }
// Проверяем, что в стеке остался только один элемент (результат)
        if (stack.size() != 1) {
            throw new Exception("Incorrect expression");
        }

        return stack.pop();
    }

    // Выполнение арифметической операции
    private double performOperation(double a, double b, char operator) throws Exception {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new Exception("Division by zero");
                }
                return a / b;
            default:
                throw new Exception("Unknown operation: " + operator);
        }
    }

    // Проверка, является ли символ оператором
    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    // Проверка, является ли строка числом
    private boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Получение приоритета операции
    private int getPriority(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 0;
        }
    }
}