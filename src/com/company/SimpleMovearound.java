package com.company;

import java.util.Scanner;

public class SimpleMovearound {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String source = scanner.nextLine();

        int width = 3;
        int height = 3;

        String encoded = encode(source, width, height);
        System.out.println(encoded);

        String decoded = decode(encoded, width, height);
        System.out.println(decoded);
    }

    public static String encode(String source, int width, int height) {
        int charsPerMatrix = width * height;
        int matricesCount = source.length() / charsPerMatrix + (source.length() % charsPerMatrix > 0 ? 1 : 0);

        char[][][] encodedMatrices = new char[matricesCount][height][width];

        for (int i = 0; i < matricesCount; i++) {
            int length = charsPerMatrix;
            if (i == matricesCount - 1) {
                length = source.length() % charsPerMatrix;
            }
            char[][] matrix = encodeMatrix(source, width, height, i * charsPerMatrix, length);
            encodedMatrices[i] = matrix;
        }

        StringBuilder encoded = new StringBuilder();

        for (int i = 0; i < matricesCount; i++) {
            printMatrix(encodedMatrices[i]);
            encoded.append(collectEncodedMatrix(encodedMatrices[i], height, width));
        }

        return encoded.toString();
    }

    public static String decode(String source, int width, int height) {
        int charsPerMatrix = width * height;
        int matricesCount = source.length() / charsPerMatrix + (source.length() % charsPerMatrix > 0 ? 1 : 0);

        char[][][] decodedMatrices = new char[matricesCount][height][width];
        for (int i = 0; i < matricesCount; i++) {
            int length = charsPerMatrix;
            if (i == matricesCount - 1) {
                length = source.length() % charsPerMatrix;
            }
            char[][] matrix = decodeMatrix(source, width, height, i * charsPerMatrix, length);
            decodedMatrices[i] = matrix;
        }

        StringBuilder decoded = new StringBuilder();

        for (int i = 0; i < matricesCount; i++) {
            decoded.append(collectDecodedMatrix(decodedMatrices[i], height, width));
        }

        return decoded.toString();
    }

    public static String collectEncodedMatrix(char[][] matrix, int height, int width) {
        StringBuilder dst = new StringBuilder();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                char c = matrix[j][i];
                if (c != '\0') {
                    dst.append(c);
                }
            }
        }
        return dst.toString();
    }

    public static String collectDecodedMatrix(char[][] matrix, int height, int width) {
        StringBuilder dst = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                char c = matrix[i][j];
                if (c != '\0') {
                    dst.append(c);
                }
            }
        }
        return dst.toString();
    }

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

    public static char[][] encodeMatrix(String src, int width, int height, int startIndex, int length) {
        char[][] chars = new char[height][width];

        int cell = 0;

        for (int i = startIndex; i < startIndex + length; i++) {
            int row = cell / width;
            int col = cell % width;

            chars[row][col] = src.charAt(i);
            cell++;
        }

        return chars;
    }

    public static char[][] decodeMatrix(String src, int width, int height, int startIndex, int length) {
        char[][] chars = new char[height][width];

        int end = startIndex + length;
        int charIndex = startIndex;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (charIndex < end) {
                    chars[j][i] = src.charAt(charIndex);
                    charIndex++;
                } else {
                    chars[j][i] = '\0';
                }
            }
        }

        return chars;
    }
}
