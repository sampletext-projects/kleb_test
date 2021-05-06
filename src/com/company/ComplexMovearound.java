package com.company;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class ComplexMovearound {
    public static void main(String[] args) {

        // TODO: Эти значения можно ввести с клавиатуры, но я не тестил

        // !!!Индексы в ключах считаются от 1, а не от 0!!! (точно как в примере)

        String key1 = "231";
        String key2 = "2-1,3-3,4-2";
        int width = 3;
        int height = 4;
        String src = "Hello world!";

        String encodedString = encode(src, key1, key2, width, height);

        System.out.println(encodedString);

        String decodedString = decode(encodedString, key1, key2, width, height);

        System.out.println(decodedString);
    }

    // шифровка строки
    private static String encode(String src, String key1, String key2, int width, int height) {

        // пишем кодирующие матрицы
        char[][][] matrices = writeEncodeMatrices(src, key2, width, height);

        for (char[][] matrix : matrices) {
            printMatrix(matrix);
        }

        return collectEncodedMatrices(matrices, key1, height);
    }

    // сборка шифрованных матриц в строку
    private static String collectEncodedMatrices(char[][][] matrices, String key1, int height) {
        StringBuilder dst = new StringBuilder();

        // собираем символы из матрицы, используя ключ 1, как индексатор столбцов, при этом игнорируем пустые символы и *
        for (char[][] matrix : matrices) {
            for (int keyColumn = 0; keyColumn < key1.length(); keyColumn++) {
                int columnIndex = Integer.parseInt(Character.toString(key1.charAt(keyColumn))) - 1;
                for (int row = 0; row < height; row++) {
                    char value = matrix[row][columnIndex];
                    if (value != '\0' && value != '*')
                        dst.append(value);
                }
            }
        }
        return dst.toString();
    }

    // дешифровка строки
    private static String decode(String src, String key1, String key2, int width, int height) {
        char[][][] matrices = writeDecodeMatrices(src, key1, key2, width, height);

        return collectDecodedMatrices(matrices, key1, width, height);
    }

    // сборка дешифрованных матриц в строку
    private static String collectDecodedMatrices(char[][][] matrices, String key1, int width, int height) {
        StringBuilder dst = new StringBuilder();
        for (char[][] matrix : matrices) {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    char c = matrix[i][j];
                    if (c != '\0' && c != '*') {
                        dst.append(c);
                    }
                }
            }
        }
        return dst.toString();
    }

    // создание дешифрованных матриц
    private static char[][][] writeDecodeMatrices(String src, String key1, String key2, int width, int height) {
        List<Pair<Integer, Integer>> pairs = parseKey2(key2);

        List<char[][]> matricesList = new ArrayList<>();

        int srcOffset = 0;

        while (srcOffset < src.length()) {
            char[][] chars = new char[height][width];

            // Определяем, сколько строк нужно записать в текущей матрице
            int writeRows = height;
            int left = src.length() - srcOffset;
            // Если писать осталось меньше, чем размер матрицы, то уменьшаем количество строк до нужного количества
            if (left < width * height) {
                writeRows = left / width;
                if (left % width != 0) {
                    writeRows++;
                }
            }

            // проходим по колонкам в ключе
            for (int keyColumn = 0; keyColumn < key1.length(); keyColumn++) {
                // парсим номер колонки из ключа
                int col = Integer.parseInt(Character.toString(key1.charAt(keyColumn))) - 1;

                // пишем строку
                for (int row = 0; row < height; row++) {
                    int finalRow = row;
                    // если это ячейка второго ключа - пишем *
                    if (pairs.stream().anyMatch(pair -> pair.getKey() == finalRow && pair.getValue() == col)) {
                        chars[row][col] = '*';
                    } else {
                        // если не второй ключ

                        if (srcOffset == src.length()) {
                            // если ещё не достигли конца строки
                            chars[row][col] = '\0';
                        } else {
                            // если в эту строку что-то нужно писать
                            if (row < writeRows) {
                                chars[row][col] = src.charAt(srcOffset);
                                srcOffset++;
                            }
                        }
                    }
                }
            }
            matricesList.add(chars);
        }

        return toArray(matricesList, width, height);
    }

    // Сборка списка матриц в массив матриц
    private static char[][][] toArray(List<char[][]> list, int width, int height) {
        char[][][] matrices = new char[list.size()][height][width];
        for (int i = 0; i < list.size(); i++) {
            matrices[i] = list.get(i);
        }
        return matrices;
    }

    // создание матриц шифровки
    private static char[][][] writeEncodeMatrices(String src, String key2, int width, int height) {

        List<Pair<Integer, Integer>> pairs = parseKey2(key2);

        List<char[][]> matricesList = new ArrayList<>();

        int srcOffset = 0;

        // тупо бежим по всем символам и пишем их в матрицу
        // если встречаем ключ2, пишем * и движемся дальше
        // если строка кончилась - пишем \0

        while (srcOffset < src.length()) {
            char[][] chars = new char[height][width];
            for (int indexInMatrix = 0; indexInMatrix < width * height; indexInMatrix++) {
                int row = indexInMatrix / width;
                int col = indexInMatrix % width;

                if (pairs.stream().anyMatch(pair -> pair.getKey() == row && pair.getValue() == col)) {
                    chars[row][col] = '*';
                } else {
                    if (srcOffset == src.length()) {
                        chars[row][col] = '\0';
                    } else {
                        chars[row][col] = src.charAt(srcOffset);
                        srcOffset++;
                    }
                }
            }
            matricesList.add(chars);
        }

        return toArray(matricesList, width, height);
    }

    // парсинг ключа2 в список пар<число-число> с конвертацией индексов от 1
    private static List<Pair<Integer, Integer>> parseKey2(String key2) {
        List<Pair<Integer, Integer>> list = new ArrayList<>();

        String[] parts = key2.split(",");

        for (String part : parts) {
            String[] indexesStrings = part.split("-");
            int row = Integer.parseInt(indexesStrings[0]) - 1;
            int column = Integer.parseInt(indexesStrings[1]) - 1;

            list.add(new Pair<>(row, column));
        }
        return list;
    }

    // метод вывода матрицы, можно использовать для тестов
    public static void printMatrix(char[][] matrix) {

        for (int i = 0; i < matrix[0].length * 4 + 1; i++) {
            System.out.print('-');
        }
        System.out.println();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print("| " + (matrix[i][j] == '\0' ? " " : matrix[i][j]) + " ");
            }
            System.out.println("|");

            for (int j = 0; j < matrix[0].length * 4 + 1; j++) {
                System.out.print('-');
            }
            System.out.println();
        }
        System.out.println();
    }
}
