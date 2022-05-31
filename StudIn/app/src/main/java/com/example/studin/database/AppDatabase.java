package com.example.studin.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities={EventTable.class},version=1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract EventDAO eventDAO();

}
