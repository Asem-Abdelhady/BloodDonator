package com.example.bloodonation1.Repository;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class AuthRepository {


    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public Task<AuthResult> Signup (String email, String password){
        return firebaseAuth.createUserWithEmailAndPassword(email,password);
    }


    public Task<AuthResult> Signin(String email, String password){
        return firebaseAuth.signInWithEmailAndPassword(email, password);
    }

    public boolean isAuthorized () {
        return firebaseAuth.getCurrentUser() != null;
    }
}
