package com.company;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] pirateShip = Arrays.stream(scanner.nextLine().split(">")).mapToInt(Integer::parseInt).toArray();
        int[] warShip = Arrays.stream(scanner.nextLine().split(">")).mapToInt(Integer::parseInt).toArray();
        int maxHealth = Integer.parseInt(scanner.nextLine());
        String command = scanner.nextLine();

        while (!command.equals("Retire")) {
            String[] separatedCommand = command.split(" ");
            String commandType = separatedCommand[0];
            switch (commandType) {
                case "Fire":
                    int indexToFire = Integer.parseInt(separatedCommand[1]);
                    int damageToFire = Integer.parseInt(separatedCommand[2]);
                    if (indexInRange(indexToFire, warShip)) {
                        warShip[indexToFire] -= damageToFire;
                        if (warShip[indexToFire] <= 0) {
                            System.out.println("You won! The enemy ship has sunken.");
                            return;
                        }
                    }
                    break;
                case "Defend":
                    int startIndexToDefend = Integer.parseInt(separatedCommand[1]);
                    int endIndexToDefend = Integer.parseInt(separatedCommand[2]);
                    int damageToDefend = Integer.parseInt(separatedCommand[3]);
                    if (indexInRange(startIndexToDefend, pirateShip) && indexInRange(endIndexToDefend, pirateShip)) {
                        for (int i = startIndexToDefend; i <= endIndexToDefend; i++) {
                            pirateShip[i] -= damageToDefend;
                            if (pirateShip[i] <= 0) {
                                System.out.println("You lost! The pirate ship has sunken.");
                                return;
                            }
                        }
                    }
                    break;
                case "Repair":
                    int indexToRepair = Integer.parseInt(separatedCommand[1]);
                    int healthToRepair = Integer.parseInt(separatedCommand[2]);
                    if (indexInRange(indexToRepair, pirateShip)) {
                        pirateShip[indexToRepair] += healthToRepair;
                        if (pirateShip[indexToRepair] > maxHealth) {
                            pirateShip[indexToRepair] = maxHealth;
                        }
                    }
                    break;
                case "Status":
                    int repairCount = 0;
                    for (int i : pirateShip) {
                        double needRepair = maxHealth * 0.2;
                        if (i < needRepair) {
                            repairCount++;
                        }
                    }
                    System.out.printf("%d sections need repair.%n", repairCount);
                    break;
            }
            command = scanner.nextLine();
        }
        int pirateShipSum = IntStream.of(pirateShip).sum();
        int warShipSum = IntStream.of(warShip).sum();
        System.out.printf("Pirate ship status: %d%n", pirateShipSum);
        System.out.printf("Warship status: %d%n", warShipSum);
    }

    private static boolean indexInRange(int index, int[] arr) {
        return index >= 0 && index < arr.length;
    }
}