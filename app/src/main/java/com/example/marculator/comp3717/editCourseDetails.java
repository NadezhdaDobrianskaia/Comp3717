package com.example.marculator.comp3717;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class editCourseDetails extends ActionBarActivity {


    private EditText courseName; //category, weight, dueDate;
    private EditText category,item, weight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course_details);
        courseName = (EditText)findViewById(R.id.edit_course_details_course_editTextName);
        category = (EditText)findViewById(R.id.edit_course_details_category);
        item = (EditText)findViewById(R.id.edit_course_details_itemName);
        weight = (EditText)findViewById(R.id.edit_course_details_weight);
        Bundle course = getIntent().getExtras();
        courseName.setText(course.getString("course"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
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

    //Called when the user clicks the add button for add activity screen
    public void addToTemplatesScreen(View view){
        Intent i = new Intent();
        Bundle name = new Bundle();
        name.putString("courseName",courseName.getText().toString());
        //name.putString("category",category.getText().toString());
        //name.putInt("weight",Integer.parseInt( weight.getText().toString()));
        i.putExtras(name);
        setResult(RESULT_OK, i);
        finish();
    }
}
