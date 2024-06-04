package com.insight.backend.model;

public class Audit {
    private final int id;
    private final String name;

    public Audit (int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}