package com.insight.backend.model;

/**
 * Represents a category in the system.
 */
public class Category {
    private int id;
    private String name;

    /**
     * Constructs a new Category with the specified ID and name.
     *
     * @param id   the ID of the category
     * @param name the name of the category
     */
    public Category(int id, String name) {
        setCategoryId(id);
        setCategoryName(name);
    }

    /**
     * Sets the ID of the category.
     *
     * @param id the ID to set
     */
    public void setCategoryId(int id) {
        this.id = id;
    }

    /**
     * Returns the ID of the category.
     *
     * @return the ID of the category
     */
    public int getCategoryId() {
        return this.id;
    }

    /**
     * Sets the name of the category.
     *
     * @param name the name to set
     */
    public void setCategoryName(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the category.
     *
     * @return the name of the category
     */
    public String getCategoryName() {
        return this.name;
    }
}