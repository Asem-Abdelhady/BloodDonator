package com.example.bloodonation1.Presesntation;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bloodonation1.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {

    private static final String Tag = "MainActivity";
    private static final int Error_Dialog_Request = 9001;
    Button Add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Add = findViewById(R.id.add_button);

        if(isServiceOK()){
            init();
        }
    }








    private void init(){
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MapActivity.class));
            }
        });

    }



    public boolean isServiceOK () {
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);
        if (available == ConnectionResult.SUCCESS) {
            Log.d(Tag, "Everything is okay and connected with the services");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, Error_Dialog_Request);
            dialog.show();
        } else {
            Toast.makeText(this, "You can't make the request", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

}
