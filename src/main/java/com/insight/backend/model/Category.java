package com.insight.backend.model;

public class Category {
    private int id;
    private String name;

    public Category(int id, String name) {
        setCategoryId(id);
        setCategoryName(name);
    }

    public void setCategoryId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return this.id;
    }

    public void setCategoryName(String name) {
        this.name = name;
    }

    public String getCategoryName() {
        return this.name;
    }
}