package recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final Validator validator;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, Validator validator) {
        this.recipeRepository = recipeRepository;
        this.validator = validator;
    }

    /**
     * Saves a new recipe to the database after validation.
     * @param recipe - recipe that needs to be saved
     * @return - returns bad request status if input is not valid, or ok status if saving was successful
     */
    public ResponseEntity<Map<String, Integer>> addNewRecipe(String name, Recipe recipe) {
        if (!validator.validateRecipeInput(recipe)) {
            return ResponseEntity.badRequest().build();
        }
        recipe.setAuthor(name);
        recipe.setDate(LocalDateTime.now());
        recipeRepository.save(recipe);
        return ResponseEntity.ok(Collections.singletonMap("id", recipe.getId()));
    }

    /**
     * Finds a recipe in database by a given id.
     * @param id - id of the recipe that needs to be found
     * @return - returns bad request status if id parameter is not a number, not found status if a recipe with the given
     * id is not found, or ok status with the found recipe
     */
    public ResponseEntity findRecipe(String id) {
        Integer recipeId = validator.validateInteger(id);
        if (recipeId == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Recipe> recipe = recipeRepository.findById(recipeId);

        if (recipe.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(recipe);

    }

    /**
     * Deletes a recipe from database by a given id.
     * @param id - id of the recipe that needs to be deleted
     * @return - returns bad request status if id parameter is not a number, not found status if a recipe with the given
     * id is not found, or no content status if deletion was successful
     */
    public ResponseEntity deleteRecipe(String name, String id) {
        Integer recipeId = validator.validateInteger(id);
        if (recipeId == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Recipe> recipe = recipeRepository.findById(recipeId);

        if (recipe.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (!name.equals(recipe.get().getAuthor())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        recipeRepository.delete(recipe.get());
        return ResponseEntity.noContent().build();
    }

    /**
     * Updates a recipe by a specific id to the given recipe.
     * @param id - unique identifier of the recipe required to update
     * @param recipe - given new recipe
     * @return - returns bad request status if id parameter is not a number or recipe data is not valid,
     * not found status if a recipe with the given id is not found, or no content status if deletion was successful
     */
    public ResponseEntity updateRecipe(String name, String id, Recipe recipe) {
        Integer recipeId = validator.validateInteger(id);

        if (!validator.validateRecipeInput(recipe) || recipeId == null) {
            return ResponseEntity.badRequest().body(recipe.toString());
        }

        if (recipeRepository.findById(recipeId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (!name.equals(recipeRepository.getById(recipeId).getAuthor())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        recipe.setId(recipeId);
        recipe.setAuthor(name);
        recipe.setDate(LocalDateTime.now());
        recipeRepository.save(recipe);
        return ResponseEntity.noContent().build();
    }

    /**
     * Searches for a list of recipes by a category or a particular recipe by its name
     * @param category - a recipe category to search for
     * @param name - a recipe name to search for
     * @return - return bad request status if search parameters are not valid or ok status with specific body otherwise
     */
    public ResponseEntity searchRecipe(String category, String name) {
        if (!validator.validateSearchParameters(category, name)) {
            return ResponseEntity.badRequest().build();
        }

        if (category != null && !category.equals("")) {
            List<Recipe> recipes = recipeRepository.getAllByCategory(category);
            recipes.sort(Comparator.comparing(Recipe::getDate));
            Collections.reverse(recipes);
            return ResponseEntity.ok(recipes);
        }

        if (name != null && !name.equals("")) {
            List<Recipe> recipes = recipeRepository.getAllByName(name);
            recipes.sort(Comparator.comparing(Recipe::getDate));
            Collections.reverse(recipes);
            return ResponseEntity.ok(recipes);
        }

        return ResponseEntity.ok(Collections.emptyList());
    }
}
