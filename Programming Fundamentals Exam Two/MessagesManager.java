package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int maxMessages = Integer.parseInt(scanner.nextLine());
        String command = scanner.nextLine();
        Map<String, List<Integer>> messages = new HashMap<>();
        while (!command.equals("Statistics")) {
            String[] separatedCommand = command.split("=");
            String commandType = separatedCommand[0];
            switch (commandType) {
                case "Add":
                    String usernameToAdd = separatedCommand[1];
                    int sentMessages = Integer.parseInt(separatedCommand[2]);
                    int receivedMessages = Integer.parseInt(separatedCommand[3]);
                    if (!messages.containsKey(usernameToAdd)) {
                        messages.put(usernameToAdd, new ArrayList<>());
                        messages.get(usernameToAdd).add(sentMessages);
                        messages.get(usernameToAdd).add(receivedMessages);
                    }
                    break;
                case "Message":
                    String sender = separatedCommand[1];
                    String receiver = separatedCommand[2];
                    if (messages.containsKey(sender) && messages.containsKey(receiver)) {
                        messagesUpdater(messages, sender, maxMessages);
                        messagesUpdater(messages, receiver, maxMessages);
                    }
                    break;
                case "Empty":
                    String username = separatedCommand[1];
                    if (username.equals("All")) {
                        messages.clear();
                    } else messages.remove(username);
                    break;
            }
            command = scanner.nextLine();
        }
        System.out.printf("Users count: %d%n", messages.size());
        messages.entrySet().stream().sorted((f, s) -> f.getKey().compareTo(s.getKey()))
                .sorted((f, s) -> Integer.compare(s.getValue().get(1), f.getValue().get(1)))
                .forEach(entry -> {
                    int sum = entry.getValue().stream().mapToInt(Integer::intValue).sum();
                    System.out.printf("%s - %d%n", entry.getKey(), sum);
                });
    }

    private static void messagesUpdater(Map<String, List<Integer>> messages, String person, int maxMessages) {
        int personCapacity = messages.get(person).get(0) + messages.get(person).get(1) + 1;
        if (personCapacity >= maxMessages) {
            System.out.printf("%s reached the capacity!%n", person);
            messages.remove(person);
        } else {
            messages.get(person).set(0, messages.get(person).get(0) + 1);
        }
    }
}