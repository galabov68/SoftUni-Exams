package spaceStation.models.astronauts;

public class Biologist extends BaseAstronaut {

    private static final double INITIAL_OXYGEN = 70;
    private static final double OXYGEN_TO_BREATH = 5;

    public Biologist(String name) {
        super(name, INITIAL_OXYGEN);
        super.setNeededOxygen(OXYGEN_TO_BREATH);
    }
}
