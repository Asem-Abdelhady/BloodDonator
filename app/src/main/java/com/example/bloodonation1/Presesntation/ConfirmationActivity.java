package com.example.bloodonation1.Presesntation;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bloodonation1.Checker.Checker;
import com.example.bloodonation1.Models.LocalRequestModel;
import com.example.bloodonation1.Models.RemoteRequestModel;
import com.example.bloodonation1.Presesntation.ViewModel.MapViewModel;
import com.example.bloodonation1.Presesntation.ViewModel.RequestViewModel;
import com.example.bloodonation1.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ConfirmationActivity extends AppCompatActivity {

    private static final String Tag = "ConfirmationActivity";
    private static final int Error_Dialog_Request = 9001;
    private EditText confirmAge;
    private EditText confirmPhone;
    private EditText confirmName;
    private EditText confirmBloodType;
    RequestViewModel requestViewModel;
    MapViewModel mapViewModel;

    Button Add;
    Spinner BloodType;
    Spinner age;

    String BloodTypeChosen;
    String AgeChosen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        Add = findViewById(R.id.add_button);
        BloodType = findViewById(R.id.blood_spinner);
        age = findViewById(R.id.age_spinner);


        fill_spinners();
        SpinnerChosen();


        requestViewModel = ViewModelProviders.of(this).get(RequestViewModel.class);
        mapViewModel = ViewModelProviders.of(this).get(MapViewModel.class);


        confirmName = findViewById(R.id.confirm_name);
        confirmPhone = findViewById(R.id.confirm_phone);

        if (isServiceOK()) {
            init();
        }
    }


    private void init() {
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRequest();
            }
        });

    }


    public void addRequest(){
        String phone = confirmPhone.getText().toString();
        String name = confirmName.getText().toString();


        if ( AgeChosen == ""){
            Toast.makeText(this, "Choose fucking age", Toast.LENGTH_SHORT).show();
            return;
        }

        if ( phone.isEmpty()){
            confirmPhone.setError("Fill the fucking field  !!");
            return;
        }
        if ( name.isEmpty()){
            confirmName.setError("Fill the fucking field  !!");
            return;
        }
        if ( BloodTypeChosen == ""){
            Toast.makeText(this, "Choose fucking BloodType", Toast.LENGTH_SHORT).show();
            return;
        }



            if (isServiceOK()){
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                LocalRequestModel request = new LocalRequestModel(BloodTypeChosen,name,AgeChosen,phone, date);
                RemoteRequestModel requestModel = new RemoteRequestModel(BloodTypeChosen, name, AgeChosen, phone);

                requestViewModel.insert(request);
                mapViewModel.addMarker(requestModel);
                Toast.makeText(this, date, Toast.LENGTH_SHORT).show();

                Toast.makeText(this, "request saved", Toast.LENGTH_SHORT).show();
                Toast.makeText(this,String.valueOf(request.getId()), Toast.LENGTH_SHORT).show();
                Checker.checker = false;
                startActivity(new Intent(getApplicationContext(), MapActivity.class));
                finish();
            }


    }


    public boolean isServiceOK() {
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(ConfirmationActivity.this);
        if (available == ConnectionResult.SUCCESS) {
            Log.d(Tag, "Everything is okay and connected with the services");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(ConfirmationActivity.this, available, Error_Dialog_Request);
            dialog.show();
        } else {
            Toast.makeText(this, "You can't make the request", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    private void fill_spinners(){
        List<String> BloodList = new ArrayList<>();

        BloodList.add("O-");
        BloodList.add("O+");
        BloodList.add("B-");
        BloodList.add("B+");
        BloodList.add("A-");
        BloodList.add("A+");
        BloodList.add("AB-");
        BloodList.add("AB+");

        ArrayAdapter<String> BloodAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, BloodList);
        BloodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        BloodType.setPrompt("Choose the blood type !");
        BloodType.setAdapter(BloodAdapter);


        List<String> AgeList = new ArrayList<>();
        for (int i = 1; i <= 100; i++){
            AgeList.add(String.valueOf(i));
        }

        ArrayAdapter<String> AgeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, AgeList);
        AgeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        age.setAdapter(AgeAdapter);
        age.setPrompt("Choose the patient's age");

    }

    private void SpinnerChosen(){

        BloodType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                BloodTypeChosen = String.valueOf(adapterView.getSelectedItem());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                BloodTypeChosen = "";
            }
        });

        age.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                AgeChosen = String.valueOf(adapterView.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                AgeChosen = "";

            }
        });

    }
}