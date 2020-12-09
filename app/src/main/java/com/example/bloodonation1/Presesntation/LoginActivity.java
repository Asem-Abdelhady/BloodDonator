package com.example.bloodonation1.Presesntation;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bloodonation1.Presesntation.ViewModel.LoginViewModel;
import com.example.bloodonation1.R;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class LoginActivity extends AppCompatActivity {

    // ui
    private EditText passwordText, emailText;
    private TextView registerText;

    // data
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);

        emailText = findViewById(R.id.LoginEmail);
        passwordText = findViewById(R.id.LoginPassword);

        registerText = findViewById(R.id.go_to_register);

        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        if (viewModel.isAuthorized()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }
        goToRegister();

    }


    private void goToRegister(){
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignupActivity.class));
                finish();
            }
        });
    }

    public void onLoginClick (View view) {

        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            if (TextUtils.isEmpty(email)) emailText.setError("You should provide an email address");
            if (TextUtils.isEmpty(password)) emailText.setError("You should provide a password");
            return;
        }


        viewModel.Login(email, password).subscribe(new SingleObserver<FirebaseUser>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(FirebaseUser firebaseUser) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Toast.makeText(LoginActivity.this, "Welcome Home", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void onImgClick (View view) {
        startActivity(new Intent(this, SignupActivity.class));
    }
}
