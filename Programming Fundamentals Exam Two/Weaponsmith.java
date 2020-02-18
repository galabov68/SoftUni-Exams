package com.company;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] input = scanner.nextLine().split("\\|");
        String command = scanner.nextLine();

        while (!command.equals("Done")) {
            String[] separatedCommand = command.split(" ");
            String commandType = separatedCommand[1];

            switch (commandType) {
                case "Right":
                    int indexToRight = Integer.parseInt(separatedCommand[2]);
                    if (indexToRight >= 0 && indexToRight < input.length) {
                        if (indexToRight + 1 < input.length) {
                            String toMove = input[indexToRight];
                            input[indexToRight] = input[indexToRight + 1];
                            input[indexToRight + 1] = toMove;
                        }
                    }
                    break;
                case "Left":
                    int indexToLeft = Integer.parseInt(separatedCommand[2]);
                    if (indexToLeft >= 0 && indexToLeft < input.length) {
                        if (indexToLeft - 1 >= 0) {
                            String toMove = input[indexToLeft];
                            input[indexToLeft] = input[indexToLeft - 1];
                            input[indexToLeft - 1] = toMove;
                        }
                    }
                    break;
                case "Even":
                    for (int i = 0; i < input.length; i++) {
                        if (i % 2 == 0) {
                            System.out.print(input[i] + " ");
                        }
                    }
                    System.out.println();
                    break;
                case "Odd":
                    for (int i = 0; i < input.length; i++) {
                        if (i % 2 != 0) {
                            System.out.print(input[i] + " ");
                        }
                    }
                    System.out.println();
                    break;
            }
            command = scanner.nextLine();
        }
        StringBuilder weapon = new StringBuilder();
        for (String s : input) {
            weapon.append(s);
        }
        System.out.printf("You crafted %s!", weapon);
    }
}