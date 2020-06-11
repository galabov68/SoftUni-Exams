package aquarium.models.fish;

import static aquarium.common.ExceptionMessages.*;

public abstract class BaseFish implements Fish {

    private String name;
    private String species;
    private int size;
    private double price;

    BaseFish(String name, String species, int size, double price) {
        this.setName(name);
        this.setSpecies(species);
        this.setSize(size);
        this.setPrice(price);
    }

    @Override
    public void eat() {
        this.setSize(this.getSize() + 5);
    }

    @Override
    public int getSize() {
        return this.size;
    }

    void setSize(int size) {
        this.size = size;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    private void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException(FISH_PRICE_BELOW_OR_EQUAL_ZERO);
        }
        this.price = price;
    }

    public String getSpecies() {
        return this.species;
    }

    private void setSpecies(String species) {
        if (species == null || species.trim().isEmpty()) {
            throw new NullPointerException(SPECIES_NAME_NULL_OR_EMPTY);
        }
        this.species = species;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullPointerException(FISH_NAME_NULL_OR_EMPTY);
        }

        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
