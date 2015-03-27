package com.example.marculator.comp3717;

import android.app.Activity;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class InputMarksActivity extends Activity {

    /// an array of all the course names to populate spinner 1
    ArrayList<String> strCourses = new ArrayList<String>();
    /// an array of all the items names to populate spinner 2
    ArrayList<String> strItems = new ArrayList<>();
    /// all courses taken from phone data file
    ArrayList<Course> courseList = new ArrayList<Course>();
    /// all courses taken from courselist
    ArrayList<Item> itemList = new ArrayList<Item>();

    /// all marks taken from phone data file
    ArrayList<Item> marksList = new ArrayList<Item>();





    /// a handle to the spinner element
    Spinner courseNames;
    /// a handle to the item element
    Spinner itemNames;

    /// one course object
    // (it will be constantly updating if user changes the selection of the course in the spinner)
    Course MyCourse;
    int editingCourse;

    /// one item object
    // (it will be constantly updating if user changes the selection of the course in the spinner)
    Item MyItem;
    int editingItem;

    // a handle to the item edit text
    EditText mark;

    // a handle to the item edit text
    EditText markOutOf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_marks);

        courseNames = (Spinner) findViewById(R.id.spnCourses);
        itemNames = (Spinner) findViewById(R.id.spnItems);
        loadCourseData();
        loadMarksData();
        mark = (EditText) findViewById(R.id.edtInputMark);
        markOutOf = (EditText) findViewById(R.id.edtInputMarkOutOf);

    }

    /// this method is triggered when someone choose a spinner item for course
    /// it calls the next spinner element on the xml
    /// and says "populate items from this course!" based on user's choice
    /// see  method:(loadItemData)
    private AdapterView.OnItemSelectedListener courseNamesClickListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            editingCourse = position;
            strItems.clear(); // reset the spinner items
            itemList.clear(); // reset the array
            MyCourse = courseList.get(position);
            ArrayList<Item> temp = MyCourse.getItems();
            for(Item i : temp) {
                strItems.add(i.ToString());
                itemList.add(i);
            }
            loadItemData();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    /// this method is triggered when someone choose a spinner item for course item
    private AdapterView.OnItemSelectedListener itemNamesClickListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            editingItem = position;
            MyItem = itemList.get(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    /// populates the spinner element 2 (the items selection)
    public void loadItemData() {
        ArrayAdapter adapterSpinnerItems = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strItems);
        itemNames.setAdapter(adapterSpinnerItems);
        itemNames.setOnItemSelectedListener(itemNamesClickListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_details_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    /// this method is triggered when user clicks Input Marks button THIS IS NOT IMPLEMENTED YET
    // the data is saved to a file on their phone
    public void save_data(View v){
        MyItem.setMyMark(Double.parseDouble(mark.getText().toString()));
        MyItem.setMarkOutOf(Double.parseDouble(markOutOf.getText().toString()));
        itemList.set(editingItem, MyItem);
        MyCourse.setItems(itemList);
        courseList.set(editingCourse, MyCourse);

        try{
            FileOutputStream fOut = openFileOutput("dataList.bin",MODE_PRIVATE);
            ObjectOutputStream osw = new ObjectOutputStream(fOut);
            osw.writeObject(courseList);
            osw.flush();
            osw.close();
            Toast.makeText(getBaseContext(), "file Saved successfully", Toast.LENGTH_SHORT).show();
        }
        catch (FileNotFoundException e){
            Toast.makeText(getBaseContext(), "FileNotfound", Toast.LENGTH_SHORT).show();
        }
        catch (IOException ioe){
            Toast.makeText(getBaseContext(), "io", Toast.LENGTH_SHORT).show();
            ioe.printStackTrace();
        }
    }

    /// this method populates the activity with phone file data for courses
    /// or displays nothing and the user can add all as needed
    public void loadCourseData(){
        try {
            FileInputStream fIn = openFileInput("dataList.bin");
            ObjectInputStream isr = new ObjectInputStream(fIn);

            courseList = (ArrayList<Course>)isr.readObject();
            isr.close();
            fIn.close();
            Toast.makeText(getBaseContext(),(courseList.get(0)).getCourseName(),Toast.LENGTH_LONG).show();
            for (int i = 0; i < courseList.size(); i++) {
                Course temp = courseList.get(i);
                strCourses.add(temp.getCourseName());
            }

            ArrayAdapter adapterSpinnerCourses = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strCourses);
            courseNames.setAdapter(adapterSpinnerCourses);
            courseNames.setOnItemSelectedListener(courseNamesClickListener);

        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
        catch(ClassNotFoundException cnf){
            cnf.printStackTrace();
        }

    }


    /// this method populates the activity with phone file data for marks
    /// or displays nothing and the user can add all as needed
    public void loadMarksData(){
        try {
            FileInputStream fIn = openFileInput("marksList.bin");
            ObjectInputStream isr = new ObjectInputStream(fIn);

            marksList = (ArrayList<Item>)isr.readObject();
            isr.close();
            fIn.close();
            Toast.makeText(getBaseContext(),(marksList.get(0)).getItemName(),Toast.LENGTH_LONG).show();
            for (int i = 0; i < marksList.size(); i++) {
                Item temp = marksList.get(i);
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
        catch(ClassNotFoundException cnf){
            cnf.printStackTrace();
        }

    }
}
