package com.example.misterburger.testtusk.model;

import io.realm.RealmObject;

/**
 * Created by Burge on 06.02.2018.
 */

public class Source extends RealmObject {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}