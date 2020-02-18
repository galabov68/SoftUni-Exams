package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int biscuitsPerWorker = Integer.parseInt(scanner.nextLine());
        int workersCount = Integer.parseInt(scanner.nextLine());
        int biscuitsForMonth = Integer.parseInt(scanner.nextLine());
        int nextMonthProduction = 0;

        for (int i = 1; i <= 30; i++) {

            if (i % 3 == 0) {
                double lowerProduction = biscuitsPerWorker * 0.75;
                nextMonthProduction += lowerProduction * workersCount;
            } else {
                nextMonthProduction += biscuitsPerWorker * workersCount;
            }
        }
        System.out.printf("You have produced %d biscuits for the past month.%n", nextMonthProduction);

        if (nextMonthProduction > biscuitsForMonth) {
            int difference = nextMonthProduction - biscuitsForMonth;
            double percentage = (double) difference / biscuitsForMonth * 100;
            System.out.printf("You produce %.2f percent more biscuits.%n", percentage);
        } else {
            int difference = biscuitsForMonth - nextMonthProduction;
            double percentage = (double) difference / biscuitsForMonth * 100;
            System.out.printf("You produce %.2f percent less biscuits.", percentage);
        }
    }
}