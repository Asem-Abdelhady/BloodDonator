package com.example.bloodonation1.Presesntation.ViewModel;

import android.arch.lifecycle.ViewModel;

import com.example.bloodonation1.Models.RemoteRequestModel;
import com.example.bloodonation1.Repository.FirestoreRepository;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.QuerySnapshot;

public class MapViewModel extends ViewModel {

    private FirestoreRepository mFirestoreRepository = new FirestoreRepository();

    public Task addMarker(RemoteRequestModel model) {
       return mFirestoreRepository.addMarkers(model);
    }

    public Task<QuerySnapshot> getBloodRequests () {
        return mFirestoreRepository.getBloodRequests();
    }

    public void listenForChanges (EventListener<QuerySnapshot> listener) {
        mFirestoreRepository.listenForChanges(listener);
    }

    public void removeListener () {
        mFirestoreRepository.removeListener();
    }
}
