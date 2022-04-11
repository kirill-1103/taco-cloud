package ru.krey.tacocloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.krey.tacocloud.model.Ingredient;
import ru.krey.tacocloud.model.Ingredient.Type;
import ru.krey.tacocloud.model.User;
import ru.krey.tacocloud.repo.IngredientRepository;
import ru.krey.tacocloud.repo.UserRepository;

@Configuration
public class DataConfig {

    private final PasswordEncoder passwordEncoder;

    public DataConfig(PasswordEncoder encoder){
        this.passwordEncoder = encoder;
    }

    @Bean
    public CommandLineRunner dataLoader(IngredientRepository ingredientRepo, UserRepository userRepo) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                addIngredients(ingredientRepo);
                addUsers(userRepo);
            }
        };
    }

    private void addIngredients(IngredientRepository ingredientRepo){
        ingredientRepo.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
        ingredientRepo.save(new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
        ingredientRepo.save(new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
        ingredientRepo.save(new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN));
        ingredientRepo.save(new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES));
        ingredientRepo.save(new Ingredient("LETC", "Lettuce", Type.VEGGIES));
        ingredientRepo.save(new Ingredient("CHED", "Cheddar", Type.CHEESE));
        ingredientRepo.save(new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
        ingredientRepo.save(new Ingredient("SLSA", "Salsa", Type.SAUCE));
        ingredientRepo.save(new Ingredient("SRCR", "Sour Cream", Type.SAUCE));
    }

    private void addUsers(UserRepository userRepo){
        userRepo.save(new User(
           "kirill", passwordEncoder.encode("123"),
           "Kirill Kreysmann","Malaya Buharestskaya 10k1",
           "Saint-Petesburg","Saint-Petesburg",
                "941","89141395288"
        ));
    }

}
