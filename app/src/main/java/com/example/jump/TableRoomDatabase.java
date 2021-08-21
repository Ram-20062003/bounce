package com.example.jump;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities ={Table_Score.class},version = 1)
public  abstract class TableRoomDatabase extends RoomDatabase {
    public abstract Table_Score_dao table_score_dao();
        private static volatile TableRoomDatabase INSTANCE;
    public static TableRoomDatabase getInstance(Context context)
    {
        if(INSTANCE==null)
        {
            synchronized (TableRoomDatabase.class){
                if(INSTANCE==null)
                {
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),TableRoomDatabase.class,"Table_Database").build();

                }

            }
        }
        return INSTANCE;
    }
}
