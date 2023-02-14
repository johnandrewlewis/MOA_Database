package com.example.android.orienteering.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "ormeau")
public class CoursesEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "Marker #")
    private int marker_id;
    @ColumnInfo(name = "Latitude")
    private double marker_lat;
    @ColumnInfo(name = "Longitude")
    private double marker_long;
    @ColumnInfo(name = "Description")
    private String description;
    @ColumnInfo(name = "Picture")
    private int picture;
    @ColumnInfo(name = "updated_at")
    private Date updatedAt;

    @Ignore
    public CoursesEntry(int marker_id, String description, double marker_lat, double marker_long,
                        int picture, Date updatedAt) {
        this.marker_id = marker_id;
        this.marker_lat = marker_lat;
        this.marker_long = marker_long;
        this.description = description;
        this.picture = picture;
        this.updatedAt = updatedAt;
    }

    public CoursesEntry(int id, int marker_id, String description, double marker_lat, double marker_long,
                        int picture, Date updatedAt) {
        this.id = id;
        this.marker_id = marker_id;
        this.marker_lat = marker_lat;
        this.marker_long = marker_long;
        this.description = description;
        this.picture = picture;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMarker_id() {
        return marker_id;
    }

    public void setMarker_id(int marker_id) {this.marker_id = marker_id;}

    public double getMarker_lat() {
        return marker_lat;
    }

    public void setMarker_lat(double marker_lat) {
        this.marker_lat = marker_lat;
    }

    public double getMarker_long() {
        return marker_long;
    }

    public void setMarker_long(double marker_long) {
        this.marker_long = marker_long;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}