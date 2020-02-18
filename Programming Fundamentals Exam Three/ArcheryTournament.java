package com.company;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] targets = Arrays.stream(scanner.nextLine().split("\\|")).mapToInt(Integer::parseInt).toArray();
        String command = scanner.nextLine();
        int archerPoints = 0;

        while (!command.equals("Game over")) {
            String[] separatedCommand = command.split(" ");

            switch (separatedCommand[0]) {
                case "Shoot":
                    String[] directionAndIndexes = separatedCommand[1].split("@");
                    int startIndex = Integer.parseInt(directionAndIndexes[1]);
                    int length = Integer.parseInt(directionAndIndexes[2]);

                    if (startIndex < 0 || startIndex > targets.length - 1) {
                        command = scanner.nextLine();
                        continue;
                    }

                    switch (directionAndIndexes[0]) {
                        case "Left":
                            int indexLeft = startIndex;
                            for (int i = length; i > 0; i--) {
                                if (indexLeft == 0) {
                                    indexLeft = targets.length - 1;
                                } else {
                                    indexLeft--;
                                }
                            }
                            if (targets[indexLeft] < 5 && targets[indexLeft] > 0) {
                                archerPoints += targets[indexLeft];
                                targets[indexLeft] = 0;
                            } else if (targets[indexLeft] >= 5) {
                                archerPoints += 5;
                                targets[indexLeft] -= 5;
                            }
                            break;
                        case "Right":
                            int indexRight = startIndex;
                            for (int i = length; i > 0; i--) {
                                if (indexRight == targets.length - 1) {
                                    indexRight = 0;
                                } else {
                                    indexRight++;
                                }
                            }

                            if (targets[indexRight] < 5 && targets[indexRight] > 0) {
                                archerPoints += targets[indexRight];
                                targets[indexRight] = 0;
                            } else if (targets[indexRight] >= 5) {
                                archerPoints += 5;
                                targets[indexRight] -= 5;
                            }
                            break;
                    }
                    break;
                case "Reverse":
                    for (int i = 0; i < targets.length / 2; i++) {
                        int temp = targets[i];
                        targets[i] = targets[targets.length - i - 1];
                        targets[targets.length - i - 1] = temp;
                    }
                    break;
            }
            command = scanner.nextLine();
        }
        for (int i = 0; i < targets.length; i++) {
            if (i == targets.length - 1) {
                System.out.print(targets[i]);
            } else {
                System.out.print(targets[i] + " - ");
            }
        }
        System.out.println();
        System.out.printf("Iskren finished the archery tournament with %d points!", archerPoints);
    }
}