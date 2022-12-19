package recipes;

import org.springframework.stereotype.Component;

/**
 * A class handling input validations.
 */
@Component
public class Validator {

    /**
     * Checks if given value is a number.
     * @param str - a string needs to be checked
     * @return - returns an Integer value if string is a number, or null otherwise
     */
    public Integer validateInteger(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Checks if all fields of the recipe has some kind of value.
     * @param recipe - recipe that needs to be checked
     * @return - return false if any of the fields are null or empty, true otherwise
     */
    public boolean validateRecipeInput(Recipe recipe) {
        if (recipe.getName() == null || recipe.getName().strip().equals("")) {
            return false;
        }

        if (recipe.getCategory() == null || recipe.getCategory().strip().equals("")) {
            return false;
        }

        if (recipe.getDescription() == null || recipe.getDescription().strip().equals("")) {
            return false;
        }

        if (recipe.getIngredients() == null || recipe.getIngredients().length == 0) {
            return false;
        }

        if (recipe.getDirections() == null || recipe.getDirections().length == 0) {
            return false;
        }

        return true;
    }

    /**
     * Validates search parameters by teh rule of only one can be an active parameter
     * @param category - a recipe category
     * @param name - a recipe name
     * @return - returns false if both params have value or both of them are null, true otherwise
     */
    public boolean validateSearchParameters(String category, String name) {
        if (category != null && name != null) {
            return false;
        }

        if (category == null && name == null) {
            return false;
        }

        return true;
    }

    /**
     * Checks if given string is a valid email address or not
     * @param email - given email to check
     * @return - true, if it is a valid email, false otherwise
     */
    public boolean validateEmail(String email) {
        if (email == null || email.strip().equals("")) {
            return false;
        }
        return email.matches(".+[@].+[\\\\.].+");
    }

    /**
     * Checks if given string is a valid password
     * @param password - password to check
     * @return - true if password is at least 8 characters long, false otherwise
     */
    public boolean validatePassword(String password) {
        if (password == null || password.strip().equals("")) {
            return false;
        }
        return password.length() >= 8;
    }
}
