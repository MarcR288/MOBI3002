package com.codelab.basics;

// Object DB ... see Room for Android Studio
// https://developer.android.com/training/data-storage/room
public class DataModel {

    private int id;
    private String name;
    private String type;
    private int accessCount;

    public DataModel() {
        this.setId(0);
        this.setName("Default Name");
        this.setType("Default Type");
    }

    public DataModel(int id, String name, String type, int accessCount) {
        this.setId(id);
        this.setName(name);
        this.setType(type);
        this.setAccessCount(accessCount);
    }

    @Override
    public String toString() {
        return
                "ID= " + getId() + '\'' +
                ", name= " + getName() + '\'' +
                ", type= " + getType() + '\'' +
                ", access count= " + getAccessCount();
    }

    public void setAccessCount() {
        this.accessCount = accessCount;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAccessCount() {
        return accessCount;
    }

    public void setAccessCount(int accessCount) {
        this.accessCount = accessCount;
    }
}
