# Android: JsonReader Example
This is an example of how to use Android's JsonReader class to read data from a JSON array and convert the JSON array of JSON objects to an ArrayList of Java objects.

I wrote lots of comments in the code, so it should be easy to understand.

### Important Files:
- [jsonscript_persons.json](/jsonscript_persons.json) (The JSON-File we're reading from)
- [MainActivity.java](/Android_Project/app/src/main/java/com/derandroidpro/example/jsonreaderexample_android/MainActivity.java) (This is where all the work happens)
- [Person.java](/Android_Project/app/src/main/java/com/derandroidpro/example/jsonreaderexample_android/Person.java) (A data class to create an object for each person)
- [AndroidManifest.xml](/Android_Project/app/src/main/AndroidManifest.xml) (Don't forget the permissions in the manifest!)
- [activity_main.xml](/Android_Project/app/src/main/res/layout/activity_main.xml) (The layout of the app)
