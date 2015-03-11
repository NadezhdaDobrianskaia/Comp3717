package com.example.marculator.comp3717;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class CourseDetailsActivity extends ListActivity {

    TextView itemNameLabel, weightLabel, categoryLabel;
    EditText courseName, itemName, weight;
    Course myCourse;
    Spinner mySpinner;
    private static final String[] paths = {"Quiz", "Assignment", "Lab", "Exam","Other"};
    Item myItem;
    ArrayList<Item> items = new ArrayList<Item>();
    ArrayList<String> itemsNames = new ArrayList<String>();
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


        //listview code
        //ListView listView = getListView();
        //listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        //listView.setTextFilterEnabled(true);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,paths));
    }
    public void onListItemClick(ListView parent, View v, int position, long id){
        Toast.makeText(this, "you have selected"+ paths[position], Toast.LENGTH_SHORT).show();
    }
 /*   public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
                break;
            case 3:
                //whatever
                break;
            case 4:
                //whatever;
                break;
        }
    }
*/

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
        ArrayAdapter<String>adapter2 = new ArrayAdapter<String>(CourseDetailsActivity.this,
                android.R.layout.simple_spinner_item,paths);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(adapter2);
        //com.example.marculator.comp3717.CourseDetailsActivity;
        ;
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.category_list, android.R.layout.simple_spinner_item);
        mySpinner.setAdapter(adapter);
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        myItem.setCategory("Quiz");
                        break;
                    case 1:
                        myItem.setCategory("Assignment");
                        break;
                    case 2:
                        myItem.setCategory("Lab");
                        break;
                    case 3:
                        myItem.setCategory("Exam");
                        break;
                    case 4:
                        myItem.setCategory("Other");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    public void addItem(View v){
        myItem.setItemName(itemName.getText().toString());
        myItem.setWeight(Double.parseDouble(weight.getText().toString()));

        if(items.add(myItem)) {
            for(int i=0; i<items.size(); i++) {
                itemsNames.add(myItem.getItemName());
                Item temp = items.get(i);
                Toast.makeText(getBaseContext(), temp.getCategory(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getBaseContext(), temp.getItemName(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(getBaseContext(), temp.getWeight().toString(), Toast.LENGTH_SHORT).show();
            }
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
