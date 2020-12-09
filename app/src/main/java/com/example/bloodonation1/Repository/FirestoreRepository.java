package com.example.bloodonation1.Repository;

import com.example.bloodonation1.Models.RemoteRequestModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

public class FirestoreRepository {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ListenerRegistration subscription;

    private static final String REQUESTS_COLLECTION = "Markers";

    public Task<Void> addMarkers(RemoteRequestModel model){
        return db.collection(REQUESTS_COLLECTION).document().set(model);
    }

    // get blood requests from DB
    public Task<QuerySnapshot> getBloodRequests () {
        return db.collection(REQUESTS_COLLECTION).get();
    }

    // listen for changes
    public void listenForChanges (EventListener<QuerySnapshot> listener) {
        subscription = db.collection(REQUESTS_COLLECTION).addSnapshotListener(listener);
    }

    // unsubscribe from the observable
    public void removeListener () {
        if (subscription != null) subscription.remove();
    }
}
