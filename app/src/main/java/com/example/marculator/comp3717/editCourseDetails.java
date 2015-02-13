package com.example.marculator.comp3717;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class editCourseDetails extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    private EditText courseName; //category, weight, dueDate;
    private EditText item, weight;
    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course_details);
        courseName = (EditText)findViewById(R.id.edit_course_details_course_editTextName);

        item = (EditText)findViewById(R.id.edit_course_details_itemName);
        weight = (EditText)findViewById(R.id.edit_course_details_weight);
        Bundle course = getIntent().getExtras();
        courseName.setText(course.getString("course"));

        //Creating an adapter so the user can select the category for his item
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.category_list, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
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

    // The following to methods belong to the AdapterView.OnItemSelectedListener interface and must
    // overridden in order to implement the listener
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // I've cast the result of the spinner into a view. We can use this later to identify
        // the selected category. It is currently unused.

        // TextView myText = (TextView) view;
    }

    // This is called when the current selection disappears due to some event like touch getting
    // activated or the adapter becomes empty
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
