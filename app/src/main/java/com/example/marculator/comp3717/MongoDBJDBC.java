package com.example.marculator.comp3717;

/**
 * Created by student327 on 31/01/2015.
 */

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MongoDBJDBC{
    public static void main( String args[] ){
        try{
            // To connect to mongodb server
            //MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
            //***********use the above (uncomment line above) when wanting to use localhost!!!!**************//
            //otherwise use this stuff://
            String textUri = "mongodb://rosannawubs:comp3717@ds039431.mongolab.com:39431/bcitproject";
            MongoClientURI uri = new MongoClientURI(textUri);
            MongoClient m = new MongoClient(uri);
            // Now connect to the database
            String username = "rosannawubs";
            char[] password = {'c','o','m','p','3','7','1','7'};
            DB db = m.getDB( "bcitproject" );
            System.out.println("Connect to database successfully");
            boolean auth = db.authenticate(username, password);
            System.out.println("Authentication: "+auth);
            DBCollection coll = db.getCollection("courseTemplates");
            System.out.println("Collection mycol selected successfully");
            BasicDBObject doc = new BasicDBObject("title", "MongoDB").
                    append("description", "database").
                    append("likes", 100).
                    append("url", "http://www.tutorialspoint.com/mongodb/").
                    append("by", "tutorials point");
            //coll.insert(doc);
            //System.out.println("Document inserted successfully");
            //*****uncomment the two code lines above - > that will insert a document***//

            //print the records
            DBCursor cursor = coll.find();
            int i=1;
            while (cursor.hasNext()) {
                System.out.println("Inserted Document: "+i);
                System.out.println(cursor.next());
                i++;
            }
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
}
