package com.example.marculator.comp3717;

import java.io.Serializable;

public class Item implements Serializable {

    private String itemName = "";
    private String category = "";
    private double weight;
    private double myMark;

    //constructor
    public Item(String category,String itemName , double weight){
        this.itemName = itemName;
        this.category = category;
        this.weight = weight;
        this.myMark = 0;
    }
    public Item(String itemName, double weight){
        this.itemName = itemName;
        this.category = null;
        this.weight = weight;
        this.myMark = 0;
    }

    public double getMyMark() {
        return myMark;
    }

    public void setMyMark(double myMark) {
        this.myMark = myMark;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
    public String getWeightString(){
        String str = String.valueOf(weight);
        return str;
    }

    public String ToString() { return itemName; }
}
