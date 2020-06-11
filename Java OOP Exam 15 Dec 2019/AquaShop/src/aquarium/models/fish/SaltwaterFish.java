package aquarium.models.fish;

public class SaltwaterFish extends BaseFish {

    private static final int INITIAL_SIZE = 5;

    public SaltwaterFish(String name, String species, double price) {
        super(name, species, INITIAL_SIZE, price);
    }

    @Override
    public void eat() {
        super.setSize(super.getSize() + 2);
    }
}
