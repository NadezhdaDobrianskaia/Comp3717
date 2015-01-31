package com.example.marculator.comp3717;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.TextView;


public class templatesScreen extends ActionBarActivity {

//    public final static String EXTRA_MESSAGE = "com.calculatorAssign.myapplication.editCourseDetails";
    TextView course1, course2, course3, course4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_templates_screen);
        course1 = (TextView)findViewById(R.id.textView_course1);
        course2 = (TextView)findViewById(R.id.textView_course2);
        course3 = (TextView)findViewById(R.id.textView_course3);
        course4 = (TextView)findViewById(R.id.textView_course4);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_course_screen, menu);
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


    public void addCourseDetails(View view){
        Intent i = new Intent(this,editCourseDetails.class);
        startActivity(i);
    }


    //Called when the user clicks the add button for add course screen
    public void editCourseDetails(View view){
        Intent i = new Intent(this,editCourseDetails.class);
        startActivityForResult(i, 1);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Bundle bundle = data.getExtras();
                course1.setText(bundle.getString("courseName"));
            }
        }

    }
}
