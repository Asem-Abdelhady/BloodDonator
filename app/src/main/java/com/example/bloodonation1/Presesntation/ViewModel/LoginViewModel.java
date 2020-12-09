package com.example.bloodonation1.Presesntation.ViewModel;


import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.bloodonation1.Repository.AuthRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class LoginViewModel extends ViewModel {

    AuthRepository authRepository = new AuthRepository();

    public Single<FirebaseUser> Login(final String email, final String password) {
        return Single.create(new SingleOnSubscribe<FirebaseUser>() {
            @Override
            public void subscribe(final SingleEmitter<FirebaseUser> emitter) throws Exception {
                authRepository.Signin(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) emitter.onSuccess(task.getResult().getUser());
                        else emitter.onError((task.getException()));
                    }
                });
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public boolean isAuthorized () {
        return authRepository.isAuthorized();
    }

}
