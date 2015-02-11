package com.example.marculator.comp3717;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;


public class templatesScreen extends ActionBarActivity {

//    public final static String EXTRA_MESSAGE = "com.calculatorAssign.myapplication.editCourseDetails";
    TextView course1, course2, course3, course4;
    Button editCourse1, editCourse2, editCourse3, editCourse4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_templates_screen);
        course1 = (TextView)findViewById(R.id.textView_course1);
        course2 = (TextView)findViewById(R.id.textView_course2);
        course3 = (TextView)findViewById(R.id.textView_course3);
        course4 = (TextView)findViewById(R.id.textView_course4);
        editCourse1 = (Button)findViewById(R.id.templates_editBtn_course1);
        editCourse2 = (Button)findViewById(R.id.templates_editBtn_course2);
        editCourse3 = (Button)findViewById(R.id.templates_editBtn_course3);
        editCourse4 = (Button)findViewById(R.id.templates_editBtn_course4);
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
        Bundle course = new Bundle();
        course.putString("course","add was called");
        i.putExtras(course);
        startActivityForResult(i, 1);
    }


    //Called when the user clicks the add button for add course screen
    public void editCourseDetails(View view){
        Intent i = new Intent(this,editCourseDetails.class);
        Bundle course = new Bundle();
        //add if statment to make sure that the edit is editing the right one
        if(view == editCourse1)
            course.putString("course",course1.getText().toString());
        else if(view == editCourse2)
            course.putString("course",course2.getText().toString());
        else if(view == editCourse3)
            course.putString("course",course3.getText().toString());
        else if(view == editCourse4)
            course.putString("course",course4.getText().toString());
        else //make an error
            course.putString("course","empty");
        i.putExtras(course);



        startActivityForResult(i, 2);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 2) {
                Bundle bundle = data.getExtras();
                course1.setText(bundle.getString("courseName"));
            }
            if (requestCode == 1) {
                ViewGroup layout = (ViewGroup) findViewById(R.id.templates_linear_layout);

                TextView n=new TextView(this);
               // n.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                n.setText("Added tv");
                layout.addView(n);
                Bundle bundle = data.getExtras();
                n.setText(bundle.getString("courseName"));
            }
        }

    }
}
