package com.example.bloodonation1.Presesntation.ViewModel;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.bloodonation1.Repository.AuthRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class SignupViewModel extends ViewModel {
    AuthRepository authRepository = new AuthRepository();

    public Observable<FirebaseUser> Signup(final String email, final String password){
        return Observable.create(new ObservableOnSubscribe<FirebaseUser>() {
            @Override
            public void subscribe(final ObservableEmitter<FirebaseUser> emitter) throws Exception {
                authRepository.Signup(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) emitter.onNext(task.getResult().getUser());
                        else emitter.onError(task.getException());
                        emitter.onComplete();
                    }
                });
            }
        });
    }

}
