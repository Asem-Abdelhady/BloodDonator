package com.example.bloodonation1.Presesntation;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.bloodonation1.Adapters.RequestAdapter;
import com.example.bloodonation1.Checker.Checker;
import com.example.bloodonation1.Models.LocalRequestModel;
import com.example.bloodonation1.Presesntation.ViewModel.MainActivityViewModel;
import com.example.bloodonation1.Presesntation.ViewModel.RequestViewModel;
import com.example.bloodonation1.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_REQUEST = 1;


    RequestViewModel requestViewModel;
    MainActivityViewModel mainActivityViewModel;
    FloatingActionButton Add;
    LinearLayout goToMap;
    LinearLayout Logout;
    LinearLayout About;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Add = findViewById(R.id.add_float_button);
        goToMap = findViewById(R.id.req_map);
        Logout = findViewById(R.id.Logout);
        About= findViewById(R.id.About_us);
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        init();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final RequestAdapter adapter = new RequestAdapter();
        recyclerView.setAdapter(adapter);

        requestViewModel = ViewModelProviders.of(this).get(RequestViewModel.class);
        requestViewModel.getAllRequestss().observe(this, new Observer<List<LocalRequestModel>>() {
            @Override
            public void onChanged(@Nullable List<LocalRequestModel> requests) {
                //update RecyclerView
                Toast.makeText(MainActivity.this, "onChanged", Toast.LENGTH_SHORT).show();
                adapter.setRequestes(requests);

            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                requestViewModel.delete(adapter.getRequestAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Request deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        goToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Checker.checker = true;
                startActivity(new Intent(getApplicationContext(), MapActivity.class));
            }
        });

        About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Checker.checker = true;
                startActivity(new Intent(getApplicationContext(), AboutUsActivity.class));
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityViewModel.Logout();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

    }

    private void init() {
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ConfirmationActivity.class));
            }
        });

    }
}

