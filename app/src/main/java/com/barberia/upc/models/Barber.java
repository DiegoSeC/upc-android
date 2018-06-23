package com.barberia.upc.models;

public class Barber {
    private int id;
    private String name;
    private String picture;
    private int rank;
    private String bio;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Barber(int id, String name, String picture, int rank, String bio) {
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.rank = rank;
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
