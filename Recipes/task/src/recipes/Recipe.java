package recipes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@JsonIgnoreProperties(value = {"id", "author"})
@Entity
@Table(name = "recipes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private String author;
    @Column
    private String name;
    @Column
    private String category;
    @Column
    private LocalDateTime date;
    @Column
    private String description;
    @Column
    private String[] ingredients;
    @Column
    private String[] directions;
}
