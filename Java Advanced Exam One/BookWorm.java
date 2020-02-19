package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder initialString = new StringBuilder(scanner.nextLine());
        int rowsAndCols = Integer.parseInt(scanner.nextLine());
        String[][] matrix = new String[rowsAndCols][rowsAndCols];
        int playerRow = 0;
        int playerCol = 0;

        for (int row = 0; row < rowsAndCols; row++) {
            String[] values = scanner.nextLine().split("");
            for (int col = 0; col < rowsAndCols; col++) {
                matrix[row][col] = values[col];
                if (matrix[row][col].equals("P")) {
                    playerRow = row;
                    playerCol = col;
                    matrix[row][col] = "-";
                }
            }
        }

        String command = scanner.nextLine();
        while (!command.equals("end")) {
            switch (command) {
                case "up":
                    playerRow--;
                    if (playerRow < 0) {
                        playerRow++;
                        checkForStringLength(initialString);
                    }
                    checkForLetter(matrix, playerRow, playerCol, initialString);
                    break;
                case "down":
                    playerRow++;
                    if (playerRow >= matrix.length) {
                        playerRow--;
                        checkForStringLength(initialString);
                    }
                    checkForLetter(matrix, playerRow, playerCol, initialString);
                    break;
                case "left":
                    playerCol--;
                    if (playerCol < 0) {
                        playerCol++;
                        checkForStringLength(initialString);
                    }
                    checkForLetter(matrix, playerRow, playerCol, initialString);
                    break;
                case "right":
                    playerCol++;
                    if (playerCol >= matrix[0].length) {
                        playerCol--;
                        checkForStringLength(initialString);
                    }
                    checkForLetter(matrix, playerRow, playerCol, initialString);
                    break;
            }
            command = scanner.nextLine();
        }
        matrix[playerRow][playerCol] = "P";
        System.out.println(initialString);
        for (String[] strings : matrix) {
            for (String string : strings) {
                System.out.print(string);
            }
            System.out.println();
        }
    }
    private static void checkForLetter(String[][] matrix, int playerRow, int playerCol, StringBuilder initialString) {
        if (!matrix[playerRow][playerCol].equals("-")) {
            initialString.append(matrix[playerRow][playerCol]);
            matrix[playerRow][playerCol] = "-";
        }
    }
    private static void checkForStringLength(StringBuilder initialString) {
        if (initialString.length() > 0) {
            initialString.deleteCharAt(initialString.length() - 1);
        }
    }
}