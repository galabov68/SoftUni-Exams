package santasWorkshop.repositories;

import santasWorkshop.models.Dwarf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class DwarfRepository implements Repository<Dwarf> {

    private Collection<Dwarf> dwarfs;

    public DwarfRepository() {
        dwarfs = new ArrayList<>();
    }

    @Override
    public Collection<Dwarf> getModels() {
        return Collections.unmodifiableCollection(dwarfs);
    }

    @Override
    public void add(Dwarf model) {
        dwarfs.add(model);
    }

    @Override
    public boolean remove(Dwarf model) {
        return dwarfs.remove(model);
    }

    @Override
    public Dwarf findByName(String name) {
        return dwarfs.stream()
                .filter(dwarf -> dwarf.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
