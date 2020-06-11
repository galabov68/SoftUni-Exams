package aquarium.models.fish;

public class FreshwaterFish extends BaseFish {

    private static final int INITIAL_SIZE = 3;

    public FreshwaterFish(String name, String species, double price) {
        super(name, species, INITIAL_SIZE, price);
    }

    @Override
    public void eat() {
        super.setSize(super.getSize() + 3);
    }
}
