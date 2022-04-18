package zoo.entities.animals;

public class TerrestrialAnimal extends BaseAnimal{

    private static final double KG =5.50;

    public TerrestrialAnimal(String name, String kind, double price) {
        super(name, kind, price);
        setKg(KG);
    }

    @Override
    public void eat() {
        double newSize = getKg() + 5.7;
        setKg(newSize);
    }
}
