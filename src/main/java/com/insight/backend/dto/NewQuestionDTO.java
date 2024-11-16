package com.insight.backend.dto;

import java.util.List;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.insight.backend.model.Category;
import com.insight.backend.model.Rating;


/**
 * Data Transfer Object equivalent to JSON object of GET /api/v1/questions/new.
 * model specification
 *  @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "question")
    private Set<Rating> rating;
    @ManyToOne
    @JoinColumn(name="category_id", nullable=false)
    private Category category;
    private LocalDateTime DeletedAt;
 */
public class NewAuditDTO {

    /**
     * The supplied name of the question during creation.
     * Must not be blank and should not exceed 255 characters.
     */
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 255, message = "Name should be up to 255 characters")
    private String name;

    /*
     * The Set of Rating asociated with a Question.
     *
     */
    private Set<Rating> rating;

    /*
     * The Category Objects associated with the audit.
     * Must not be null.
     */
    @NotNull(message = "Categories cannot be null")
    private Categories categories;

    /**
     * Gets the name of the audit.
     *  
     * @return the name of the audit
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the audit.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of category IDs associated with the audit.
     *
     * @return the list of category IDs
     */
    public List<Long> getCategories() {
        return categories;
    }

    /**
     * Sets the list of category IDs associated with the audit.
     *
     * @param categories the list of category IDs to set
     */
    public void setCategories(List<Long> categories) {
        this.categories = categories;
    }
}
