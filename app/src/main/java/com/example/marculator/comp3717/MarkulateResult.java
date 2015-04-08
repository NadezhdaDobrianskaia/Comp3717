package com.example.marculator.comp3717;

import android.app.ListActivity;
import android.content.Intent;
//import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
//import android.widget.Toast;

import java.util.ArrayList;


public class MarkulateResult extends ListActivity {
    TextView courseName, displayMark;
    Course myCourse; /// a variable to hold the course that was passed over from the CourseDetails screen
    ArrayList<Item> items;//"change" = new ArrayList<Item>();
    ArrayList<String> itemsNames = new ArrayList<String>();
    double sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markulate_result);
        courseName = (TextView)findViewById(R.id.courseName);
        displayMark = (TextView)findViewById(R.id.yourMark);
        Intent i = getIntent();
        myCourse = (Course)i.getSerializableExtra("myCourse");
        setCourse();
        ListView listView = getListView();
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    protected void setCourse(){

        if(myCourse.getCourseName() != null)
            courseName.setText(myCourse.getCourseName());
        items = myCourse.getItems();
        for (int i = 0; i < items.size(); i++) {
            Item temp = items.get(i);
             if(Double.compare(temp.getMarkOutOf(),0.0)==0) {
                 itemsNames.add(temp.getCategory() + "     " + temp.getItemName() + "       " + " 0.0 %");
             }
             else {
                 double percent = (temp.getMyMark() / temp.getMarkOutOf())*100;
                 itemsNames.add(temp.getCategory() + "     " + temp.getItemName() + "     " + String.format("%.2f", percent) + "%");
             }
        }

        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemsNames));
    }


    public void Marculate(View v){
        sum = 0;
        double weight = 0;
        double percent = 0;
        double myMark = 0;
        for (int i = 0; i < items.size(); i++) {
            Item temp = items.get(i);

            if(Double.compare(temp.getMarkOutOf(),0.0)==0){
               ;// Toast.makeText(getBaseContext(),"if not a zero",Toast.LENGTH_LONG).show();

            }
            else{
                weight = temp.getWeight();
                //Toast.makeText(getBaseContext(),String.valueOf(weight),Toast.LENGTH_LONG).show();
                percent = temp.getMyMark() / temp.getMarkOutOf()*100;
                myMark = percent*weight/100;
                //Toast.makeText(getBaseContext(),String.valueOf(temp.getMyMark() / temp.getMarkOutOf()*100),Toast.LENGTH_LONG).show();
                sum = sum + myMark;
              //  Toast.makeText(getBaseContext(),String.valueOf(sum),Toast.LENGTH_LONG).show();
            }
                //Toast.makeText(getBaseContext(),String.valueOf(temp.getMyMark() / temp.getMarkOutOf()*100),Toast.LENGTH_LONG).show();
               // Toast.makeText(getBaseContext(),"Not a number",Toast.LENGTH_LONG).show();
        }
        displayMark.setText(String.format("%.2f", sum));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_markulate_result, menu);
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
