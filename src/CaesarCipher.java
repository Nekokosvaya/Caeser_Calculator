package src;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;


public class CaesarCipher {
    private static final char[] ENG_LOWER = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final char[] ENG_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final char[] RUS_LOWER = {'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й',
            'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф',
            'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я'};
    private static final char[] RUS_UPPER = {'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й',
            'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф',
            'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я'};

    // Частотный анализ для русского и английского алфавитов
    private static final Map<Character, Double> RUSSIAN_FREQ = Map.ofEntries(
            Map.entry('о', 0.1097), Map.entry('е', 0.0845), Map.entry('а', 0.0801),
            Map.entry('и', 0.0735), Map.entry('н', 0.067), Map.entry('т', 0.0626),
            Map.entry('с', 0.0547), Map.entry('р', 0.0473), Map.entry('в', 0.0454),
            Map.entry('л', 0.044), Map.entry('к', 0.0349), Map.entry('м', 0.0321),
            Map.entry('д', 0.0298), Map.entry('п', 0.0281), Map.entry('у', 0.0262)

    );

    private static final Map<Character, Double> ENGLISH_FREQ = Map.ofEntries(
            Map.entry('e', 0.127), Map.entry('t', 0.091), Map.entry('a', 0.082),
            Map.entry('o', 0.075), Map.entry('i', 0.070), Map.entry('n', 0.067),
            Map.entry('s', 0.063), Map.entry('h', 0.061), Map.entry('r', 0.060),
            Map.entry('d', 0.043), Map.entry('l', 0.040), Map.entry('c', 0.028)

    );
   // Часто встречающиеся слова в русском языке для проверки, потому что частотный анализ может не сработать на коротких текстах
    private static final Set<String> COMMON_RUS_WORDS = Set.of(
            "и", "в", "не", "на", "я", "быть", "он", "с", "что", "а", "привет", "мир",
            "как", "это", "по", "из", "у", "за", "то", "же", "к", "для"
    );
    private static final Set<String> COMMON_ENG_WORDS = Set.of(
            "the", "and", "you", "that", "was", "for", "are", "with", "this", "have",
            "hello", "world", "he", "she", "we", "it", "to", "a", "in", "is", "of"
    );

    // Метод шифрования
    public String encrypt(String message, int shift) {
        return caesarTransform(message, shift);
    }

    // Метод расшифровки
    public String decrypt(String message, int shift) {
        return caesarTransform(message, -shift);
    }

    // Общий метод для шифрования и расшифровки
    private String caesarTransform(String message, int shift) {
        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }

        StringBuilder result = new StringBuilder();

        for (char ch : message.toCharArray()) {
            result.append(shiftChar(ch, shift));
        }

        return result.toString();
    }

    // Метод для сдвига символа в зависимости от алфавита и сдвига
    private char shiftChar(char ch, int shift) {
        if (isInAlphabet(ch, ENG_LOWER)) {
            return shiftInAlphabet(ch, shift, ENG_LOWER);
        } else if (isInAlphabet(ch, ENG_UPPER)) {
            return shiftInAlphabet(ch, shift, ENG_UPPER);
        } else if (isInAlphabet(ch, RUS_LOWER)) {
            return shiftInAlphabet(ch, shift, RUS_LOWER);
        } else if (isInAlphabet(ch, RUS_UPPER)) {
            return shiftInAlphabet(ch, shift, RUS_UPPER);
        } else {
            return ch; // Не буква — не шифруем
        }
    }

    // Метод для проверки, является ли символ буквой из заданного алфавита
    private boolean isInAlphabet(char ch, char[] alphabet) {
        for (char a : alphabet) {
            if (ch == a) return true;
        }
        return false;
    }

    // Метод для сдвига символа в алфавите с учетом сдвига
    private char shiftInAlphabet(char ch, int shift, char[] alphabet) {
        int len = alphabet.length;
        int index = 0;

        for (int i = 0; i < len; i++) {
            if (alphabet[i] == ch) {
                index = i;
                break;
            }
        }

        // Вычисляем новый индекс с учетом сдвига
        int newIndex = (index + shift) % len;
        if (newIndex < 0) newIndex += len;

        return alphabet[newIndex];
    }

    // Расшифровка без знания сдвига
    public String[] bruteForceDecrypt(String message) {
        Set<String> results = new LinkedHashSet<>(); // Используем LinkedHashSet для сохранения уникальных результатов в порядке их добавления

        int maxShift = Math.max(ENG_LOWER.length, RUS_LOWER.length); // Максимальный сдвиг равен размеру алфавита (для английского и русского)

        for (int shift = 1; shift < maxShift; shift++) { // Пробуем все возможные сдвиги от 1 до максимального размера алфавита
            String decrypted = decrypt(message, shift); // Используем метод decrypt для расшифровки с текущим сдвигом
            results.add("Shift " + shift + ": " + decrypted); // Добавляем результат
        }

        return results.toArray(new String[0]);
    }

    // Определяет лучший сдвиг с помощью частотного анализа!!!
    public String frequencyBruteForce(String message) {
        boolean isRussian = isRussianText(message.toLowerCase());
        char[] alphabet = isRussian ? RUS_LOWER : ENG_LOWER;
        Map<Character, Double> expectedFreq = isRussian ? RUSSIAN_FREQ : ENGLISH_FREQ;

        String bestResult = "";
        double minDifference = Double.MAX_VALUE;

        for (int shift = 1; shift < alphabet.length; shift++) {
            String decrypted = decrypt(message, shift);
            double difference = calculateFrequencyDifference(decrypted.toLowerCase(), expectedFreq, alphabet);

            if (difference < minDifference) {
                minDifference = difference;
                bestResult = "Shift " + shift + ": " + decrypted;
            }
        }

        return bestResult;
    }

    // Проверяет, на каком языке текст (русский или английский)
    private boolean isRussianText(String message) {
        for (char c : message.toCharArray()) {
            if ((c >= 'а' && c <= 'я') || (c >= 'А' && c <= 'Я') || c == 'ё' || c == 'Ё') {
                return true;
            }
        }
        return false;
    }


    // Считает отличие между частотами в тексте и ожидаемыми частотами
    private double calculateFrequencyDifference(String text, Map<Character, Double> expectedFreq, char[] alphabet) {
        Map<Character, Integer> actualCounts = new java.util.HashMap<>();
        int total = 0;

        for (char ch : text.toCharArray()) {
            if (containsChar(alphabet, ch)) {
                actualCounts.put(ch, actualCounts.getOrDefault(ch, 0) + 1);
                total++;
            }
        }

    // Если нет символов в тексте, возвращаем максимальное отличие
        double diff = 0.0;
        for (Map.Entry<Character, Double> entry : expectedFreq.entrySet()) {
            char ch = entry.getKey();
            double expected = entry.getValue();
            double actual = actualCounts.getOrDefault(ch, 0) / (double) total;
            diff += Math.abs(expected - actual);
        }

        return diff;
    }

    // Проверяет, содержится ли символ в массиве
    private boolean containsChar(char[] array, char ch) {
        for (char c : array) {
            if (c == ch) return true;
        }
        return false;
    }

    // Проверка через словарь слов
    public String dictionaryBasedBruteForce(String message) {
        boolean isRussian = isRussianText(message);
        char[] alphabet = isRussian ? RUS_LOWER : ENG_LOWER;
        int maxScore = -1;
        String bestResult = "";

        for (int shift = 1; shift < alphabet.length; shift++) {
            String decrypted = decrypt(message, shift);
            int score = countKnownWords(decrypted.toLowerCase(), isRussian);

            if (score > maxScore) {
                maxScore = score;
                bestResult = "Shift " + shift + ": " + decrypted;
            }
        }

        return bestResult;
    }

    // Подсчёт известных слов
    private int countKnownWords(String text, boolean isRussian) {
        String[] words = text.split("\\s+|\\p{Punct}+");
        int count = 0;

        Set<String> dictionary = isRussian ? COMMON_RUS_WORDS : COMMON_ENG_WORDS;

        for (String word : words) {
            if (dictionary.contains(word)) {
                count++;
            }
        }

        return count;
    }



}