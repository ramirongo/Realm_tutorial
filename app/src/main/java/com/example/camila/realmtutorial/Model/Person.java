package com.example.camila.realmtutorial.Model;

import io.realm.RealmObject;

/**
 * Created by Camila on 08-01-2018.
 */

public class Person extends RealmObject{
    private String name;
    private int age;

    public Person(){}

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
