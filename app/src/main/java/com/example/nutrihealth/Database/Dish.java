package com.example.nutrihealth.Database;

public class Dish {

    private String dishName;
    private String dishDescription;
    private String tokenID;

    //Constructors
    public Dish() {
    }

    public Dish(String dishName, String dishDescription) {
        this.dishName = dishName;
        this.dishDescription = dishDescription;

    }

    public Dish(String dishName, String dishDescription, String tokenID) {
        this.dishName = dishName;
        this.dishDescription = dishDescription;
        this.tokenID = tokenID;
    }

    //Getters and setters
    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDishName() { return this.dishName; }



    public void setDishDescription(String dishDescription) {this.dishDescription = dishDescription; }

    public String getDishDescription() {return this.dishDescription; }



    public String getTokenID() {
        return tokenID;
    }

    public void setTokenID(String tokenID) {
        this.tokenID = tokenID;
    }
}
