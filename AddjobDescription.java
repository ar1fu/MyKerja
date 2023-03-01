package com.example.mykerja;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mykerja.jobList.JobListDatabase;


public class AddjobDescription extends Fragment {

    Button add_button2;
    EditText line1, line2, line3;
    TextView job_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_addjob_description, container, false);
        //String jobData = getArguments().getString("jobData");

        job_id = view.findViewById(R.id.jobIdDesc);
        line1 = view.findViewById(R.id.editTextTextMultiLine);
        line2 = view.findViewById(R.id.editTextTextMultiLine2);
        line3 = view.findViewById(R.id.editTextTextMultiLine3);

        add_button2 = view.findViewById(R.id.button2);
        add_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobListDatabase jobDB = new JobListDatabase(getActivity());
                jobDB.addDescJob(job_id.getText().toString().trim(),
                        line1.getText().toString().trim(),
                        line2.getText().toString().trim(),
                        line3.getText().toString().trim());
            }
        });

        return view;

    }
}