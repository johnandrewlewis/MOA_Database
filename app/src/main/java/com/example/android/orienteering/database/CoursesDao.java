package com.example.android.orienteering.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CoursesDao {

    @Query("SELECT * FROM ormeau ORDER BY id")
    LiveData<List<CoursesEntry>> loadOrmeau();

    @Insert
    void insert(CoursesEntry coursesEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(CoursesEntry coursesEntry);

    @Delete
    void delete(CoursesEntry coursesEntry);

    @Query("SELECT * FROM ormeau WHERE id = :id")
    LiveData<CoursesEntry> loadCourseById(int id);
}
