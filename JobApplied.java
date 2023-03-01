package com.example.mykerja;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mykerja.adapter.SessionManagement;
import com.google.android.material.navigation.NavigationView;

public class JobApplied extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    TextView userEmail;
    View mHeaderView;

    Button discoverButton;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_applied);

        /*This code below is to show navigation bar in this activity  */

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //discover button to search more jobs
        discoverButton = findViewById(R.id.discover_jobs);
        discoverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(JobApplied.this, MainActivity.class);
                startActivity(i);
            }
        });

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_addJob) {
            Intent intent = new Intent(JobApplied.this, AddActivity.class);
            startActivity(intent);
        }
        if (id == R.id.nav_home) {
            Intent intent = new Intent(JobApplied.this, MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.nav_myJob) {
            Intent intent = new Intent(JobApplied.this, MyJobList.class);
            startActivity(intent);
        }

        if (id == R.id.nav_myAccount) {
            Intent intent = new Intent(JobApplied.this, MyAccount.class);
            startActivity(intent);
        }
        if (id == R.id.nav_SignOut) {

            SessionManagement sessionManagement = new SessionManagement(JobApplied.this);
            sessionManagement.removeSession();
            Intent intent = new Intent(JobApplied.this, App_Start.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}