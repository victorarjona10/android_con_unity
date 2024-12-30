package com.example.restclientservweb;

import java.util.LinkedList;
import java.util.List;

public class User {

    String id;
    String email;
    String username;
    String password;
    int dinero = 20;
    //Stats stats;

    List<Products> productos;


    public User(String email, String username, String password) {
        this();
        this.email = email;
        this.username = username;
        this.password = password;
        this.productos = new LinkedList<>();
    }
    public User() {
    }

    private void setId(String id) {
        this.id=id;
    }

    public String getId(){
        return id;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Products> getProductos(){return productos; }
    public void addProducto(Products p){
        productos.add(p);
        dinero = dinero - p.getPrice();
    }

    public int getDinero(){
        return this.dinero;
    }
    public String getEmail(){
        return email;
    }
    public void setDinero(int dinero){
        this.dinero = dinero;
    }


    public void setProductos(List<Products> productos){this.productos = productos; }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public void setUser(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.dinero = user.getDinero();
        this.productos = user.getProductos();
        //this.stats = user.getStats();
    }

//    public Stats getStats() {
//        return stats;
//    }
//
//    public void setStats(Stats stats) {
//        this.stats = stats;
//    }
    //
//    public User(String username, int dinero) {
//        this.username = username;
//        this.dinero = dinero;
//        this.productos = new LinkedList<>();
//    }
//
}