package com.example.mykerja;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mykerja.adapter.SessionManagement;
import com.example.mykerja.jobList.JobListDatabase;
import com.example.mykerja.model.User;


public class LoginActivity extends AppCompatActivity {
    EditText email,password ;
    Button loginBtn,signupBtn ;

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;


    @Override
    protected void onStart() {
        super.onStart();
        checkSession();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        loginBtn = findViewById(R.id.login_btn);
        signupBtn = findViewById(R.id.login_signup_btn);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        JobListDatabase db = new JobListDatabase(this);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String EMAIL = email.getText().toString();
                String PASS = password.getText().toString();
                int USERID = db.getUserID(EMAIL);

                if(EMAIL.equals("")||PASS.equals("")){
                    Toast.makeText(LoginActivity.this, "Please fill in all field", Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean checkUserPass = db.checkEmailPassword(EMAIL,PASS);
                    if(checkUserPass == true){

                        User user = new User(USERID,EMAIL);
                        SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
                        sessionManagement.saveSession(user);

                        //Pass user email to main activity
                        mEditor.putString(getString(R.string.email),EMAIL );
                        mEditor.commit();

                        Toast.makeText(LoginActivity.this, "Successfully Login ", Toast.LENGTH_SHORT).show();
                        moveToMainActivity();
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private void checkSession() {
        SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
        int UserID = sessionManagement.getSession();

        if(UserID != -1){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
    }
    private void moveToMainActivity(){
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}