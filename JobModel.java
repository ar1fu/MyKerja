package com.example.mykerja.model;

public class JobModel {
    String job_id, job_position, job_place, job_salary, job_location;

    public JobModel(String job_id, String job_position, String job_place, String job_salary, String job_location) {
        this.job_id = job_id;
        this.job_position = job_position;
        this. job_place = job_place;
        this. job_salary = job_salary;
        this.job_location = job_location;
    }

    public String getJobID(){return job_id;}

    public String getJobPosition(){
        return job_position;
    }

    public String getJobPlace(){
        return job_place;
    }

    public String getJobSalary(){
        return job_salary;
    }

    public String getJobLocation(){
        return job_location;
    }
}
