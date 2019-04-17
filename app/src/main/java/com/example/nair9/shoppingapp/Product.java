package com.example.nair9.shoppingapp;

/**
 * Created by nair9 on 08-04-2019.
 */
public class Product {

    String name;
    String price;
    String weight;
    int number;

    public Product(String i1, String i2, String i3, int i4) {
        name = i1;
        price = i2;
        weight = i3;


    }

    public String getName(){return name;}

    public String getPrice(){return price;}

    public String getWeight(){return weight;}

    public int getQuant(){return number;}

    public void setQuant(int i){this.number =number;}

}
