package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        Map<String, List<Integer>> peoples = new HashMap<>();
        while (!command.equals("Results")) {
            String[] separatedCommand = command.split(":");
            String commandType = separatedCommand[0];
            switch (commandType) {
                case "Add":
                    String personName = separatedCommand[1];
                    int health = Integer.parseInt(separatedCommand[2]);
                    int energy = Integer.parseInt(separatedCommand[3]);
                    if (peoples.containsKey(personName)) {
                        int newHealth = peoples.get(personName).get(0) + health;
                        peoples.get(personName).set(0, newHealth);
                    } else {
                        peoples.put(personName, new ArrayList<>());
                        peoples.get(personName).add(health);
                        peoples.get(personName).add(energy);
                    }
                    break;
                case "Attack":
                    String attackerName = separatedCommand[1];
                    String defenderName = separatedCommand[2];
                    int damage = Integer.parseInt(separatedCommand[3]);
                    if (peoples.containsKey(attackerName) && peoples.containsKey(defenderName)) {
                        int reducedHealth = peoples.get(defenderName).get(0) - damage;
                        if (reducedHealth <= 0) {
                            peoples.remove(defenderName);
                            System.out.printf("%s was disqualified!%n", defenderName);
                        } else {
                            peoples.get(defenderName).set(0, reducedHealth);
                        }
                        int reducedEnergy = peoples.get(attackerName).get(1) - 1;
                        if (reducedEnergy <= 0) {
                            peoples.remove(attackerName);
                            System.out.printf("%s was disqualified!%n", attackerName);
                        } else {
                            peoples.get(attackerName).set(1, reducedEnergy);
                        }
                    }
                    break;
                case "Delete":
                    String username = separatedCommand[1];
                    if (username.equals("All")) {
                        peoples.clear();
                    } else peoples.remove(username);
                    break;
            }
            command = scanner.nextLine();
        }
        System.out.printf("People count: %d%n", peoples.size());
        peoples.entrySet().stream().sorted((f, s) -> f.getKey().compareTo(s.getKey()))
                .sorted((f, s) -> Integer.compare(s.getValue().get(0), f.getValue().get(0)))
                .forEach(entry -> {
                    System.out.printf("%s - %d - %d%n", entry.getKey(), entry.getValue().get(0), entry.getValue().get(1));
                });
    }
}