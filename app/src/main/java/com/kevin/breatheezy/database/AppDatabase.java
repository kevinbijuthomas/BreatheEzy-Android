package com.kevin.breatheezy.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {LungFunctionEntity.class, DiaryEntity.class, TreatmentEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "BreatheEzyDB";
    private static final Object LOCK = new Object();
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context){
        if(sInstance == null){
            synchronized (LOCK){
                sInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME).build();
            }
        }
        return sInstance;
    }

    public abstract LungFunctionDAO lungFunctionDAO();
    public  abstract DiaryDAO diaryDAO();
    public abstract TreatmentDAO treatmentDAO();
}
