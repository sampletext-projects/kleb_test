package com.company;

public class MultilangOnecapMonophonic {

    static char[] alphabet =
            {'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ь', 'Ы', 'Ъ', 'Э', 'Ю', 'Я', ' '};

    static char[][] encodingMatrix = {
            {'Ф', 'Н', '(', 'Щ', 'И', 'Г', 'Е', 'R', 'A', 'Д', 'Ы', '~', '@', 'S', 'X', 'Я', 'Ж', '^', 'C', 'Ш', 'М', 'Б', 'Q', 'П', 'Т', 'Х', 'Ю', 'Ъ', 'Р', '}', '|', '_', '#'},
            {'*', 'Н', 'У', 'Щ', 'D', '+', 'Е', 'R', '=', 'Д', 'Ц', 'Й', 'Ч', '[', 'В', 'Ь', ')', 'O', '&', '{', 'М', 'Б', 'Q', 'П', 'Т', 'Х', 'Ю', 'Ъ', 'Р', '}', '|', '_', '<'},
            {'Л', 'Н', '(', 'Щ', 'И', ']', 'Е', 'R', '%', 'Д', 'Ы', '~', '@', 'G', '/', 'Я', 'Э', 'З', '"', 'Ш', 'М', 'Б', 'Q', 'П', 'Т', 'Х', 'Ю', 'Ъ', 'Р', '}', '|', '_', 'W'},
            {'Ф', 'Н', 'У', 'Щ', 'D', 'К', 'Е', 'R', 'A', 'Д', 'Ц', 'Й', 'Ч', 'S', '+', 'Ь', 'Ж', '^', 'C', '{', 'М', 'Б', 'Q', 'П', 'Т', 'Х', 'Ю', 'Ъ', 'Р', '}', '|', '_', 'V'}
    };

    public static void main(String[] args) {
        String source = "аааааа";

        String encoded = encode(source.toUpperCase());

        System.out.println(encoded);

        String decoded = decode(encoded);

        System.out.println(decoded);
    }

    public static String encode(String src) {
        int[] usages = new int[33 + 1];

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < src.length(); i++) {
            int alphabetIndex = indexOf(alphabet, src.charAt(i));

            char encodedChar = encodingMatrix[usages[alphabetIndex] % 4][alphabetIndex];
            usages[alphabetIndex]++;
            result.append(encodedChar);
        }

        return result.toString();
    }

    public static int indexOf(char[] array, char c) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == c) {
                return i;
            }
        }
        return -1;
    }

    public static int encodingIndexOf(char c) {
        for (int i = 0; i < 4; i++) {
            int encodingIndex = indexOf(encodingMatrix[i], c);

            if (encodingIndex != -1) {
                return encodingIndex;
            }
        }
        return -1;
    }


    public static String decode(String src) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < src.length(); i++) {
            int encodingIndexOf = encodingIndexOf(src.charAt(i));

            result.append(alphabet[encodingIndexOf]);
        }

        return result.toString();
    }
}
