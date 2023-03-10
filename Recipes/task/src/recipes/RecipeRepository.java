package recipes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

    @Query("SELECT r FROM Recipe r WHERE lower(r.category) = lower(:category)")
    List<Recipe> getAllByCategory(String category);

    @Query("SELECT r FROM Recipe r WHERE lower(r.name) LIKE '%' || lower(:name) || '%'")
    List<Recipe> getAllByName(String name);

}
