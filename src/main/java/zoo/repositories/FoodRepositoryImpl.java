package zoo.repositories;

import zoo.entities.foods.Food;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FoodRepositoryImpl implements FoodRepository {

    private List<Food> foods;

    public FoodRepositoryImpl() {
        foods = new ArrayList<>();
    }


    @Override
    public void add(Food food) {
        foods.add(food);

    }

    @Override
    public boolean remove(Food food) {
        return foods.remove(food);
    }

    @Override
    public Food findByType(String type) {

        return foods.stream()
                .filter(d -> d.getClass().getSimpleName().equals(type))
                .findFirst()
                .orElse(null);
    }
}
