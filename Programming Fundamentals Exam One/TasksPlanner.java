import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] tasks = scanner.nextLine().split(" ");
        int[] tasksInHours = new int[tasks.length];

        for (int i = 0; i < tasksInHours.length; i++) {
            tasksInHours[i] = Integer.parseInt(tasks[i]);
        }

        String inputLine = scanner.nextLine();

        while (!inputLine.equals("End")) {
            boolean flag = true;

            switch (inputLine) {
                case "Count Completed":
                    int countCompleted = 0;

                    for (int i = 0; i < tasksInHours.length; i++) {

                        if (tasksInHours[i] == 0) {
                            countCompleted++;
                        }
                    }
                    flag = false;
                    System.out.println(countCompleted);
                    break;
                case "Count Incomplete":
                    int countInCompleted = 0;

                    for (int i = 0; i < tasksInHours.length; i++) {

                        if (tasksInHours[i] > 0) {
                            countInCompleted++;
                        }
                    }
                    flag = false;
                    System.out.println(countInCompleted);
                    break;
                case "Count Dropped":
                    int countDropped = 0;

                    for (int i = 0; i < tasksInHours.length; i++) {

                        if (tasksInHours[i] < 0) {
                            countDropped++;
                        }
                    }
                    flag = false;
                    System.out.println(countDropped);
                    break;
            }

            if (flag) {
                String[] input = inputLine.split(" ");

                switch (input[0]) {
                    case "Complete":

                        if (!(Integer.parseInt(input[1]) < 0 || Integer.parseInt(input[1]) > tasksInHours.length - 1)) {
                            tasksInHours[Integer.parseInt(input[1])] = 0;
                        }
                        break;
                    case "Change":

                        if (!(Integer.parseInt(input[1]) < 0 || Integer.parseInt(input[1]) > tasksInHours.length - 1)) {
                            tasksInHours[Integer.parseInt(input[1])] = Integer.parseInt(input[2]);
                        }
                        break;
                    case "Drop":
                        if (!(Integer.parseInt(input[1]) < 0 || Integer.parseInt(input[1]) > tasksInHours.length - 1)) {
                            tasksInHours[Integer.parseInt(input[1])] = -1;
                        }

                        break;
                }
            }
            inputLine = scanner.nextLine();
        }

        for (int i = 0; i < tasksInHours.length; i++) {

            if (tasksInHours[i] > 0) {
                System.out.print(tasksInHours[i] + " ");
            }
        }
    }
}