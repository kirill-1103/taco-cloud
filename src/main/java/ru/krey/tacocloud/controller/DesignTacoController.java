package ru.krey.tacocloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.krey.tacocloud.model.Ingredient;
import ru.krey.tacocloud.model.Ingredient.Type;
import ru.krey.tacocloud.model.Order;
import ru.krey.tacocloud.model.Taco;
import ru.krey.tacocloud.repo.IngredientRepository;
import ru.krey.tacocloud.repo.TacoRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private final IngredientRepository ingredientRepository;
    private final TacoRepository tacoRepository;
    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepository, TacoRepository repository){
        this.ingredientRepository =ingredientRepository;
        this.tacoRepository = repository;
    }

//    @ModelAttribute
//    public void addIngredientsToModel(Model model) {
////        List<Ingredient> ingredients = Arrays.asList(
////                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
////                new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
////                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
////                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
////                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
////                new Ingredient("LETC", "Lettuce", Type.VEGGIES),
////                new Ingredient("CHED", "Cheddar", Type.CHEESE),
////                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
////                new Ingredient("SLSA", "Salsa", Type.SAUCE),
////                new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
////        );
//
//        List<Ingredient> ingredients = new ArrayList<>();
//        ingredientRepository.findAll().forEach(ingredients::add);
//
//        Type[] types = Ingredient.Type.values();
//        for (Type type : types) {
//            model.addAttribute(type.toString().toLowerCase(),
//                    filterByType(ingredients, type));
//        }
//    }

//    @ModelAttribute(name="order")
//    public Order order(){return new Order();}

    @ModelAttribute(name="design")
    public Taco design(){return new Taco();}

    @ModelAttribute(name="order")
    public Order order(){return new Order();}


    @GetMapping
    public String showDesignForm(Model model){
        populateIngredients(model);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return "design";
    }

    @PostMapping
    public String processDesign(@Valid  @ModelAttribute("design") Taco design, Errors errors, @ModelAttribute Order order,Model model){
        if(errors.hasErrors()){
            log.info(errors.toString());
            populateIngredients(model);
            return "design";
        }
        Taco saved = tacoRepository.save(design);
        order.addDesign(saved);
        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients,Type type){
        return ingredients.stream().filter(x->x.getType().equals(type)).collect(Collectors.toList());
    }

    private void populateIngredients(Model model){
        List<Ingredient> ingredients = new ArrayList<>(ingredientRepository.findAll());
        log.info(ingredients.toString());
        Type[] types = Ingredient.Type.values();
        for(var type:types){
            model.addAttribute(type.toString().toLowerCase(),filterByType(ingredients,type));
        }
    }
}
