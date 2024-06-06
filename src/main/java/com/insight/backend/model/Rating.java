package com.insight.backend.model;


public class Rating {

    private int id;
    private String name;
    private String comment; 
    private int points;
    private Boolean nA;

    public void setId(int id){
        
        this.id = id;

    }

    public int getId(){

        return this.id;
    }

    public void setName(String name){

        this.name = name;

    }

    public String getName(){

        return this.name;
    }

    public void setComment(String comment){

        this.comment = comment;

    }

    public String getComment(){

        return this.comment;
    }

    public void setPoints(int points){

        this.points = points;

    }

    public int getPoints(){

        return this.points;
    }

    public void setNA(Boolean nA){

        this.nA = nA;

    }

    public Boolean getNA(){

        return this.nA;
    }

}
