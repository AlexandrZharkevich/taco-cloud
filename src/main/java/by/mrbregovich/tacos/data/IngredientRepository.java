package by.mrbregovich.tacos.data;

import by.mrbregovich.tacos.Ingredient;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
    List<Ingredient> findAll();
}
