package com.kevin.breatheezy.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TreatmentDAO {
    @Query("SELECT * FROM Treatment ORDER BY ID")
    List<TreatmentEntity> loadAllTreatment();

    @Insert
    void insertTreatment(TreatmentEntity treatmentEntity);

    @Update
    void updateTreatment(TreatmentEntity treatmentEntity);

    @Delete
    void deleteRecord(TreatmentEntity treatmentEntity);

    @Query("SELECT * FROM Treatment WHERE ID = :treatmentEntityID")
    TreatmentEntity getTreatmentByID(int treatmentEntityID);

}
