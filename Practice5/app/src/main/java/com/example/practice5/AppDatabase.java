package com.example.practice5;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities={Person.class},version=1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract PersonDAO personDAO();

}
