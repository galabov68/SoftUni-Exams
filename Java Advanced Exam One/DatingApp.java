package com.company;

import java.util.ArrayDeque;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] malesSequence = scanner.nextLine().split(" ");
        String[] femalesSequence = scanner.nextLine().split(" ");
        ArrayDeque<Integer> maleStack = new ArrayDeque<>();
        ArrayDeque<Integer> femaleQueue = new ArrayDeque<>();
        int matchCount = 0;

        for (String male : malesSequence) {
            maleStack.push(Integer.parseInt(male));
        }

        for (String female : femalesSequence) {
            femaleQueue.offer(Integer.parseInt(female));
        }

        while (!(maleStack.isEmpty() || femaleQueue.isEmpty())) {
            int female = femaleQueue.poll();

            if (female <= 0) {
                continue;
            } else if (female % 25 == 0) {
                femaleQueue.poll();
                continue;
            }

            int male = maleStack.peek();

            if (male <= 0) {
                femaleQueue.offerFirst(female);
                maleStack.pop();
                continue;
            } else if (male % 25 == 0) {
                maleStack.pop();
                maleStack.pop();
            }

            if (male == female) {
                maleStack.pop();
                matchCount++;
            } else {
                maleStack.push(maleStack.pop() - 2);
                
                if (maleStack.peek() <= 0) {
                    maleStack.pop();
                }
            }
        }
        System.out.printf("Matches: %d%n", matchCount);
        System.out.print("Males left: ");

        if (maleStack.isEmpty()) {
            System.out.print("none");
        } else {
            while (!maleStack.isEmpty()) {

                if (maleStack.size() == 1) {
                    System.out.print(maleStack.pop());
                } else {
                    System.out.print(maleStack.poll() + ", ");
                }
            }
        }

        System.out.println();
        System.out.print("Females left: ");

        if (femaleQueue.isEmpty()) {
            System.out.print("none");
        } else {
            while (!femaleQueue.isEmpty()) {

                if (femaleQueue.size() == 1) {
                    System.out.print(femaleQueue.poll());
                } else {
                    System.out.print(femaleQueue.poll() + ", ");
                }
            }
        }
    }
}