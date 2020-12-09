package com.example.bloodonation1.Presesntation.ViewModel;

import android.arch.lifecycle.ViewModel;

import com.example.bloodonation1.Repository.AuthRepository;

public class MainActivityViewModel extends ViewModel {

    AuthRepository authRepository = new AuthRepository();

    public void Logout(){
        authRepository.Logout();
    }
}
