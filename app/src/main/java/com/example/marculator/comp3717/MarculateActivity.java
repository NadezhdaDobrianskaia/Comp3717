package com.example.marculator.comp3717;

import android.app.ListActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;


public class MarculateActivity extends ListActivity {

    Course myCourse;
    ArrayList<String> display = new ArrayList<String>();
    ArrayList<Course> courseList = new ArrayList<Course>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marculate);
        ListView listView = getListView();
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setTextFilterEnabled(true);
        loadData();
    }
    /// this method populates the activity with phone file data for courses
    /// or displays nothing and the user can add all as needed
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_marculator, menu);
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
