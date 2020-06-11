package rabbits;

import java.util.ArrayList;
import java.util.List;

public class Cage {
    private String name;
    private int capacity;
    private List<Rabbit> data;

    public Cage(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        this.data = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void add(Rabbit rabbit) {
        if (data.size() < capacity) {
            data.add(rabbit);
        }
    }

    public boolean removeRabbit(String name) {
        for (Rabbit rabbit : data) {
            if (rabbit.getName().equals(name)) {
                data.remove(rabbit);
                return true;
            }
        }
        return false;
    }

    public void removeSpecies(String species) {
        for (Rabbit rabbit : data) {
            if (rabbit.getSpecies().equals(species)) {
                data.remove(rabbit);
            }
        }
    }

    public Rabbit sellRabbit(String name) {
        for (Rabbit rabbit : data) {
            if (rabbit.getName().equals(name)) {
                rabbit.setAvailable(false);
                return rabbit;
            }
        }
        return null;
    }

    public List<Rabbit> sellRabbitBySpecies(String species) {
        List<Rabbit> soldRabbits = new ArrayList<>();
        for (Rabbit rabbit : data) {
            if (rabbit.getSpecies().equals(species)) {
                rabbit.setAvailable(false);
                soldRabbits.add(rabbit);
            }
        }
        return soldRabbits;
    }

    public int count() {
        return data.size();
    }

    public String report() {
        List<Rabbit> availableRabbits = new ArrayList<>();
        for (Rabbit rabbit : data) {
            if (rabbit.isAvailable()) {
                availableRabbits.add(rabbit);
            }
        }
        StringBuilder sb = new StringBuilder(String.format("Rabbits available at %s:%n", name));
        for (Rabbit availableRabbit : availableRabbits) {
            sb.append(availableRabbit);
            sb.append(String.format("%n"));
        }
        return sb.toString();
    }
}
