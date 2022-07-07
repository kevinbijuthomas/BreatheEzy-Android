package com.kevin.breatheezy.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "Diary")
public class DiaryEntity {
    @PrimaryKey(autoGenerate = true)
    int id;
    int color;
    @TypeConverters({Converters.class})
    Date savedDate;

    @Ignore
    public DiaryEntity(int color, Date savedDate) {
        this.color = color;
        this.savedDate = savedDate;
    }

    public DiaryEntity(int id, int color, Date savedDate) {
        this.id = id;
        this.color = color;
        this.savedDate = savedDate;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getSavedDate()  {return savedDate;}

    public void setSavedDate(Date savedDate) {this.savedDate = savedDate;}
}

