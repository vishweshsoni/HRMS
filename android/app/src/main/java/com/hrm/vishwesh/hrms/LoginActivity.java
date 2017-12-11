package com.hrm.vishwesh.hrms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
     EditText e1,e2;
     Button b1,b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        e1=(EditText)findViewById(R.id.editText);
        e2=(EditText)findViewById(R.id.editText2);


    }



    public void login_click(View view){


        String username=e1.getText().toString();
        String password=e2.getText().toString();
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
        BackgroundWorker backgroundWorker=new BackgroundWorker();

        backgroundWorker.execute(username,password,null);

    }

    public void signup_click(View view) {

           Intent i=new Intent(this,signup.class);
           startActivity(i);


    }

}
