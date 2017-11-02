# Android: JsonReader Example
This is an example of how to use Android's JsonReader class to read data from a JSON array and convert the JSON array of JSON objects to an ArrayList of Java objects.

The app downloads a JSON script from a server. The JSON-Script contains an array of persons with their name, profession and age. The app decodes this information and saves it as an ArrayList of Java objects of the class "Person". At the end the app displays all the data to the user using a textview (as you can see in the screenshot).

I wrote lots of comments in the code, so it should be easy to understand.

### Important Files:
- [jsonscript_persons.json](/jsonscript_persons.json) (The JSON-file we're reading from)
- [MainActivity.java](/Android_Project/app/src/main/java/com/derandroidpro/example/jsonreaderexample_android/MainActivity.java) (This is where all the work happens)
- [Person.java](/Android_Project/app/src/main/java/com/derandroidpro/example/jsonreaderexample_android/Person.java) (A data class to create an object for each person)
- [AndroidManifest.xml](/Android_Project/app/src/main/AndroidManifest.xml) (Don't forget the permissions in the manifest!)
- [activity_main.xml](/Android_Project/app/src/main/res/layout/activity_main.xml) (The layout of the app)

### The JSON-Script:
```json
{
  "user":[
    {
      "name":"John Doe",
      "profession":"Doctor",
      "age":27
    },
    {
      "name":"Mike Miller",
      "profession":"Musician",
      "age":40
    },
    {
      "name":"Bob Graham",
      "profession":"Programmer",
      "age":32
    }
  ]
}
```

### The Result:

<img src="/Screenshot1.png" height="550px" />

