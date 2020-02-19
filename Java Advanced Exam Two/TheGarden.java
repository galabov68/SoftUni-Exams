package com.company;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rows = Integer.parseInt(scanner.nextLine());
        String[][] matrix = new String[rows][];

        for (int row = 0; row < rows; row++) {
            String[] input = scanner.nextLine().split(" ");
            matrix[row] = new String[input.length];
            for (int col = 0; col < input.length; col++) {
                matrix[row][col] = input[col];
            }
        }

        int lettuceCounter = 0;
        int potatoesCounter = 0;
        int carrotsCounter = 0;
        int harmedVegCounter = 0;
        String[] command = scanner.nextLine().split(" ");
        while (!command[0].equals("End")) {

            int row = Integer.parseInt(command[1]);
            int col = Integer.parseInt(command[2]);

            if (row > matrix.length - 1 || row < 0 || col > matrix[row].length - 1 || col < 0) {
                command = scanner.nextLine().split(" ");
                continue;
            }

            switch (command[0]) {
                case "Harvest":
                    if (matrix[row][col].equals("L")) {
                        matrix[row][col] = " ";
                        lettuceCounter++;
                    } else if (matrix[row][col].equals("P")) {
                        matrix[row][col] = " ";
                        potatoesCounter++;
                    } else if (matrix[row][col].equals("C")) {
                        matrix[row][col] = " ";
                        carrotsCounter++;
                    }
                    break;
                case "Mole":
                    String direction = command[3];

                    switch (direction) {
                        case "up":
                            for (int i = row; i > 0; i -= 2) {
                                if (!matrix[i][col].equals(" ")) {
                                    matrix[i][col] = " ";
                                    harmedVegCounter++;
                                }
                            }
                            break;
                        case "down":
                            for (int i = row; i < matrix.length; i += 2) {
                                if (!matrix[i][col].equals(" ")) {
                                    matrix[i][col] = " ";
                                    harmedVegCounter++;
                                }
                            }
                            break;
                        case "left":
                            for (int i = col; i > 0; i -= 2) {
                                if (!matrix[row][i].equals(" ")) {
                                    matrix[row][i] = " ";
                                    harmedVegCounter++;
                                }
                            }
                            break;
                        case "right":
                            for (int i = col; i < matrix[row].length; i += 2) {
                                if (!matrix[row][i].equals(" ")) {
                                    matrix[row][i] = " ";
                                    harmedVegCounter++;
                                }
                            }
                            break;
                    }
                    break;
            }
            command = scanner.nextLine().split(" ");
        }
        for (String[] strings : matrix) {
            for (String string : strings) {
                System.out.print(string + " ");
            }
            System.out.println();
        }

        System.out.printf("Carrots: %d%n", carrotsCounter);
        System.out.printf("Potatoes: %d%n", potatoesCounter);
        System.out.printf("Lettuce: %d%n", lettuceCounter);
        System.out.printf("Harmed vegetables: %d%n", harmedVegCounter);
    }
}