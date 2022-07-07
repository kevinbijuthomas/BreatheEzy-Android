package com.kevin.breatheezy.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DiaryDAO {
    @Query("SELECT * FROM Diary ORDER BY ID")
    List<DiaryEntity> loadAllDiary();

    @Insert
    void insertDiary(DiaryEntity diaryEntity);

    @Update
    void updateDiary(DiaryEntity diaryEntity);

    @Delete
    void deleteRecord(DiaryEntity diaryEntity);

    @Query("SELECT * FROM Diary WHERE ID = :diaryEntityID")
    DiaryEntity getDiaryByID(int diaryEntityID);

    @Query("SELECT * FROM Diary WHERE savedDate BETWEEN :startDate AND :endDate")
    List<DiaryEntity> loadAllDiaryByDate(Long startDate, Long endDate);
}
