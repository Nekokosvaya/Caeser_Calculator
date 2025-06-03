package src;

import java.util.Scanner;

// import src.src.CaesarCipher;
// import src.src.ArithmeticEvaluator;
public class GehtsoftAssessment {
    private static final Scanner scanner = new Scanner(System.in); // Scanner для чтения ввода пользователя
    private static final CaesarCipher caesarCipher = new CaesarCipher(); // Экземпляр класса для шифрования и расшифровки
    private static final ArithmeticEvaluator evaluator = new ArithmeticEvaluator(); // Экземпляр класса для вычисления арифметических выражений

// Главный метод программы
    public static void main(String[] args) {
        System.out.println("Welcome to Gehtsoft Technical Assessment");

        boolean continueProgram = true; // Продолжаем или выходим из цикла

        while (continueProgram) { // Запускаю цикл, который будет работать, пока пользователь не захочет выйти
            displayMenu(); // Показываем варианты для пользователя
            int choice = getUserChoice(); // Возвращаем выбор пользователя (1-4)

            switch (choice) { // Обрабатываем выбор пользователя
                case 1:
                    handleCaesarEncryption();
                    break;
                case 2:
                    handleCaesarDecryption();
                    break;
                case 3:
                    handleArithmeticEvaluation();
                    break;
                case 4:
                    System.out.println("Thank you for using the program!");
                    continueProgram = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please select an option from 1 to 4."); // Если пользователь ввел что-то не то, выводим сообщение
            }

            if (continueProgram && choice >= 1 && choice <= 3) { // Если пользователь выбрал 1, 2 или 3, спрашиваем, хочет ли он продолжить
                continueProgram = askToContinue();
            }
        }

        scanner.close(); // Закрываем сканер
    }

// Показывает меню с вариантами для пользователя
    private static void displayMenu() {
        System.out.println("\nPlease choose an option:");
        System.out.println("1. Caesar Cipher Encryption");
        System.out.println("2. Caesar Cipher Decryption");
        System.out.println("3. Arithmetic Expression Evaluation");
        System.out.println("4. Exit");
        System.out.print("\nEnter your choice: ");
    }
    // Получает выбор пользователя и возвращает его как целое число. Eсли пользователь ввел что-то не то, возвращает -1
    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1; // Invalid choice
        }
    }
// Обрабатывает шифрование с помощью шифра Цезаря
    private static void handleCaesarEncryption() {
        try {
            System.out.print("Enter text to encrypt: ");
            String text = scanner.nextLine();

            System.out.print("Enter shift value: ");
            int shift = Integer.parseInt(scanner.nextLine().trim());

            String result = caesarCipher.encrypt(text, shift);
            System.out.println("Result: \"" + result + "\"");

        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid shift value. Please enter an integer.");
        } catch (Exception e) {
            System.out.println("Error during encryption: " + e.getMessage());
        }
    }
// Обрабатывает расшифровку
// C помощью шифра Цезаря. Если пользователь не ввел значение сдвига, выполняет расшифровка без знания сдвига
// и выводит все возможные расшифровки
private static void handleCaesarDecryption() {
    try {
        // Запрашиваем у пользователя текст для расшифровки и значение сдвига
        System.out.print("Enter text to decrypt: ");
        String text = scanner.nextLine();

        // Проверяем, пустой ли текст
        System.out.print("Enter shift value: ");
        String shiftInput = scanner.nextLine().trim();

        // Если пользователь не ввел значение сдвига, выполняем частотный анализ
        if (shiftInput.isEmpty()) {
            String result = caesarCipher.dictionaryBasedBruteForce(text);
            System.out.println("\nBest decryption (by frequency analysis):");
            System.out.println(result);


        // Если пользователь ввел значение сдвига, выполняем расшифровку с этим сдвигом
        } else {
            int shift = Integer.parseInt(shiftInput);
            String result = caesarCipher.decrypt(text, shift);
            System.out.println("Result: \"" + result + "\"");
        }

        // Обработка возможных исключений
    } catch (NumberFormatException e) {
        System.out.println("Error: Invalid shift value. Please enter an integer.");
    } catch (Exception e) {
        System.out.println("Error during decryption: " + e.getMessage());
    }
}

    // Обрабатывает арифметическое выражение, введенное пользователем, и выводит результат
    private static void handleArithmeticEvaluation() {
        try {
            System.out.print("Enter arithmetic expression: ");
            String expression = scanner.nextLine();

            double result = evaluator.evaluate(expression);

            System.out.println("Input: \"" + expression + "\"");
            if (result == (int) result) {
                System.out.println("Output: " + (int) result);
            } else {
                System.out.println("Output: " + result);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
// Спрашивает пользователя, хочет ли он продолжить работу с программой
    private static boolean askToContinue() {
        System.out.print("\nContinue? (y/n): ");
        String response = scanner.nextLine().trim().toLowerCase();
        return response.equals("y") || response.equals("yes");
    }
}