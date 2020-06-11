package aquarium.core;

import aquarium.models.aquariums.Aquarium;
import aquarium.models.aquariums.BaseAquarium;
import aquarium.models.aquariums.FreshwaterAquarium;
import aquarium.models.aquariums.SaltwaterAquarium;
import aquarium.models.decorations.Decoration;
import aquarium.models.decorations.Ornament;
import aquarium.models.decorations.Plant;
import aquarium.models.fish.Fish;
import aquarium.models.fish.FreshwaterFish;
import aquarium.models.fish.SaltwaterFish;
import aquarium.repositories.DecorationRepository;

import static aquarium.common.ExceptionMessages.*;
import static aquarium.common.ConstantMessages.*;

import java.util.ArrayList;
import java.util.Collection;

public class ControllerImpl implements Controller {

    private DecorationRepository decorations;
    private Collection<Aquarium> aquariums;

    public ControllerImpl() {
        decorations = new DecorationRepository();
        aquariums = new ArrayList<>();
    }

    @Override

    public String addAquarium(String aquariumType, String aquariumName) {
        Aquarium aquarium;
        if (aquariumType.equals("FreshwaterAquarium")) {
            aquarium = new FreshwaterAquarium(aquariumName);
        } else if (aquariumType.equals("SaltwaterAquarium")) {
            aquarium = new SaltwaterAquarium(aquariumName);
        } else {
            throw new IllegalArgumentException(INVALID_AQUARIUM_TYPE);
        }

        aquariums.add(aquarium);
        return String.format(SUCCESSFULLY_ADDED_AQUARIUM_TYPE, aquariumType);
    }

    @Override
    public String addDecoration(String type) {
        Decoration decoration;
        if (type.equals("Ornament")) {
            decoration = new Ornament();
        } else if (type.equals("Plant")) {
            decoration = new Plant();
        } else {
            throw new IllegalArgumentException(INVALID_DECORATION_TYPE);
        }

        decorations.add(decoration);
        return String.format(SUCCESSFULLY_ADDED_DECORATION_TYPE, type);
    }

    @Override
    public String insertDecoration(String aquariumName, String decorationType) {
        Aquarium aquarium = getAquarium(aquariumName);
        Decoration decoration = decorations.findByType(decorationType);

        if (decoration == null) {
            throw new NullPointerException(String.format(NO_DECORATION_FOUND, decorationType));
        }

        aquarium.addDecoration(decoration);
        decorations.remove(decoration);
        return String.format(SUCCESSFULLY_ADDED_DECORATION_IN_AQUARIUM, decorationType, aquariumName);
    }

    private Aquarium getAquarium(String aquariumName) {
        return aquariums.stream()
                    .filter(aquariumToLookFor -> aquariumToLookFor.getName().equals(aquariumName))
                    .findFirst()
                    .orElse(null);
    }

    @Override
    public String addFish(String aquariumName, String fishType, String fishName, String fishSpecies, double price) {
        BaseAquarium aquarium = (BaseAquarium) getAquarium(aquariumName);

        Fish fish;

        if (fishType.equals("FreshwaterFish")) {
            fish = new FreshwaterFish(fishName, fishSpecies, price);
        } else if (fishType.equals("SaltwaterFish")) {
            fish = new SaltwaterFish(fishName, fishSpecies, price);
        } else {
            throw new IllegalArgumentException(INVALID_FISH_TYPE);
        }

        if (aquarium.getCapacity() == aquarium.getFish().size()) {
            return "Not enough capacity.";
        }

        if (!aquarium.getClass().getSimpleName().replace("Aquarium", "").equals(fishType.replace("Fish", ""))) {
            return "Water not suitable.";
        }

        aquarium.addFish(fish);
        return String.format(SUCCESSFULLY_ADDED_FISH_IN_AQUARIUM, fishType, aquariumName);
    }

    @Override
    public String feedFish(String aquariumName) {
        BaseAquarium aquarium = (BaseAquarium) getAquarium(aquariumName);
        aquarium.feed();
        return String.format(FISH_FED, aquarium.getFish().size());
    }

    @Override
    public String calculateValue(String aquariumName) {
        Aquarium aquarium = getAquarium(aquariumName);
        double decorationsPriceSum = aquarium.getDecorations().stream().mapToDouble(Decoration::getPrice).sum();
        double fishPriceSum = aquarium.getFish().stream().mapToDouble(Fish::getPrice).sum();
        double totalValue = decorationsPriceSum + fishPriceSum;
        return String.format(VALUE_AQUARIUM, aquariumName, totalValue);
    }

    @Override
    public String report() {
        StringBuilder sb = new StringBuilder();
        aquariums.forEach(aquarium -> sb.append(aquarium.getInfo()));
        return String.valueOf(sb);
    }
}
