package com.example.marculator.comp3717;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


public class TemplatesActivity extends ListActivity {

    Course myCourse;
    ArrayList<String> display = new ArrayList<String>();
    ArrayList<Course> courseList = new ArrayList<Course>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_templates);
        ListView listView = getListView();
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setTextFilterEnabled(true);
        loadData();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_templates_screen, menu);
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

    public void add_course_details(View view) {
        Intent courseDetails = new Intent(this, CourseDetailsActivity.class);
        myCourse = new Course();
        courseDetails.putExtra("myCourse", myCourse);
        startActivityForResult(courseDetails, 1);

    }
    int editing = -1;
    public void onListItemClick(ListView parent, View v, int position, long id){
        editing = position;
        Intent courseDetails = new Intent(this, CourseDetailsActivity.class);
        myCourse = courseList.get(position);

        courseDetails.putExtra("myCourse", myCourse);
        startActivityForResult(courseDetails, 2);

    }

    public void save_data(View v){
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
    public void loadData(){
        try {
            FileInputStream fIn = openFileInput("dataList.bin");
            ObjectInputStream isr = new ObjectInputStream(fIn);

            courseList = (ArrayList<Course>)isr.readObject();
            isr.close();
            fIn.close();
            Toast.makeText(getBaseContext(),(courseList.get(0)).getCourseName(),Toast.LENGTH_LONG).show();
            for (int i = 0; i < courseList.size(); i++) {
                Course temp = courseList.get(i);
                display.add(temp.getCourseName());
            }
            setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, display));
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            myCourse = (Course) data.getSerializableExtra("myCourseUpdated");
            if (requestCode == 1) {
                courseList.add(myCourse);
                display.add(myCourse.getCourseName());
                /*ArrayList<Item> temp = myCourse.getItems();
                for (int i = 0; i < temp.size(); i++) {
                    //Item temp = (myCourse.getItems()).get(i);
                    Item temp2 = temp.get(i);
                    Toast.makeText(getBaseContext(), temp2.getCategory(), Toast.LENGTH_SHORT).show();
                }*/



            }
            if(requestCode == 2){
                if(editing != -1) {
                    display.set(editing, myCourse.getCourseName());
                    courseList.set(editing, myCourse);
                    editing = -1;
                }
            }
            setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, display));
        }


    }
}