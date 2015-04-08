package com.example.marculator.comp3717;

//import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
//import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//start of two items in a list
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//import android.app.ListActivity;
//import android.os.Bundle;
//import android.widget.SimpleAdapter;

public class CourseDetailsActivity extends ListActivity {

    TextView itemNameLabel, weightLabel, categoryLabel;
    EditText courseName, itemName, weight;
    Course myCourse; /// a variable to hold the course that was passed over from the CourseDetails screen
    Spinner mySpinner;
    private static final String[] paths = {"Quiz", "Assignment", "Lab", "Exam","Other"};
    Item myItem;
    ArrayList<Item> items;//"change" = new ArrayList<Item>();
    ArrayList<String> itemsNames = new ArrayList<String>();
    Button btnAddItem;
    /// this method is where the magic happens to populate a new course object
    /// or edit the already-input course object
    /// the intent is passed into the method and un-packaged to get the course object content
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        mapToIds();     //method to map all ids to the view types
        Intent i = getIntent();
        myCourse = (Course)i.getSerializableExtra("myCourse");
        setCourse();
        //Creating an adapter so the user can select the category for his item
        mySpinner = (Spinner) findViewById(R.id.category);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.category_list, android.R.layout.simple_spinner_item);
        mySpinner.setAdapter(adapter);
        mySpinner.setOnItemSelectedListener(spinnerListener);


        //the listview stores all the items that the user has added so far
        ListView listView = getListView();
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setTextFilterEnabled(true);


    }
    String categoryString = "";
    private AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            switch (position) {
                case 0:
                    myItem.setCategory("Quiz");
                    categoryString = "Quiz";
                    break;
                case 1:
                    myItem.setCategory("Assignment");
                    categoryString = "Assignment";
                    break;
                case 2:
                    myItem.setCategory("Lab");
                    categoryString = "Lab";
                    break;
                case 3:
                    myItem.setCategory("Exam");
                    categoryString = "Exam";
                    break;
                case 4:
                    myItem.setCategory("Other");
                    categoryString = "Other";
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

/// this method either unwraps the Course object to get all the items,
/// or if it is a new course object (when user clicked add) -
/// this method will not add any items and will just set a list adapter
/// to hold the items when they come
    protected void setCourse(){
        if(myCourse.getCourseName() != null)
            courseName.setText(myCourse.getCourseName());
        items = myCourse.getItems();
        for (int i = 0; i < items.size(); i++) {
            Item temp = items.get(i);
            itemsNames.add(temp.getCategory()+"     "+ temp.getItemName()+"     "+temp.getWeightString());
        }
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemsNames));
    }

    /// this method is triggered when the user clicks on "add item" button from the coursedetailsactivity
    /// a new item will be made based on the user's choice
    public void newItem(View v){
        setItemVisibilityOn(); // show fields for user to set their preferences on new item
        myItem = new Item(itemName.getText().toString(), 0);
        mySpinner.setOnItemSelectedListener(spinnerListener);
    }

    /// this method is triggered when the user clicks "add" after setting their
     /// preferences for the new item they want
     /// it takes all user preferences and stores as an Item object, and puts into an array
    public void addItem(View v){
        String tempName = itemName.getText().toString();

        if(weight.getText().toString().compareTo("")==0 || itemName.getText().toString().compareTo("")==0) {
            Toast.makeText(getBaseContext(), "Please Enter Data", Toast.LENGTH_LONG).show();
        }
        else {
            //Toast.makeText(getBaseContext(), "else", Toast.LENGTH_LONG).show();
            myItem.setCategory(categoryString);
            myItem.setItemName(itemName.getText().toString());
            if(Double.parseDouble(weight.getText().toString())>0 && Double.parseDouble(weight.getText().toString())<100) {
                myItem.setWeight(Double.parseDouble(weight.getText().toString()));

                //Toast.makeText(getBaseContext(), String.valueOf(editing),Toast.LENGTH_LONG).show(); //testing
                /// if the user wants to add, i.e. they have not made a choice by selecting from the list to edit
                if (editing == -1) {
                    if (myCourse.addCourseArrayList(myItem)) {
                        itemsNames.add(myItem.getCategory() + "    " + myItem.getItemName() + "    " + myItem.getWeight());  //this is for the string list
                        // Toast.makeText(getBaseContext(), "I am adding item", Toast.LENGTH_LONG).show();
                    }
                }
                /// if the user wants to edit,
                else {
                    itemsNames.set(editing, myItem.getCategory() + "    " + myItem.getItemName() + "    " + myItem.getWeight());
                    myCourse.editCourseArrayList(editing, myItem);
                    editing = -1;
                    //Toast.makeText(getBaseContext(), "I am editing item", Toast.LENGTH_LONG).show();

                }
            }
            else{
                Toast.makeText(getBaseContext(),"Please enter a weight between 0 and 100", Toast.LENGTH_LONG).show();
            }
        }
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemsNames)); //this is for the string list
        setItemVisibilityOff();
        //double list
     /*   list = buildData();
        String[] from = { "name", "weight" };
        int[] to = { android.R.id.text1, android.R.id.text2 };
        SimpleAdapter adapter = new SimpleAdapter(this, list,android.R.layout.simple_list_item_2, from, to);
        setListAdapter(adapter);
    */

    }
    /* double list continued
    ArrayList<Map<String, String>> list =new ArrayList<Map<String, String>>();
    private ArrayList<Map<String, String>> buildData() {
        list.add(putData(myItem.getItemName(), myItem.getWeightString()));
        return list;
    }

    private HashMap<String, String> putData(String name, String purpose) {
        HashMap<String, String> item = new HashMap<String, String>();
        item.put("name", name);
        item.put("weight", purpose);
        return item;
    }
    */


    /// this member will change based on the user's choice or non-choice on the item list
    int editing = -1;
    public void onListItemClick(ListView parent, View v, int position, long id){
        editing = position;
       // Toast.makeText(getBaseContext(), String.valueOf(editing),Toast.LENGTH_LONG).show();
        newItem(v);
        itemName.setText(items.get(position).getItemName());
        weight.setText(items.get(position).getWeightString());
    }

    /// this method is triggered when the user has selected "Save" and wants to go back
     /// to the courses screen (templatesactivity)
    public void detailsAdded(View v){
        Intent i = new Intent();
        myCourse.setCourseName(courseName.getText().toString());
        i.putExtra("myCourseUpdated",myCourse);
        setResult(RESULT_OK, i);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course_details_screen, menu);
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

    /// this method lets the user see their options when adding a new item
    private void setItemVisibilityOn(){
        categoryLabel.setVisibility(View.VISIBLE);
        itemNameLabel.setVisibility(View.VISIBLE);
        weightLabel.setVisibility(View.VISIBLE);
        mySpinner.setVisibility(View.VISIBLE);
        itemName.setVisibility(View.VISIBLE);
        weight.setVisibility(View.VISIBLE);
        btnAddItem.setVisibility(View.VISIBLE);
    }

    /// this method keeps the screen cleaning by not right away displaying
    /// options to set up a new item
    private void setItemVisibilityOff(){
        categoryLabel.setVisibility(View.GONE);
        itemNameLabel.setVisibility(View.GONE);
        weightLabel.setVisibility(View.GONE);
        mySpinner.setVisibility(View.GONE);
        itemName.setVisibility(View.GONE);
        weight.setVisibility(View.GONE);
        btnAddItem.setVisibility(View.GONE);
    }
    /// finds all elements in the xml and populates their handle to a class member
    public void mapToIds(){
        courseName = (EditText)findViewById(R.id.course_name);
        categoryLabel = (TextView)findViewById(R.id.category_label);
        itemNameLabel = (TextView)findViewById(R.id.item_name_label);
        weightLabel =(TextView)findViewById(R.id.category_weight_label);
        mySpinner = (Spinner)findViewById(R.id.category);
        itemName = (EditText)findViewById(R.id.item_name);
        weight = (EditText)findViewById(R.id.category_weight);
        btnAddItem = (Button)findViewById(R.id.btn_add_item);
        setItemVisibilityOff();
    }
}
