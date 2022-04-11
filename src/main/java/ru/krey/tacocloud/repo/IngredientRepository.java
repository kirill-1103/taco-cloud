package ru.krey.tacocloud.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.krey.tacocloud.model.Ingredient;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient,String> {
    Ingredient findIngredientById(String id);
    List<Ingredient> findAll();
}
