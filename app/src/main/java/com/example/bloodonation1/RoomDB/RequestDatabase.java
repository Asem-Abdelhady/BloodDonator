package com.example.bloodonation1.RoomDB;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.bloodonation1.Interfaces.RequestDAO;
import com.example.bloodonation1.Models.LocalRequestModel;

@Database(entities = {LocalRequestModel.class}, version = 1, exportSchema = false)
public abstract class RequestDatabase extends RoomDatabase {

    private static RequestDatabase instance;

    public abstract RequestDAO requestDAO();

    public static synchronized RequestDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    RequestDatabase.class, "request_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
}
