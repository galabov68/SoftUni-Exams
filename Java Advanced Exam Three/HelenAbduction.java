package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int energyOfParis = Integer.parseInt(scanner.nextLine());
        int rowsAndCols = Integer.parseInt(scanner.nextLine());
        String[][] matrix = new String[rowsAndCols][];
        int rowOfParis = 0;
        int colOfParis = 0;

        for (int row = 0; row < rowsAndCols; row++) {
            String[] input = scanner.nextLine().split("");
            matrix[row] = new String[input.length];
            for (int col = 0; col < matrix[row].length; col++) {
                matrix[row][col] = input[col];
                if (matrix[row][col].equals("P")) {
                    rowOfParis = row;
                    colOfParis = col;
                }
            }
        }

        boolean foundHelen = true;

        while (energyOfParis > 0 && foundHelen) {
            String[] command = scanner.nextLine().split(" ");
            String direction = command[0];
            int row = Integer.parseInt(command[1]);
            int col = Integer.parseInt(command[2]);
            matrix[row][col] = "S";
            energyOfParis--;

            switch (direction) {
                case "up":
                    if (rowOfParis - 1 >= 0) {
                        if (matrix[rowOfParis - 1][colOfParis].equals("H")) {
                            matrix[rowOfParis - 1][colOfParis] = "-";
                            matrix[rowOfParis][colOfParis] = "-";
                            foundHelen = false;
                            System.out.printf("Paris has successfully abducted Helen! Energy left: %d%n", energyOfParis);
                            continue;
                        }
                        if (matrix[rowOfParis - 1][colOfParis].equals("S")) {
                            if (energyOfParis - 2 >= 0) {
                                energyOfParis -= 2;
                            }
                        }
                        matrix[rowOfParis][colOfParis] = "-";
                        matrix[--rowOfParis][colOfParis] = "P";
                    }
                    break;
                case "down":
                    if (rowOfParis + 1 < matrix.length) {
                        if (matrix[rowOfParis + 1][colOfParis].equals("H")) {
                            matrix[rowOfParis + 1][colOfParis] = "-";
                            matrix[rowOfParis][colOfParis] = "-";
                            foundHelen = false;
                            System.out.printf("Paris has successfully abducted Helen! Energy left: %d%n", energyOfParis);
                            continue;
                        }
                        if (matrix[rowOfParis + 1][colOfParis].equals("S")) {
                            if (energyOfParis - 2 >= 0) {
                                energyOfParis -= 2;
                            }
                        }
                        matrix[rowOfParis][colOfParis] = "-";
                        matrix[++rowOfParis][colOfParis] = "P";
                    }
                    break;
                case "right":
                    if (colOfParis + 1 < matrix[0].length) {
                        if (matrix[rowOfParis][colOfParis + 1].equals("H")) {
                            matrix[rowOfParis][colOfParis + 1] = "-";
                            matrix[rowOfParis][colOfParis] = "-";
                            foundHelen = false;
                            System.out.printf("Paris has successfully abducted Helen! Energy left: %d%n", energyOfParis);
                            continue;
                        }
                        if (matrix[rowOfParis][colOfParis + 1].equals("S")) {
                            if (energyOfParis - 2 >= 0) {
                                energyOfParis -= 2;
                            }
                        }
                        matrix[rowOfParis][colOfParis] = "-";
                        matrix[rowOfParis][++colOfParis] = "P";
                    }
                    break;
                case "left":
                    if (colOfParis - 1 >= 0) {
                        if (matrix[rowOfParis][colOfParis - 1].equals("H")) {
                            matrix[rowOfParis][colOfParis - 1] = "-";
                            matrix[rowOfParis][colOfParis] = "-";
                            foundHelen = false;
                            System.out.printf("Paris has successfully abducted Helen! Energy left: %d%n", energyOfParis);
                            continue;
                        }
                        if (matrix[rowOfParis][colOfParis - 1].equals("S")) {
                            if (energyOfParis - 2 >= 0) {
                                energyOfParis -= 2;
                            }
                        }
                        matrix[rowOfParis][colOfParis] = "-";
                        matrix[rowOfParis][--colOfParis] = "P";
                    }
                    break;
            }
        }
        if (foundHelen) {
            matrix[rowOfParis][colOfParis] = "X";
            System.out.printf("Paris died at %d;%d.%n", rowOfParis, colOfParis);
        }
        for (String[] strings : matrix) {
            for (String string : strings) {
                System.out.print(string);
            }
            System.out.println();
        }
    }
}