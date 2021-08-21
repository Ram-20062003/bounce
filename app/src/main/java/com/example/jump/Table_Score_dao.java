package com.example.jump;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface Table_Score_dao {
    @Insert
    void insert_row(Table_Score table_score);
    @Query("SELECT * FROM leaderboard ORDER BY score DESC")
    List<Table_Score> get_row();
}
