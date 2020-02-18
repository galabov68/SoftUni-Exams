package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] input = scanner.nextLine().split(":");
        List<String> cardsList = new ArrayList<>();
        String[] command = scanner.nextLine().split(" ");

        while (!command[0].equals("Ready")) {
            String cardName = command[1];

            switch (command[0]) {
                case "Add":
                    if (isExist(input, cardsList, cardName)) {
                        cardsList.add(cardName);
                    } else {
                        System.out.println("Card not found.");
                    }
                    break;
                case "Insert":
                    int index = Integer.parseInt(command[2]);
                        if (isExist(input, cardsList, cardName) && index >= 0 && index < cardsList.size()) {
                            cardsList.add(index, cardName);
                        } else {
                            System.out.println("Error!");
                        }
                    break;
                case "Remove":
                    if (cardsList.contains(cardName)) {
                        cardsList.remove(cardName);
                    } else {
                        System.out.println("Card not found.");
                    }
                    break;
                case "Swap":
                    String cardOne = command[1];
                    String cardTwo = command[2];
                    int indexOfCardOne = cardsList.indexOf(cardOne);
                    int indexOfCardTwo = cardsList.indexOf(cardTwo);
                    cardsList.set(indexOfCardOne, cardTwo);
                    cardsList.set(indexOfCardTwo, cardOne);
                    break;
                case "Shuffle":
                    Collections.reverse(cardsList);
                    break;
            }
            command = scanner.nextLine().split(" ");
        }
        for (String card: cardsList) {
            System.out.printf("%s ", card);
        }
    }
    static boolean isExist(String[] input, List<String> cardsList, String cardName) {
        boolean flag = false;
        for (String card: input) {
            if (card.equals(cardName)) {
                flag = true;
                break;
            }
        }
        return flag;
    }
}