package zoo.entities.animals;

public class AquaticAnimal extends BaseAnimal{
    private static final double KG =2.50;
    public AquaticAnimal(String name, String kind, double price) {
        super(name, kind, price);
        setKg(KG);
    }

    @Override
    public void eat() {
        double newSize = getKg() + 7.5;
        setKg(newSize);
    }
}
