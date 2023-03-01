package com.example.mykerja;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.mykerja.adapter.SessionManagement;
import com.example.mykerja.jobList.JobListDatabase;
import com.google.android.material.navigation.NavigationView;

public class JobActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView job_id, job_position, job_place, job_salary, job_location, line1, line2, line3;
    String id, position, place, salary, location, jobDesc1, jobDesc2, jobDesc3;
    TextView userEmail;
    View mHeaderView;

    JobListDatabase jobDB;

    Button applyJob;

    private JobListDatabase jobData;

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_activity);

        job_position = findViewById(R.id.jobPositionView);
        job_place = findViewById(R.id.JobPlaceView);
        job_salary = findViewById(R.id.JobSalaryView);
        job_location = findViewById(R.id.JobLocationView);
        line1 = findViewById(R.id.jobDescriptionTxt);
        line2 = findViewById(R.id.jobDescription2Txt);
        line3 = findViewById(R.id.jobDescription3Txt);

        applyJob = findViewById(R.id.button);

        jobData = new JobListDatabase(this);

        //open applied job activity
        applyJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobListDatabase jobDB = new JobListDatabase(JobActivity.this);
                jobDB.getJobList();

                Intent i = new Intent(JobActivity.this, JobApplied.class);
                startActivity(i);
            }
        });

        getAndSetIntentData();

        /*This code below is to show navigation bar in this activity  */

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

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("position") && getIntent().hasExtra("place") &&
                getIntent().hasExtra("salary") && getIntent().hasExtra("location") && getIntent().hasExtra("line1") &&
                getIntent().hasExtra("line2") && getIntent().hasExtra("line3")){


            //getting data from intent
            id = getIntent().getStringExtra("id");
            position = getIntent().getStringExtra("position");
            place = getIntent().getStringExtra("place");
            salary = getIntent().getStringExtra("salary");
            location = getIntent().getStringExtra("location");
            jobDesc1 = getIntent().getStringExtra("line1");
            jobDesc2 = getIntent().getStringExtra("line2");
            jobDesc3 = getIntent().getStringExtra("line3");

            //setting intent data
            job_position.setText(position);
            job_place.setText(place);
            job_salary.setText(salary);
            job_location.setText(location);
            line1.setText(jobDesc1);
            line2.setText(jobDesc2);
            line3.setText(jobDesc3);


        }
        else{
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_addJob) {
            Intent intent = new Intent(JobActivity.this, AddActivity.class);
            startActivity(intent);
        }
        if (id == R.id.nav_home) {
            Intent intent = new Intent(JobActivity.this, MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.nav_myJob) {
            Intent intent = new Intent(JobActivity.this, MyJobList.class);
            startActivity(intent);
        }
        if (id == R.id.nav_myAccount) {
            Intent intent = new Intent(JobActivity.this, MyAccount.class);
            startActivity(intent);
        }
        if (id == R.id.nav_SignOut) {

            SessionManagement sessionManagement = new SessionManagement(JobActivity.this);
            sessionManagement.removeSession();
            Intent intent = new Intent(JobActivity.this, App_Start.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displayListView(){
        Cursor cursor = jobDB.getJobList();

        //the desired columns to be bound
        String[] columns = new String[] {JobListApplied.ID, JobListApplied.Job_position, JobListApplied.Company_name, JobListApplied.Job_salary, JobListApplied.Job_location};

        //the xml defined views which data will be bound to
        //int[] to = new int[] {}
    }

    public class JobListApplied {
        //for display after search

        public static final String ID = "_id";
        public static final String Job_position = "job_position";
        public static final String Company_name = "company_name";
        public static final String Job_salary = "job_salary";
        public static final String Job_location = "job_location";
        public static final String TAG = "ApplyJobdbAdapter";
        //private DatabaseHelper mDPHelper;
        private SQLiteDatabase mDB;

        public static final String DATABASE_NAME = "Job";
        public static final String SQLITE_TABLE = "JobList";
        public static final int DATABASE_VERSION = 1;

        private Context context;

    /*public ApplyJobdbAdapter(Context context){
        this.context = mContext;
        mDPHelper = new DatabaseHelper(mContext);
    }

     */

    /*public Cursor RetrieveJobId(String jobId) throws SQLException{
        Cursor cursor = null;
        if(jobId == null || jobId.length() == 0 ){
            cursor = mDB.query(SQLITE_TABLE, new String[] { Job_position, Company_name, Job_salary, Job_location }, null, null, null, null);
        }
        else{
            cursor = mDB.query(true, SQLITE_TABLE, new String[] { Job_position, Company_name, Job_salary, Job_location })
        }
    }

     */

        public Cursor retrieveAllList(){
            Cursor cursor = mDB.query(SQLITE_TABLE, new String[] { ID, Job_position, Company_name, Job_salary, Job_location }, null, null, null, null, null);

            if(cursor != null) {
                cursor.moveToFirst();
            }
            return cursor;
        }

    }//close ApplyJobList class
}
