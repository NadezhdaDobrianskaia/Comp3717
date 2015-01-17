package com.example.marculator.comp3717;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


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
        startActivity(new Intent(getApplicationContext(), addCourseScreen.class));
    }

    public void chartsScreen(View v){
        startActivity(new Intent(getApplicationContext(), chartsScreen.class));
    }

    public void viewDetailsScreen(View v){
        startActivity(new Intent(getApplicationContext(), viewDetailsScreen.class));
    }

    public void priorityItemsScreen(View v){
        startActivity(new Intent(getApplicationContext(), priorityItemsScreen.class));
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
