package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double sizeOfSide = Double.parseDouble(scanner.nextLine());
        int numberSheets = Integer.parseInt(scanner.nextLine());
        double singleSheetCover = Double.parseDouble(scanner.nextLine());
        double singleSheet25Percents = singleSheetCover * 0.25;
        double boxArea = sizeOfSide * sizeOfSide * 6;
        double totalAreaCovered = 0.0;

        for (int i = 1; i <= numberSheets; i++) {

            if (i % 3 == 0) {
                totalAreaCovered += singleSheet25Percents;
            } else {
                totalAreaCovered += singleSheetCover;
            }
        }

        System.out.printf("You can cover %.2f%% of the box.", (totalAreaCovered / boxArea) * 100.0);
    }
}