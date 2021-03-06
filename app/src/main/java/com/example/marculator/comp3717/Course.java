package com.example.marculator.comp3717;

import android.widget.Button;

import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable {
    private String courseName = "";
    private String category = ""; //member not used for now
    private Button editButton = null; //member not used for now
    private ArrayList<Item> Items = new ArrayList<Item>();

    //constructor called when add button is pressed without new Button
    public Course(String courseName) {
        this.courseName = courseName;
        this.category = null;
    }
    public Course(){
    }

    //constructor with a new button
    public Course(String courseName, String category, Button editButton) {
        this.courseName = courseName;
        this.category = category;
        this.editButton = editButton;
    }


    public Button getEditButton() {
        return editButton;
    }

    public void setEditButton(Button editButton) {
        this.editButton = editButton;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList getItems() {
        return Items;
    }

    // adds the item to the end of the array
    public boolean addCourseArrayList(Item item) {
        if(Items.add(item))
            return true;
        return false;
    }
    // edits the item going by the position of the item in the existing array list
    public void editCourseArrayList(int index, Item item){
        Items.set(index, item);
    }


    public void setItems(ArrayList items) {
        Items = items;
    }

    public void addItems(Item i){
        Items.add(i);
    }
}
