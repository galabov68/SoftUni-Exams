import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] input = scanner.nextLine().split(" ");
        List<String> frogs = new ArrayList<>(Arrays.asList(input));
        String[] separatedCommand = scanner.nextLine().split(" ");
        while (!separatedCommand[0].equals("Print")) {
            String command = separatedCommand[0];
            switch (command) {
                case "Join":
                    String joinFrog = separatedCommand[1];
                    frogs.add(joinFrog);
                    break;
                case "Jump":
                    String jumpFrog = separatedCommand[1];
                    int indexToAdd = Integer.parseInt(separatedCommand[2]);
                    if (indexChecker(indexToAdd, frogs)) {
                        frogs.add(indexToAdd, jumpFrog);
                    }
                    break;
                case "Dive":
                    int indexToRemove = Integer.parseInt(separatedCommand[1]);
                    if (indexChecker(indexToRemove, frogs)) {
                        frogs.remove(indexToRemove);
                    }
                    break;
                case "First":
                    int firstCount = Integer.parseInt(separatedCommand[1]);
                    if (firstCount >= frogs.size()) {
                        printList(0, frogs.size(), frogs);
                    } else {
                        printList(0, firstCount, frogs);
                    }
                    break;
                case "Last":
                    int lastCount = Integer.parseInt(separatedCommand[1]);
                    if (lastCount >= frogs.size()) {
                        printList(0, frogs.size(), frogs);
                    } else {
                        int startingIndex = frogs.size() - lastCount;
                        printList(startingIndex, frogs.size(), frogs);
                    }
                    break;
            }

            separatedCommand = scanner.nextLine().split(" ");
        }
        if (separatedCommand[1].equals("Normal")) {
            System.out.print("Frogs: ");
            printList(0, frogs.size(), frogs);
        } else if (separatedCommand[1].equals("Reversed")) {
            Collections.reverse(frogs);
            System.out.print("Frogs: ");
            printList(0, frogs.size(), frogs);
        }
    }

    private static boolean indexChecker(int index, List<String> frogList) {
        return index >= 0 && index < frogList.size();
    }

    private static void printList(int startIndex, int endIndex, List<String> list) {
        for (int i = startIndex; i < endIndex; i++) {
            System.out.print(list.get(i) + " ");
        }
        System.out.println();
    }
}