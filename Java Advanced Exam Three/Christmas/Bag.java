package christmas;

import java.util.ArrayList;
import java.util.List;

public class Bag {
    private String color;
    private int capacity;
    private List<Present> data;
    private Present present;

    public Bag(String color, int capacity) {
        this.color = color;
        this.capacity = capacity;
        this.data = new ArrayList<>();
    }

    public String getColor() {
        return color;
    }

    public int getCapacity() {
        return capacity;
    }

    public int count() {
        return data.size();
    }

    public void add(Present present) {
        if (data.size() < capacity) {
            data.add(present);
        }
    }

    public boolean remove(String name) {
        for (Present present : data) {
            if (present.getName().equals(name)) {
                data.remove(present);
                return true;
            }
        }
        return false;
    }

    public Present heaviestPresent() {
        double maxWeight = Integer.MIN_VALUE;
        Present heaviestPresent = null;
        for (Present present : data) {
            double weight = present.getWeight();
            if (weight > maxWeight) {
                weight = maxWeight;
                heaviestPresent = present;
            }
        }
        return heaviestPresent;
    }

    public Present getPresent(String name) {
        for (Present present : data) {
            if (present.getName().equals(name)) {
                return present;
            }
        }
        return null;
    }

    public String report() {
        StringBuilder sb = new StringBuilder();
        for (Present present : data) {
            if (present.getName().equals(data.get(data.size() - 1).getName())) {
                sb.append(String.format("%s", present));
            } else {
                sb.append(String.format("%s%n", present));
            }
        }
        color = Character.toUpperCase(color.charAt(0)) + color.substring(1, color.length());
        return String.format("%s bag contains:%n%s", color, sb);
    }
}
