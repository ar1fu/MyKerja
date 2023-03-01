package com.example.mykerja.adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mykerja.JobActivity;
import com.example.mykerja.R;
import com.example.mykerja.UpdateActivity;

import java.util.ArrayList;

import kotlinx.coroutines.Job;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.MyViewHolder> {


    private final Context context;
    Activity activity;
    private final ArrayList job_id;
    private final ArrayList job_position;
    private final ArrayList job_place;
    private final ArrayList job_salary;
    private final ArrayList job_location;
    private final ArrayList line1;
    private final ArrayList line2;
    private final ArrayList line3;

    public JobAdapter(Activity activity, Context context, ArrayList job_id, ArrayList job_position, ArrayList job_place, ArrayList job_salary, ArrayList job_location,
                      ArrayList line1, ArrayList line2, ArrayList line3){

        this.activity = activity;
        this.context = context;
        this.job_id = job_id;
        this.job_position = job_position;
        this.job_place = job_place;
        this.job_salary = job_salary;
        this.job_location = job_location;
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.job_recyclerview_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobAdapter.MyViewHolder holder, int position) {
        holder.job_id_txt.setText(String.valueOf(job_id.get(position)));
        holder.job_position_txt.setText(String.valueOf(job_position.get(position)));
        holder.job_place_txt.setText(String.valueOf(job_place.get(position)));
        holder.job_salary_txt.setText(String.valueOf(job_salary.get(position)));
        holder.job_location_txt.setText(String.valueOf(job_location.get(position)));

        /*holder.jobLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, JobActivity.class);
                intent.putExtra("id", String.valueOf(job_id.get(position)));
                intent.putExtra("position", String.valueOf(job_position.get(position)));
                intent.putExtra("place", String.valueOf(job_place.get(position)));
                intent.putExtra("salary", String.valueOf(job_salary.get(position)));
                intent.putExtra("location", String.valueOf(job_location.get(position)));
                //activity.startActivityForResult(intent, 1);
            }
        });

         */
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, JobActivity.class);
                intent.putExtra("id", String.valueOf(job_id.get(holder.getAdapterPosition())));
                intent.putExtra("position", String.valueOf(job_position.get(holder.getAdapterPosition())));
                intent.putExtra("place", String.valueOf(job_place.get(holder.getAdapterPosition())));
                intent.putExtra("salary", String.valueOf(job_salary.get(holder.getAdapterPosition())));
                intent.putExtra("location", String.valueOf(job_location.get(holder.getAdapterPosition())));
                intent.putExtra("line1", String.valueOf(line1.get(holder.getAdapterPosition())));
                intent.putExtra("line2", String.valueOf(line2.get(holder.getAdapterPosition())));
                intent.putExtra("line3", String.valueOf(line3.get(holder.getAdapterPosition())));


                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return job_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView job_id_txt, job_position_txt, job_place_txt, job_salary_txt, job_location_txt;
        ConstraintLayout mainLayout;
        //ConstraintLayout jobLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            job_id_txt = itemView.findViewById(R.id.jobIdTxt);
            job_position_txt = itemView.findViewById(R.id.jobPositionTxt);
            job_place_txt = itemView.findViewById(R.id.JobPlaceTxt);
            job_salary_txt = itemView.findViewById(R.id.JobSalaryTxt);
            job_location_txt = itemView.findViewById(R.id.JobLocationTxt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            //jobLayout = itemView.findViewById(R.id.jobLayout);
        }
    }

}
