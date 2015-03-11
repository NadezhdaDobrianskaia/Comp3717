package com.example.marculator.comp3717;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class courseDetailsScreen extends ActionBarActivity {

    TextView itemNameLabel, weightLabel, categoryLabel;
    EditText courseName, itemName, weight;
    Course myCourse;
    Spinner mySpinner;
    Item myItem;
    ArrayList<Item> items = new ArrayList<Item>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details_screen);
        mapToIds();     //method to map all ids to the view types
        Intent i = getIntent();
        myCourse = (Course)i.getSerializableExtra("myCourse");
        setCourse();
    }

    protected void setCourse(){
        if(myCourse.getCourseName() != null)
            courseName.setText(myCourse.getCourseName());
        if(myCourse.getItems() != null){
            Toast.makeText(getBaseContext(), "I should get the list of items", Toast.LENGTH_SHORT).show();
        }

    }


    public void newItem(View v){
        setItemVisibilityOn();
        myItem = new Item(itemName.getText().toString(), 0);

    }
    public void addItem(View v){
        myItem.setItemName(itemName.getText().toString());
        myItem.setWeight(Double.parseDouble(weight.getText().toString()));
        if(items.add(myItem)) {
            for(int i=0; i<items.size(); i++)
                Toast.makeText(getBaseContext(), items.get(i).getItemName(), Toast.LENGTH_SHORT).show();
        }

        setItemVisibilityOff();
    }

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

    private void setItemVisibilityOn(){
        categoryLabel.setVisibility(View.VISIBLE);
        itemNameLabel.setVisibility(View.VISIBLE);
        weightLabel.setVisibility(View.VISIBLE);
        mySpinner.setVisibility(View.VISIBLE);
        itemName.setVisibility(View.VISIBLE);
        weight.setVisibility(View.VISIBLE);
    }
    private void setItemVisibilityOff(){
        categoryLabel.setVisibility(View.GONE);
        itemNameLabel.setVisibility(View.GONE);
        weightLabel.setVisibility(View.GONE);
        mySpinner.setVisibility(View.GONE);
        itemName.setVisibility(View.GONE);
        weight.setVisibility(View.GONE);
    }
    public void mapToIds(){
        courseName = (EditText)findViewById(R.id.course_name);
        categoryLabel = (TextView)findViewById(R.id.category_label);
        itemNameLabel = (TextView)findViewById(R.id.item_name_label);
        weightLabel =(TextView)findViewById(R.id.category_weight_label);
        mySpinner = (Spinner)findViewById(R.id.category);
        itemName = (EditText)findViewById(R.id.item_name);
        weight = (EditText)findViewById(R.id.category_weight);
        setItemVisibilityOff();
    }
}
