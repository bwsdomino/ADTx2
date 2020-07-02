package com.example.adtx2;

public class Obj_Model {
    private int id;
    private String name;
    private int size;
    private String Items;

    public Obj_Model(int id, String name, int size, String items) {
        this.id = id;
        this.name = name;
        this.size = size;
        Items = items;
    }

    @Override
    public String toString() {
        return "Obj_Model{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", size=" + size +
                ", Items='" + Items + '\'' +
                '}';
    }

    public int getId() {
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getItems() {
        return Items;
    }

    public void setItems(String items) {
        Items = items;
    }
}
