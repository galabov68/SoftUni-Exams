package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, List<String>> notes = new LinkedHashMap<>();
        String[] input = scanner.nextLine().split("->");

        while (!input[0].equals("END")) {

            switch (input[0]) {
                case "Add":
                    String store = input[1];

                    if (notes.containsKey(store)) {
                        String[] items = input[2].split(",");
                        for (String item : items) {
                            notes.get(store).add(item);
                        }
                    } else {
                        notes.put(store, new ArrayList<>());
                        String[] items = input[2].split(",");
                        for (String item : items) {
                            notes.get(store).add(item);
                        }
                    }
                    break;
                case "Remove":
                    String toRemove = input[1];
                    notes.remove(toRemove);
                    break;
            }
            input = scanner.nextLine().split("->");
        }
        System.out.println("Stores list:");
        notes.entrySet().stream().sorted((f, s) -> s.getKey().compareTo(f.getKey())).sorted((f, s) -> Integer.compare(s.getValue().size(), f.getValue().size())).forEach(entry -> {
            System.out.println(entry.getKey());
            for (String item: entry.getValue()) {
                System.out.printf("<<%s>>%n", item);
            }
        });
    }
}