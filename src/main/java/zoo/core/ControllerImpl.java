package zoo.core;

import zoo.entities.animals.Animal;
import zoo.entities.animals.AquaticAnimal;
import zoo.entities.animals.TerrestrialAnimal;
import zoo.entities.areas.Area;
import zoo.entities.areas.LandArea;
import zoo.entities.areas.WaterArea;
import zoo.entities.foods.Food;
import zoo.entities.foods.Meat;
import zoo.entities.foods.Vegetable;
import zoo.repositories.FoodRepositoryImpl;
import zoo.repositories.FoodRepository;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static zoo.common.ConstantMessages.*;
import static zoo.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {
    private FoodRepository foods;
    private Map<String, Area> areas;

    public ControllerImpl() {
        this.foods = new FoodRepositoryImpl();
        this.areas = new LinkedHashMap<>();
    }


    @Override
    public String addArea(String areaType, String areaName) {

        Area area;
        if (areaType.equals("LandArea")) {
            area = new LandArea(areaName);
        } else if (areaType.equals("WaterArea")) {
            area = new WaterArea(areaName);
        } else {
            throw new NullPointerException(INVALID_AREA_TYPE);
        }

        areas.put(area.getName(), area);

        return String.format(SUCCESSFULLY_ADDED_AREA_TYPE, areaType);
    }

    @Override
    public String buyFood(String type) {
        Food food;
        if (type.equals("Meat")) {
            food = new Meat();
        } else if (type.equals("Vegetable")) {
            food = new Vegetable();
        } else {
            throw new IllegalArgumentException(INVALID_FOOD_TYPE);
        }

        foods.add(food);

        return String.format(SUCCESSFULLY_ADDED_FOOD_TYPE, type);
    }

    @Override
    public String foodForArea(String areaName, String foodType) {

        Food food = foods.findByType(foodType);

        if (food == null) {
            throw new IllegalArgumentException(String.format(NO_FOOD_FOUND, foodType));
        }

        foods.remove(food);

        areas.get(areaName).addFood(food);

        return String.format(SUCCESSFULLY_ADDED_FOOD_IN_AREA, areaName,foodType);
    }

    @Override
    public String addAnimal(String areaName, String animalType, String animalName, String kind, double price) {

        Animal animal;
        if (animalType.equals("AquaticAnimal")) {
            animal = new AquaticAnimal(animalName, animalType, price);
        } else if (animalType.equals("TerrestrialAnimal")) {
            animal = new TerrestrialAnimal(animalName, animalType, price);
        } else {
            throw new IllegalArgumentException(INVALID_ANIMAL_TYPE);
        }

        try {
            Area area = areas.get(areaName);
            area.addAnimal(animal);
        } catch (IllegalStateException ex) {
            return ex.getMessage();
        }

        return String.format(SUCCESSFULLY_ADDED_ANIMAL_IN_AREA, animalType, areaName);
    }

    @Override
    public String feedAnimal(String areaName) {

        Area area = areas.get(areaName);
        area.feed();
        return String.format(ANIMAL_FED, area.getAnimal().size());
    }

    @Override
    public String calculateKg(String areaName) {

        Area area = areas.get(areaName);

        double value = area.getAnimal().stream().mapToDouble(Animal::getPrice).sum()
                + area.getFood().stream().mapToDouble(Food::getPrice).sum();

        return String.format(KILOGRAMS_AREA, areaName, value);
    }

    @Override
    public String getStatistics() {
        return areas.values().stream().map(Area::getInfo).collect(Collectors.joining(System.lineSeparator()));
    }


}
