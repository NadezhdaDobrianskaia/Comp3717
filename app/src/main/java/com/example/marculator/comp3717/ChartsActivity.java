package com.example.marculator.comp3717;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
//import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;


public class ChartsActivity extends ActionBarActivity {
    private ListView lv;

    // Instantiating an array list (you don't need to do this,
    // you already have yours).
    List<String> allMarksData = new ArrayList<String>();

    /// all marks taken from phone data file
    ArrayList<Course> courseList = new ArrayList<Course>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

        lv = (ListView) findViewById(R.id.lstAllMarksData);

        loadData();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_charts, menu);
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

    /// this method populates the activity with phone file data for marks
    /// or displays nothing and the user can add all as needed
    public void loadData(){
        try {
            FileInputStream fIn = openFileInput("dataList.bin");
            ObjectInputStream isr = new ObjectInputStream(fIn);

            courseList = (ArrayList<Course>)isr.readObject();
            isr.close();
            fIn.close();
            //Toast.makeText(getBaseContext(), (courseList.get(0)).getCourseName(), Toast.LENGTH_LONG).show();
            for (int i = 0; i < courseList.size(); i++) {
                ArrayList<Item> tempItemList = new ArrayList<Item>();
                tempItemList = courseList.get(i).getItems();
                for( int j = 0; j < tempItemList.size(); j++) {
                    double percent = tempItemList.get(j).getMyMark()/tempItemList.get(j).getMarkOutOf();
                    allMarksData.add(courseList.get(i).getCourseName() + "   " +
                            tempItemList.get(j).getCategory() + "   " +
                            tempItemList.get(j).getItemName() + "   " +
                            percent*100 + "%");
                }
            }

            // This is the array adapter, it takes the context of the activity as a
            // first parameter, the type of list view as a second parameter and your
            // array as a third parameter.
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    allMarksData );

            lv.setAdapter(arrayAdapter);
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
}

