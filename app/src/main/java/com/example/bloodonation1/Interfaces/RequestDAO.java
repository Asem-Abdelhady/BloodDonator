package com.example.bloodonation1.Interfaces;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.bloodonation1.Models.LocalRequestModel;

import java.util.List;

@Dao
public interface RequestDAO {

    @Insert
    void insert(LocalRequestModel model);

    @Update
    void update(LocalRequestModel model);

    @Delete
    void delete(LocalRequestModel model);

    @Query("DELETE FROM LocalRequestModel")
    void deleteAllRequestes();

    @Query("SELECT * FROM LocalRequestModel ")
    LiveData<List<LocalRequestModel>> getAllRequestes();
}