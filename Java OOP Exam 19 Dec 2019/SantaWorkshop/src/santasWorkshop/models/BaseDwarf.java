package santasWorkshop.models;

import java.util.ArrayList;
import java.util.Collection;

import static santasWorkshop.common.ExceptionMessages.*;

public abstract class BaseDwarf implements Dwarf {

    private String name;
    private int energy;
    private Collection<Instrument> instruments;

    public BaseDwarf(String name, int energy) {
        this.setName(name);
        this.setEnergy(energy);
        this.instruments = new ArrayList<>();
    }

    @Override
    public void work() {
        this.setEnergy(Math.max(this.energy - 10, 0));
    }

    @Override
    public void addInstrument(Instrument instrument) {
        instruments.add(instrument);
    }

    @Override
    public boolean canWork() {
        return this.getEnergy() > 0;
    }

    @Override
    public String getName() {
        return this.name;
    }

    private void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullPointerException(DWARF_NAME_NULL_OR_EMPTY);
        }

        this.name = name;
    }

    @Override
    public int getEnergy() {
        return this.energy;
    }

    void setEnergy(int energy) {
        if (energy < 0) {
            throw new IllegalArgumentException(DWARF_ENERGY_LESS_THAN_ZERO);
        }

        this.energy = energy;
    }

    @Override
    public Collection<Instrument> getInstruments() {
        return this.instruments;
    }
}
