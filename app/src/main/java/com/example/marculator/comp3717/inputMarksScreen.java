package com.example.marculator.comp3717;

import android.app.Activity;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;


public class inputMarksScreen extends Activity {

    //public static String val;

    //TextView course1;
    String course1Name = "Hello";

    String[] courses ={
        course1Name,
        "course 2",
        "course 3",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_marks_screen);

        //course1 = (TextView)findViewById(R.id.textView_course);
        //course1Name = course1.getText().toString();

       // Log.d("val", val);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, courses);

        AutoCompleteTextView textView = (AutoCompleteTextView)findViewById(R.id.autoFillCourses);
        textView.setThreshold(3);
        textView.setAdapter(adapter);
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
}
