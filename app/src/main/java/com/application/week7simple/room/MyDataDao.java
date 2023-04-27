package com.application.week7simple.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MyDataDao {

    @Insert
    void insert(MyData myData);

//    @Query("SELECT * FROM mytable WHERE id = :id")
//    MyData getById(int id);

    @Update
    void update(MyData myData);

    @Delete
    void delete(MyData myData);

    @Query("SELECT * FROM mytable")
    List<MyData> getAllData();
}
