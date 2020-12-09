package com.example.bloodonation1.Presesntation.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.bloodonation1.Models.LocalRequestModel;
import com.example.bloodonation1.Repository.DBRepositories;

import java.util.List;

public class RequestViewModel extends AndroidViewModel {
    private DBRepositories repository;
    private LiveData<List<LocalRequestModel>> allRequests;

    public RequestViewModel(@NonNull Application application) {
        super(application);
        repository = new DBRepositories(application);
        updateRequests();
    }

    private void updateRequests () {
        allRequests = repository.getAllRequestes();
    }

    public void insert(LocalRequestModel model) {
        repository.insert(model);
        updateRequests();
    }

    public void delete(LocalRequestModel model) {
        repository.delete(model);
        updateRequests();
    }

    public void deleteAllRequestes () {
        repository.deleteAllRequestes();
        updateRequests();
    }

    public LiveData<List<LocalRequestModel>> getAllRequestss() {
        return allRequests;
    }
}