package com.example.bloodonation1.Presesntation;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bloodonation1.Presesntation.ViewModel.SignupViewModel;
import com.example.bloodonation1.R;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        changeStatusBarColor();

        Button SignUp = findViewById(R.id.SignUp);

        final EditText Email = findViewById(R.id.editTextEmail);
        final EditText Mobile = findViewById(R.id.editTextMobile);
        final EditText Password = findViewById(R.id.editTextPassword);
        final EditText Name = findViewById(R.id.editTextName);

        final SignupViewModel signupViewmodel= ViewModelProviders.of(this).get(SignupViewModel.class);


        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Password.getText().toString().equals("") && Email.getText().toString().equals("") && Mobile.getText().toString().equals("") && Name.getText().toString().equals(""))
                    Toast.makeText(SignupActivity.this, "Please fill all the fields !!", Toast.LENGTH_SHORT).show();

                else if(Email.getText().toString().equals("")){
                    Email.setError("Please enter your email address");
                    Email.requestFocus();
                }

                else if (Mobile.getText().toString().equals("")){
                    Mobile.setError("Please enter your mobile number !");
                    Mobile.requestFocus();
                }

                else if (Password.getText().toString().equals("")){
                    Password.setError("Please enter your password");
                    Password.requestFocus();
                }

                else if (Password.getText().toString().length() < 6){
                    Password.setError("Please enter more than 6 digits");
                    Password.requestFocus();
                }

                else if(!(Password.getText().toString().equals("") && Email.getText().toString().equals("") && Mobile.getText().toString().equals("")&& Name.getText().toString().equals(""))){
                    signupViewmodel.Signup(Email.getText().toString(), Password.getText().toString()).subscribe(new Observer<FirebaseUser>() {
                        @Override
                        public void onComplete() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(FirebaseUser firebaseUser) {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                    });
                }


            }
        });
    }
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    public void onLoginClick (View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

}