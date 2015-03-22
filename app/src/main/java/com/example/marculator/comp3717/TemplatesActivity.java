package com.example.marculator.comp3717;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

    /// this method is one of the methods
    /// responsible for initiating a new activity (the coursedetailsactivity)
    /// the intent is built and the empty course item is passed over
    public void add_course_details(View view) {
        Intent courseDetails = new Intent(this, CourseDetailsActivity.class);
        myCourse = new Course();
        courseDetails.putExtra("myCourse", myCourse);
        startActivityForResult(courseDetails, 1);

    }

    /// this method is one of the methods
    /// responsible for initiating a new activity (the coursedetailsactivity)
    /// the intent is built and the populated course object is passed over in it
    int editing = -1;
    public void onListItemClick(ListView parent, View v, int position, long id){
        editing = position;
        Intent courseDetails = new Intent(this, CourseDetailsActivity.class);
        myCourse = courseList.get(position); // gets the course where the user clicked

        courseDetails.putExtra("myCourse", myCourse);
        startActivityForResult(courseDetails, 2);

    }

    /// this method is triggered when user clicks Save button
    // the data is saved to a file on their phone
    public void save_data(View v){
        try{
            FileOutputStream fOut = openFileOutput("dataList.bin",MODE_PRIVATE);
            ObjectOutputStream osw = new ObjectOutputStream(fOut);
            osw.writeObject(courseList);
            osw.flush();
            osw.close();
            Toast.makeText(getBaseContext(), "file Saved successfully", Toast.LENGTH_SHORT).show();
            setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, display));
        }
        catch (FileNotFoundException e){
            Toast.makeText(getBaseContext(), "FileNotfound", Toast.LENGTH_SHORT).show();
        }
        catch (IOException ioe){
            Toast.makeText(getBaseContext(), "io", Toast.LENGTH_SHORT).show();
            ioe.printStackTrace();
        }

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

    /// this method is triggered when coming back from the coursedetails activity screen
    /// it will take the intent the activity originally sent and unpackage it to get the changed
    /// or new courseobject
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            myCourse = (Course) data.getSerializableExtra("myCourseUpdated");

            /// if the user has added a course this gets called
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

            /// if the user has editing a course, this gets called
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


//----------------------------------pulling from Mongo DB
    int startInputCourse; //save the indes where the course was added
    String importCourse;
    public void load_data(View v){
        new LoadCourse().execute("https://api.mongolab.com/api/1/databases/bcitcstnadiad/collections/LabMongodb?apiKey=OcEZk6lUN0M-oCQ7ej4gDKOaREvtkPXU");
    }

    public String readJSONFeed(String URL){
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL);
        try{
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if(statusCode == 200){
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(content));
                String line;
                while((line=reader.readLine()) != null){
                    stringBuilder.append(line);
                }
            }
            else{
                Log.e("JSON", "Failed to download file");
            }
        }
        catch(ClientProtocolException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private class LoadCourse extends AsyncTask<String, Void, String>{
        protected String doInBackground(String... urls){
            return readJSONFeed(urls[0]);
        }
        protected void onPostExecute(String result){
            try{
                JSONArray jsonArray = new JSONArray(result);
                Log.i("JSON","number of surveys inFeed:" +
                        jsonArray.length());
                Toast.makeText(getBaseContext(), "The Changes were not wiped", Toast.LENGTH_SHORT).show();

                startInputCourse =courseList.size();

                //print out the content of the jsonFeed----
                for(int i=0; i<jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                  //  Toast.makeText(getBaseContext(), jsonObject.getString("my_course") + "Start at " + startInputCourse, Toast.LENGTH_SHORT).show();
                    Course temp = new Course();
                    temp.setCourseName(jsonObject.getString("my_course"));
                    courseList.add(temp);
                    display.add(jsonObject.getString("my_course"));

                }

               // Toast.makeText(getBaseContext(), "Finish loading", Toast.LENGTH_SHORT).show();
                importCourse = courseList.get(startInputCourse).getCourseName();
                //Toast.makeText(getBaseContext(), startInputCourse + importCourse, Toast.LENGTH_SHORT).show();
                new LoadItems().execute("https://api.mongolab.com/api/1/databases/bcitcstnadiad/collections/LabMongodb2?apiKey=OcEZk6lUN0M-oCQ7ej4gDKOaREvtkPXU");

                displayList();
            }
            catch(JSONException e){
                e.printStackTrace();
            }
        }
    }

    private class LoadItems extends AsyncTask<String, Void, String>{
        protected String doInBackground(String... urls){
            return readJSONFeed(urls[0]);
        }
        protected void onPostExecute(String result){
            try{
                JSONArray jsonArray = new JSONArray(result);
                Log.i("JSON","number of surveys inFeed:" +
                        jsonArray.length());
                //print out the content of the jsonFeed----
                for(int i=0; i<jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                   if(jsonObject.getString("my_course").equals(importCourse)){

                       Item myTempItem = new Item(jsonObject.getString("category"),jsonObject.getString("category_name"),Double.valueOf(jsonObject.getString("weight")).doubleValue());
                       Course temp = courseList.get(startInputCourse);
                       temp.getItems().add(myTempItem);
                      // Toast.makeText(getBaseContext(), jsonObject.getString("category") + " Inside " + importCourse  , Toast.LENGTH_SHORT).show();
                    }

                }
                startInputCourse +=1;
                if(startInputCourse < courseList.size()) {
                    importCourse = courseList.get(startInputCourse).getCourseName();
                   // Toast.makeText(getBaseContext(), startInputCourse + importCourse, Toast.LENGTH_SHORT).show();
                    new LoadItems().execute("https://api.mongolab.com/api/1/databases/bcitcstnadiad/collections/LabMongodb2?apiKey=OcEZk6lUN0M-oCQ7ej4gDKOaREvtkPXU");
                }

            }
            catch(JSONException e){
                e.printStackTrace();
            }
        }
    }

    public void displayList(){
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, display));
    }
//-----------------------------------end pulling prom Mongo DB
}