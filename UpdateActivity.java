package com.example.mykerja;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mykerja.jobList.JobListDatabase;

public class UpdateActivity extends AppCompatActivity {

    EditText job_position, job_place, job_salary, job_location;
    Button update_button, delete_button;

    String id, position, place, salary, location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        job_position = findViewById(R.id.job_position_update);
        job_place = findViewById(R.id.company_name_update);
        job_salary = findViewById(R.id.salary_update);
        job_location = findViewById(R.id.job_location_update);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);
        //first we call this
        getAndSetIntentData();

        //set actionBar title after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        ab.setTitle(position);
        if(ab != null){
            ab.setTitle(position);
        }

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobListDatabase dbUpdate = new JobListDatabase(UpdateActivity.this);
                position = job_position.getText().toString().trim();
                place = job_place.getText().toString().trim();
                salary = job_salary.getText().toString().trim();
                location = job_location.getText().toString().trim();
                //and only then we call this
                dbUpdate.updateData(id, position, place, salary, location);
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("position") && getIntent().hasExtra("place") &&
                getIntent().hasExtra("salary") && getIntent().hasExtra("location")){

            //getting data from intent
            id = getIntent().getStringExtra("id");
            position = getIntent().getStringExtra("position");
            place = getIntent().getStringExtra("place");
            salary = getIntent().getStringExtra("salary");
            location = getIntent().getStringExtra("location");

            //setting intent data
            job_position.setText(position);
            job_place.setText(place);
            job_salary.setText(salary);
            job_location.setText(location);
        }
        else{
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + position + " ?");
        builder.setMessage("Are you sure you want to delete " + position + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                JobListDatabase db = new JobListDatabase(UpdateActivity.this);
                db.deleteOneRow(id);
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}