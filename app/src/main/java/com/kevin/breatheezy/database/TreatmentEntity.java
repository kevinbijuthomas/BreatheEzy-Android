package com.kevin.breatheezy.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Treatment")
public class TreatmentEntity {
    @PrimaryKey(autoGenerate = true)
    int id;
    String name;
    int puffs;
    int times;

    @Ignore
    public TreatmentEntity(String name, int puffs, int times) {
        this.name = name;
        this.puffs = puffs;
        this.times = times;
    }

    public TreatmentEntity(int id, String name, int puffs, int times) {
        this.id = id;
        this.name = name;
        this.puffs = puffs;
        this.times = times;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {return  name;}

    public void setName(String name) {this.name = name;}

    public int getPuffs() {
        return puffs;
    }

    public void setPuffs(int puffs) {
        this.puffs = puffs;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

}

