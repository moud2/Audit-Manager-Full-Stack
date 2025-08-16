package com.insight.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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
public class NewQuestionDTO {

    /**
     * The supplied name of the question during creation.
     * Must not be blank and should not exceed 255 characters.
     */
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 4096, message = "Name should be up to 4096 characters")
    private String name;

    // TODO: maybe categoryId?
    //@NotBlank(message = "Name cannot be blank")
    @NotNull(message = "Category ID cannot be null")
    private Long categoryId;

    /**
     * Gets the name of the question.
     * 
     * @return the name of the question
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the question.
     * 
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the ID of the category to which the question belongs.
     * 
     * @return the category ID
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * Sets the ID of the category to which the question belongs.
     * 
     * @param categoryId the category ID to set
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    
}
