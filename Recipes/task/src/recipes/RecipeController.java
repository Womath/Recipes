package recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * Handles user requests.
 */
@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    /**
     * Adds a new recipe to the recipe list.
     * @param recipe - the recipe to be added
     * @return - returns an appropriate HTTP status code and message
     */
    @PostMapping("/new")
    public ResponseEntity addRecipe(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Recipe recipe) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return recipeService.addNewRecipe(userDetails.getUsername(), recipe);
    }

    /**
     * Retrieves a recipe from the recipe list.
     * @param id - id of the recipe that needs to be found
     * @return - returns an appropriate HTTP status code and message
     */
    @GetMapping("/{id}")
    public ResponseEntity getRecipe(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("id") String id) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return recipeService.findRecipe(id);
    }

    /**
     * Deletes a recipe from the recipe list.
     * @param id - id of the recipe that needs to be deleted
     * @return - returns an appropriate HTTP status code and message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteRecipe(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("id") String id) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return recipeService.deleteRecipe(userDetails.getUsername(), id);
    }

    /**
     * Updates a recipe from recipe list.
     * @param id - id of the recipe that needs to be updated
     * @param recipe - a recipe that will overwrite the previous one
     * @return - returns an appropriate HTTP status code and message
     */
    @PutMapping("/{id}")
    public ResponseEntity updateRecipe(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("id") String id, @RequestBody Recipe recipe) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return recipeService.updateRecipe(userDetails.getUsername(), id, recipe);
    }

    /**
     * Searches for a list of recipes by their name or category
     * @param category - recipe category to search for
     * @param name - recipe name to search for
     * @return - returns an appropriate HTTP status code and message
     */
    @GetMapping("/search")
    public ResponseEntity searchRecipe(@AuthenticationPrincipal UserDetails userDetails, @RequestParam(required = false) String category, @RequestParam(required = false) String name) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return recipeService.searchRecipe(category, name);
    }

}
