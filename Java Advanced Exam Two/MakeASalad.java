package com.company;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] vegetables = scanner.nextLine().split(" ");
        int[] calories = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        ArrayDeque<String> queue = new ArrayDeque<>();
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        ArrayDeque<Integer> readySalads = new ArrayDeque<>();

        for (String vegetable : vegetables) {
            queue.offer(vegetable);
        }

        for (int calory : calories) {
            stack.push(calory);
        }

        while (!(queue.isEmpty() || stack.isEmpty())) {
            int salad = stack.peek();
            boolean flag = true;

            while (flag) {
                if (queue.isEmpty()) {
                    readySalads.offer(salad);
                    stack.pop();
                    flag = false;
                    continue;
                }
                String vegetable = queue.poll();
                int vegetableCalories = vegChecker(vegetable);
                int saladCalories = stack.pop();
                int leftCalories = saladCalories - vegetableCalories;

                if (leftCalories > 0) {
                    stack.push(leftCalories);
                } else {
                    readySalads.offer(salad);
                    flag = false;
                }
            }
        }

        if (!readySalads.isEmpty()) {
            for (Integer readySalad : readySalads) {
                System.out.print(readySalad + " ");
            }
            System.out.println();
        }

        if (!stack.isEmpty()) {
            for (Integer integer : stack) {
                System.out.print(integer + " ");
            }
            System.out.println();
        }

        if (!queue.isEmpty()) {
            for (String s : queue) {
                System.out.print(s + " ");
            }
            System.out.println();
        }
    }
    private static int vegChecker(String vegetable) {
        int calories = 0;

        switch (vegetable) {
            case "tomato":
                calories = 80;
                break;
            case "carrot":
                calories = 136;
                break;
            case "lettuce":
                calories = 109;
                break;
            case "potato":
                calories = 215;
                break;
        }
        return calories;
    }
}