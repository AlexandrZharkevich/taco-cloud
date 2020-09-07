package by.mrbregovich.tacos.web;

import by.mrbregovich.tacos.Ingredient;
import by.mrbregovich.tacos.Ingredient.Type;
import by.mrbregovich.tacos.Order;
import by.mrbregovich.tacos.Taco;
import by.mrbregovich.tacos.TacoDto;
import by.mrbregovich.tacos.data.IngredientRepository;
import by.mrbregovich.tacos.data.TacoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;

    private final TacoRepository designRepo;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository designRepo) {
        this.ingredientRepo = ingredientRepo;
        this.designRepo = designRepo;
    }

    @GetMapping
    public String showDesignForm(Model model) {

        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(ingredients::add);

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
        model.addAttribute("design", new TacoDto());
        return "design";
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "design")
    public TacoDto tacoDto() {
        return new TacoDto();
    }

    @PostMapping
    public String processDesign(@Valid TacoDto design, Errors errors, @ModelAttribute Order order) {

        if (errors.hasErrors()) {
            return "design";
        }

//        log.info("Processing design " + design);
        Taco newTaco = new Taco();
        newTaco.setName(design.getName());
        List<Ingredient> ingredients = new ArrayList<>();
        design.getIngredients().forEach(
                i -> ingredients.add(ingredientRepo.findById(i).get())
        );
        newTaco.setIngredients(ingredients);
        Taco saved = designRepo.save(newTaco);
        order.addDesign(saved);

        return "redirect:/orders/current";
    }

    private static List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
