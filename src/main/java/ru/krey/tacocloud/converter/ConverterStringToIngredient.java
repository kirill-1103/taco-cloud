package ru.krey.tacocloud.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.krey.tacocloud.model.Ingredient;
import ru.krey.tacocloud.repo.IngredientRepository;

@Component
public class ConverterStringToIngredient implements Converter<String, Ingredient> {
    final IngredientRepository ingredientRepo;

    @Autowired
    public ConverterStringToIngredient(IngredientRepository repo){
        this.ingredientRepo=repo;
    }


    @Override
    public Ingredient convert(String id) {
        return ingredientRepo.findById(id).get();
    }
}
