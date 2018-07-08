package com.barberia.upc.models;

public class HairCut {
    private String name;
    private String description;
    private String picture;
    private int rank;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public HairCut(String name, String description, String picture, int rank) {
        this.name = name;
        this.description = description;
        this.picture = picture;
        this.rank = rank;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
