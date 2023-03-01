package com.example.mykerja;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mykerja.jobList.JobListDatabase;

public class AddActivity extends AppCompatActivity {

    EditText job_position, company_name, salary, job_location, line1, line2, line3;
    Button add_button;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        job_position = findViewById(R.id.job_position);
        company_name = findViewById(R.id.company_name);
        salary = findViewById(R.id.salary);
        job_location = findViewById(R.id.job_location);
        line1 = findViewById(R.id.editTextTextMultiLine);
        line2 = findViewById(R.id.editTextTextMultiLine2);
        line3 = findViewById(R.id.editTextTextMultiLine3);

        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobListDatabase jobDB = new JobListDatabase(AddActivity.this);
                jobDB.addJob(job_position.getText().toString().trim(),
                        company_name.getText().toString().trim(),
                        Integer.parseInt(salary.getText().toString().trim()),
                        job_location.getText().toString().trim(),
                        line1.getText().toString().trim(),
                        line2.getText().toString().trim(),
                        line3.getText().toString().trim());


                /*Intent intent = new Intent(context, AddjobDescription.class);
                intent.putExtra("position", String.valueOf(job_position));
                intent.putExtra("position", String.valueOf(company_name));
                intent.putExtra("position", String.valueOf(salary));
                intent.putExtra("position", String.valueOf(job_location));

                 */

                //getSupportFragmentManager().beginTransaction().replace(R.id.AddJob, new AddjobDescription()).commit();
                /*Bundle bundle = new Bundle();
                bundle.putString("jobData", "From AddActivity");
                Fragment fragment = new Fragment();
                fragment.setArguments(bundle);

                 */
                /*add_button.setVisibility(View.GONE);
                job_position.setVisibility(View.GONE);
                company_name.setVisibility(View.GONE);
                salary.setVisibility(View.GONE);
                job_location.setVisibility(View.GONE);

                 */

            }
        });
    }
}