package com.derandroidpro.example.jsonreaderexample_android;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // This is the URL where we are going to pull our json file from:
    final String jsonFileUrl = "http://derandroidpro.esy.es/Android_Beta/JsonReaderExample_GitHub/jsonscript_persons.json";
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // first initialize the text view which will be used to display the results:
        tv1 = findViewById(R.id.tv1);
        // check if internet is available: no internet - no json file from the internet :D
        if(internetAvailable()) {
            // run the AsyncTask that is used to do the everything else:
            new ReadJsonAsyncTask().execute(jsonFileUrl);
        } else {
            // if internet is not available, inform the user using a simple toast:
            Toast.makeText(this, "Internet not available - check your connection.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean internetAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    // let's create an AsyncTask: We use it, because we can't run the file download and the json
    // decoding on the UI thread, since it would freeze the UI until the file is downloaded.
    // The AsyncTask has got three parameters:
    // 1. The data type of the parameter of the doInBackground() Method - in our case String,
    //      since we want to pass the url of the json file to the method.
    // 2. The Progress Update Parameter Type: Used for progress updates: We don't use this anyways,
    //      so let's just use Object as a default data type.
    // 3. The return type of the doInBackground() Method (as well as the parameter data type of the onPostExecute-Method:
    //    We use an ArrayList of persons here, since the doInBackground()-Method extracts the information
    //    about the persons from the json script and creates an ArrayList of Objects from our class "Person"

    private class ReadJsonAsyncTask extends AsyncTask<String, Object,  ArrayList<Person>> {
        @Override
        protected ArrayList<Person> doInBackground(String... jsonFileUrlString) {
            // at first create the ArrayList where our persons will be stored in:
            ArrayList<Person> persons = new ArrayList<>();
            // since there always might be errors when doing internet things, we have to use a try -
            // catch case to prevent the app from crashing:
            try {
                // create an URL (object) from our URL-String that says. where the json object is stored on the server:
                URL jsonFileUrl = new URL(jsonFileUrlString[0]);
                // create an InputStreamReader from an InputStream that we get from our URL object.
                // This InputStreamReader can be used by the JsonReader to read the content from the json file:
                InputStreamReader isReader = new InputStreamReader(jsonFileUrl.openStream());
                // create a JsonReader: It is used to extract all the information from the json script
                JsonReader jsonReader = new JsonReader(isReader);

                /*let's define a few simple rules on how to read a json script using jsonReader:
                - you see {     -   write jsonReader.beginObject();
                - you see [     -   write jsonReader.beginArray();
                - you see <name of a parameter>    -   write jsonReader.nextName();
                - you see }     -   write jsonReader.endObject();
                - you see ]     -   write jsonReader.endArray();
                 */

                // Now apply these rules and read the data from the json script:

                //first line starts with "{", so use .beginObject():
                jsonReader.beginObject();
                // next line is a name of a parameter (user), so use .nextName():
                jsonReader.nextName();
                // next thing is a "[", so use .beginArray():
                jsonReader.beginArray();
                // Now we've entered the array that consists out of multiple persons. Now we have
                // to read out person by person, or let's say object by object. For this purpose we
                // use the hasNext() Method of the jsonReader. This method will return true as long as
                // there are still unread objects inside the array:
                while(jsonReader.hasNext()){
                    // the beginning of every object obviously is a "{", so use .beginObject():
                    jsonReader.beginObject();
                    // Now it's time to create our object for the current person. Have a look at
                    // the Person class before you continue!
                    // It contains three variables: name, profession and age. So we have to get
                    // these three parameters from every person in the json script and put the
                    // information into the person's object.
                    Person currentPerson = new Person();
                    // We've entered the current object of the person, so we have to cycle through
                    // every parameter that is attached to the current object. The jsonReader.hasNext()-Method
                    // will return true as long as there are unread parameters inside the current object:
                    while (jsonReader.hasNext()){
                        // let's get the name of the current parameter using the nextName()-Method.
                        // The name can be "name", "profession" or "age", so we have to check
                        // which one it is right now using a switch:
                        switch (jsonReader.nextName()){
                            case "name": {
                                // If the current parameter is named "name", add the value of
                                // the parameter to the object using the .setName()-Method.
                                // You can get the value of the current parameter using the .nextString()-Method
                                // (as long as the expected Value is a String.
                                // If it's an int or a boolean, use .nextInt() / .nextBoolean())
                                currentPerson.setName(jsonReader.nextString());
                                break;
                            } case "profession":{
                                // do the same with the profession:
                                currentPerson.setProfession(jsonReader.nextString());
                                break;
                            } case "age":{
                                // ...and again with the age. But watch out: the age is an int,
                                // so use .nextInt() instead of .nextString()!
                                currentPerson.setAge(jsonReader.nextInt());
                                break;
                            } default:{
                                // Also add a default case for the case that there are additional
                                // parameters or parameters with misspelled names inside the json object.
                                // If this is the case, just skip the parameter using .skipValue():
                                jsonReader.skipValue();
                                break;
                            }
                        }
                    }
                    // every parameter of the current object (person) has been read out now.
                    // It's time to add our created person to the ArrayList of persons:
                    persons.add(currentPerson);
                    // last character of an object always is "}", so let's call .endObject() acc. to our rules:
                    jsonReader.endObject();
                }
                // after every object has been read out and is ended, we're at the array level again.
                // since the next character is "[", call .endArray():
                jsonReader.endArray();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // now the whole ArrayList of persons is returned to the onPostExecute() Method:
            return persons;
        }

        @Override
        protected void onPostExecute(ArrayList<Person> persons) {
            // This method is called on the UI thread, after doInBackground() has finished.
            // Time to print the values that we extracted from the json script:

            // We use a for loop to cycle through the whole ArrayList of persons and print their data one by one:
            for(int i = 0; i<persons.size(); i++){
                tv1.append("Name: " + persons.get(i).getName() + "\n");
                tv1.append("Profession: " + persons.get(i).getProfession() + "\n");
                tv1.append("Age: " + persons.get(i).getAge() + "\n");
                tv1.append("\n");
            }
            super.onPostExecute(persons);
        }
    }

}
