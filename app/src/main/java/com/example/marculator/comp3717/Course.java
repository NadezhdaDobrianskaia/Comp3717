package com.example.marculator.comp3717;

import android.widget.Button;

import java.io.Serializable;
import java.util.ArrayList;
 
public class Course implements Serializable {
    private String courseName = "";
    private String category = "";
    private Button editButton = null;
    private ArrayList Items = null;

    //constructor called when add button is pressed without new Button
    public Course(String courseName) {
        this.courseName = courseName;
        this.category = null;
        this.Items = null;

    }
    //constructor without the new button
    public Course(String courseName, String category) {
        this.courseName = courseName;
        this.category = category;

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

    public void setItems(ArrayList items) {
        Items = items;
    }

    public void addItems(Item i){
        Items.add(i);
    }
}
