package com.example.marculator.comp3717;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void addCourseScreenButton(View v) {
        startActivity(new Intent(getApplicationContext(), templatesScreen.class));
    }

    public void chartsScreen(View v){
        startActivity(new Intent(getApplicationContext(), chartsScreen.class));
    }

    public void viewDetailsScreen(View v){
       // inputMarksScreen.val = ((TextView)findViewById(R.id.textView_course)).getText().toString();
        //Log.d("TextView", ((TextView) findViewById(R.id.textView_course)).getText().toString());

        startActivity(new Intent(getApplicationContext(), inputMarksScreen.class));
    }

    public void markulateScreen(View v){
        startActivity(new Intent(getApplicationContext(), markulateScreen.class));
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
