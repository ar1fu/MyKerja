package com.example.mykerja;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mykerja.jobList.JobListDatabase;

public class SignUpActivity extends AppCompatActivity {

    EditText email,password,confirmPass ;
    Button signUpButton,loginBtn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.reg_email_field);
        password = findViewById(R.id.reg_pass_field);
        confirmPass = findViewById(R.id.reg_confirm_pass_field);

        signUpButton = findViewById(R.id.reg_signUp_btn);
        loginBtn = findViewById(R.id.reg_login_btn);

        JobListDatabase db = new JobListDatabase(this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String EMAIL = email.getText().toString();
                String PASS = password.getText().toString();
                String CONFIRM_PASS = confirmPass.getText().toString();

                if(EMAIL.equals("")||PASS.equals("")||CONFIRM_PASS.equals("")){
                    Toast.makeText(SignUpActivity.this, "Fill in all the field", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(PASS.equals(CONFIRM_PASS)){
                        Boolean emailCheckResult =  db.checkEmail(EMAIL);
                        if(emailCheckResult == false){
                            Boolean regResult = db.insertData(EMAIL,PASS);

                            if(regResult == true){
                                Toast.makeText(SignUpActivity.this, "Successfully Signed Up ", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(SignUpActivity.this, "Fail to Sign Up , Please try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(SignUpActivity.this, "User already exist ", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(SignUpActivity.this, "Password not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}