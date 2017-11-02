package com.derandroidpro.example.jsonreaderexample_android;

public class Person {

    /*
    * This class is just a container for our persons. For every person we create an object
    * of this class that stores name, profession and age.
    * */

    // Variales:
    private String name = "";
    private String profession = "";
    private int age = -1;

    // getter and setter method for every variable:
    // To keep your life simple: right-click the variable > Generate... > Getter and Setter ;)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

