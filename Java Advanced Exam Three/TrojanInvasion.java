package com.company;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int waves = Integer.parseInt(scanner.nextLine());
        int[] platesInput = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        ArrayDeque<Integer> platesQueue = new ArrayDeque<>();
        ArrayDeque<Integer> warriorsStack = new ArrayDeque<>();

        for (int plate : platesInput) {
            platesQueue.offer(plate);
        }

        for (int i = 1; i <= waves; i++) {

            if (!platesQueue.isEmpty()) {
                int[] warriorsInput = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();

                if (i % 3 == 0) {
                    platesQueue.offer(Integer.parseInt(scanner.nextLine()));
                }

                for (int warrior : warriorsInput) {
                    warriorsStack.push(warrior);
                }
            }

            while (!(platesQueue.isEmpty() || warriorsStack.isEmpty())) {
                int warrior = warriorsStack.pop();
                int plate = platesQueue.poll();

                if (warrior > plate) {
                    warriorsStack.push(warrior - plate);
                } else if (warrior < plate) {
                    platesQueue.offerFirst(plate - warrior);
                }
            }
        }
        if (platesQueue.isEmpty() && warriorsStack.isEmpty()) {
            System.out.println("The Spartans successfully repulsed the Trojan attack.");
        } else if (!platesQueue.isEmpty()) {
            System.out.println("The Spartans successfully repulsed the Trojan attack.");
            System.out.print("Plates left: ");
            int size = platesQueue.size();
            for (int i = 0; i < size; i++) {
                if (i == size - 1) {
                    System.out.print(platesQueue.poll());
                } else {
                    System.out.print(platesQueue.poll() + ", ");
                }
            }
            System.out.println();
        } else {
            System.out.println("The Trojans successfully destroyed the Spartan defense.");
            System.out.print("Warriors left: ");
            int size = warriorsStack.size();
            for (int i = 0; i < size; i++) {
                if (i == size - 1) {
                    System.out.print(warriorsStack.pop());
                } else {
                    System.out.print(warriorsStack.pop() + ", ");
                }
            }
        }
    }
}