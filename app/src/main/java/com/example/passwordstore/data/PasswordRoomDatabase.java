package com.example.passwordstore.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.passwordstore.model.Password;

@Database(entities = {Password.class},version = 1,exportSchema = false)
public abstract class PasswordRoomDatabase extends RoomDatabase {
    public static volatile PasswordRoomDatabase INSTANCE;
    public abstract PasswordDao passwordDao();

    public static PasswordRoomDatabase getDatabase(Context context){
        if (INSTANCE==null){
            synchronized (PasswordRoomDatabase.class){
                if (INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PasswordRoomDatabase.class,"password_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
