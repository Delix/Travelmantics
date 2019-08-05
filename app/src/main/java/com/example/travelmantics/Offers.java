package com.example.travelmantics;

import java.io.Serializable;

public class Offers
{
    private String id;
    private String Title;
    private String Description;
    private String Price;
    private String Imageurl;


    public Offers()
    {

    }


    public Offers(String id, String title, String description, String price, String imageurl) {
        this.id = id;
        Title = title;
        Description = description;
        Price = price;
        Imageurl = imageurl;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getImageurl() {
        return Imageurl;
    }

    public void setImageurl(String imageurl) {
        Imageurl = imageurl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
