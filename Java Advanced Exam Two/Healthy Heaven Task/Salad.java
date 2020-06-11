package healthyHeaven;

import java.util.ArrayList;
import java.util.List;

public class Salad {
    private String name;
    private List<Vegetable> products;

    public Salad(String name) {
        this.name = name;
        this.products = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getTotalCalories() {
        int caloriesSum = 0;
        for (Vegetable product : products) {
            caloriesSum += product.getCalories();
        }
        return caloriesSum;
    }

    public int getProductCount() {
        return products.size();
    }

    public void add(Vegetable vegetable) {
        products.add(vegetable);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Vegetable product : products) {
            sb.append(String.format("%s%n", product));
        }
        return String.format("Salad %s is %d calories and have %d products:%n%s", name, getTotalCalories(), getProductCount(), sb);
    }
}
