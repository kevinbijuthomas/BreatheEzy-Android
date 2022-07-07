package com.kevin.breatheezy.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LungFunctionDAO {
    @Query("SELECT * FROM LungFunction ORDER BY ID")
    List<LungFunctionEntity> loadAllLungFunction();

    @Insert
    void insertLungFunction(LungFunctionEntity lungFunctionEntity);

    @Update
    void updateLungFunction(LungFunctionEntity lungFunctionEntity);

    @Delete
    void deleteRecord(LungFunctionEntity lungFunctionEntity);

    @Query("SELECT * FROM LungFunction WHERE ID = :lungFunctionID")
    LungFunctionEntity getLungFunctionByID(int lungFunctionID);

    @Query("SELECT * FROM LungFunction WHERE savedDate BETWEEN :startDate AND :endDate")
    List<LungFunctionEntity> loadAllLungFunctionByDate(Long startDate, Long endDate);
}
