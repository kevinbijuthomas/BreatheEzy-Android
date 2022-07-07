package com.kevin.breatheezy.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "LungFunction")
public class LungFunctionEntity {
    @PrimaryKey(autoGenerate = true)
    int id;
    int PEFR;
    @TypeConverters({Converters.class})
    Date savedDate;

    @Ignore
    public LungFunctionEntity(int PEFR, Date savedDate) {
        this.PEFR = PEFR;
        this.savedDate = savedDate;
    }

    public LungFunctionEntity(int id, int PEFR, Date savedDate) {
        this.id = id;
        this.PEFR = PEFR;
        this.savedDate = savedDate;
    }

    public int getPEFR() {
        return PEFR;
    }

    public void setPEFR(int PEFR) {
        this.PEFR = PEFR;
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

