package com.example.mykerja;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mykerja.adapter.JobAdapter;
import com.example.mykerja.adapter.SessionManagement;
import com.example.mykerja.jobList.JobListDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MyJobList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    RecyclerView recyclerView;
    FloatingActionButton add_button;

    JobListDatabase jobDB;
    ArrayList<String> job_id, job_position, company_name, salary, job_location, jobDesc1, jobDesc2, jobDesc3;

    JobAdapter jobAdapter;

    TextView userEmail;
    View mHeaderView;

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navBar();


        recyclerView = findViewById(R.id.job_list_recyclerView);
        /*add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        }));

         */

        jobDB = new JobListDatabase(MyJobList.this);
        job_id = new ArrayList<>();
        job_position = new ArrayList<>();
        company_name = new ArrayList<>();
        salary = new ArrayList<>();
        job_location = new ArrayList<>();
        jobDesc1 = new ArrayList<>();
        jobDesc2 = new ArrayList<>();
        jobDesc3 = new ArrayList<>();

        //storeDataInArrays();

        jobAdapter = new JobAdapter(MyJobList.this, this, job_id, job_position, company_name, salary, job_location, jobDesc1, jobDesc2, jobDesc3);
        recyclerView.setAdapter(jobAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyJobList.this));

    }

    public void navBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mHeaderView = navigationView.getHeaderView(0);
        userEmail = mHeaderView.findViewById(R.id.show_email);

        //To set user email in navigation drawer.
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        String email = preferences.getString(getString(R.string.email), "");
        userEmail.setText(email);

        //Change Hamburger Icon on nav drawer to red by code
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.red));
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    /*public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main, container, false);
    }*/

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_addJob) {
            Intent intent = new Intent(MyJobList.this, AddActivity.class);
            startActivity(intent);
        }
        if (id == R.id.nav_home) {
            Intent intent = new Intent(MyJobList.this, MainActivity.class);
            startActivity(intent);
        }

        if (id == R.id.nav_myJob) {
            Intent intent = new Intent(MyJobList.this, MyJobList.class);
            startActivity(intent);
        }

        if (id == R.id.nav_myAccount) {
            Intent intent = new Intent(MyJobList.this, MyAccount.class);
            startActivity(intent);
        }
        if (id == R.id.nav_SignOut) {

            SessionManagement sessionManagement = new SessionManagement(MyJobList.this);
            sessionManagement.removeSession();
            moveToLogin();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void moveToLogin() {
        Intent intent = new Intent(MyJobList.this, App_Start.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*void storeDataInArrays() {
        Cursor cursor = jobDB.getJobList();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                job_id.add(cursor.getString(0));
                job_position.add(cursor.getString(1));
                company_name.add(cursor.getString(2));
                salary.add(cursor.getString(3));
                job_location.add(cursor.getString(4));
                jobDesc1.add(cursor.getString(5));
                jobDesc2.add(cursor.getString(6));
                jobDesc3.add(cursor.getString(7));
            }
        }
    }

     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }



}//close class

