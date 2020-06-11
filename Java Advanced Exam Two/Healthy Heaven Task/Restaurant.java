package healthyHeaven;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String name;
    private List<Salad> data;

    public Restaurant(String name) {
        this.name = name;
        this.data = new ArrayList<>();
    }

    public void add(Salad salad) {
        data.add(salad);
    }

    public boolean buy(String name) {
        for (Salad salad : data) {
            if (salad.getName().equals(name)) {
                data.remove(salad);
                return true;
            }
        }
        return false;
    }

    public String getHealthiestSalad() {
        int healthiestSaladCalories = Integer.MAX_VALUE;
        String healthiestSalad = null;
        for (Salad salad : data) {
            int saladCalories = salad.getTotalCalories();
            if (saladCalories < healthiestSaladCalories) {
                healthiestSaladCalories = saladCalories;
                healthiestSalad = salad.getName();
            }
        }
        return healthiestSalad;
    }

    public String generateMenu() {
        StringBuilder sb = new StringBuilder();
        for (Salad salad : data) {
            sb.append(String.format("%s%n", salad));
        }
        return String.format("%s have %d salads:%n%s", name, data.size(), sb);
    }
}
