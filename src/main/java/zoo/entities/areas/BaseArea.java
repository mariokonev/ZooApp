package zoo.entities.areas;

import zoo.entities.animals.Animal;
import zoo.entities.foods.Food;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static zoo.common.ExceptionMessages.*;
import static zoo.common.ConstantMessages.*;

public abstract class BaseArea implements Area{

    private String name;
    private int capacity;
    private List<Food> foods;
    private List<Animal> animals;

    protected BaseArea(String name, int capacity){
        setName(name);
        this.capacity = capacity;
        this.foods = new ArrayList<>();
        this.animals = new ArrayList<>();

    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullPointerException(AREA_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
    }


    @Override
    public int sumCalories() {
        return foods.stream().mapToInt(Food::getCalories).sum();
    }

    @Override
    public String getName() {
        return name;
    }



    @Override
    public void addAnimal(Animal animal) {
        if (capacity == this.animals.size()) {
            throw new IllegalStateException(NOT_ENOUGH_CAPACITY);
        }

        String AnimalType = animal.getClass().getSimpleName().replace("Animal","");

        if (this.getClass().getSimpleName().contains(AnimalType)) {
            throw new IllegalStateException(AREA_NOT_SUITABLE);
        }

        this.animals.add(animal);
    }


    @Override
    public void removeAnimal(Animal animal) {
        this.animals.remove(animal);

    }

    @Override
    public void addFood(Food food) {
        this.foods.add(food);

    }

    @Override
    public void feed() {
        for (Animal a : animals) {
            a.eat();
        }

    }

    @Override
    public String getInfo() {
        String animalOutput = animals.isEmpty()
                ? "none"
                : animals.stream().map(Animal::getName).collect(Collectors.joining(" "));

        return String.format("%s (%s):%n" +
                        "Animal: %s%n" +
                        "Foods: %d%n" +
                        "Calories: %d",
                name, this.getClass().getSimpleName(), animalOutput, foods.size(), sumCalories());

    }

    @Override
    public Collection<Animal> getAnimal() {
        return animals;
    }

    @Override
    public Collection<Food> getFood() {
        return foods;
    }
}
