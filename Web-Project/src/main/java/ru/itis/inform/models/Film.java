package ru.itis.inform.models;

import java.text.SimpleDateFormat;
import java.sql.Date;

/**
 * Created by Тимур on 03.10.2016.
 */
//Модель фильма
public class Film {

    private String name;
    private int producer;
    private int studio;
    private int id;
    private Date date;
    private String description;
    private int remark;
    private String imageURL = "";

    public Film(int id, String name, int producer, int studio,  String description, int remark, Date date) {
        this.name = name;
        this.producer = producer;
        this.studio = studio;
        this.id = id;
        this.description = description;
        this.remark = remark;
        this.date = date;
    }

    public Film(int id, String name, int producer, int studio,  String description, int remark, Date date, String imageURL) {
        this.name = name;
        this.producer = producer;
        this.studio = studio;
        this.id = id;
        this.description = description;
        this.remark = remark;
        this.date = date;
        this.imageURL = imageURL;
    }

    public Film(String name, int producer, int studio,  String description, int remark) {
        this.name = name;
        this.producer = producer;
        this.studio = studio;
        this.description = description;
        this.remark = remark;
        this.date = date;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageURL() {

        return imageURL;
    }

    public Film(String name, int producer, int studio, String description, int remark, String imageURL) {
        this.name = name;
        this.producer = producer;
        this.studio = studio;
        this.description = description;
        this.remark = remark;
        this.imageURL = imageURL;

    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDate(long timestamp){
        date = new Date(timestamp);
    };

    public String getName() {
        return name;
    }

    public int getProducer() {
        return producer;
    }

    public int getStudio() {
        return studio;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public int getRemark() {
        return remark;
    }
}
