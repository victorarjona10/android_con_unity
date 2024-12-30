package com.example.restclientservweb;


import com.google.gson.annotations.SerializedName;

public class Products {
    @SerializedName("nameProduct")
    private String name;

    @SerializedName("price")
    private int price;

    @SerializedName("id")
    private int id;

    public Products(String name, int price, int id) {
        this.name = name;
        this.price = price;
        this.id = id;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

   public int getId() {
    return id;
}

    public void setId(int id) {this.id = id;}

    @Override
    public String toString() {
        return name + " - " + price + " - " + id;
    }
}