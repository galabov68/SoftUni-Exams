package santasWorkshop.models;

public class Sleepy extends BaseDwarf {

    private static final int INITIAL_ENERGY = 50;

    public Sleepy(String name) {
        super(name, INITIAL_ENERGY);
    }

    @Override
    public void work() {
        super.setEnergy(Math.max(super.getEnergy() - 15, 0));
    }
}
