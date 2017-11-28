package com.hrm.vishwesh.hrms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
     EditText e1,e2;
     Button b1,b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1=(EditText)findViewById(R.id.editText);
        e2=(EditText)findViewById(R.id.editText2);


    }



    public void login_click(View view){


        String username=e1.getText().toString();
        String password=e2.getText().toString();
        Intent i=new Intent(this,login_activity.class);
        startActivity(i);
        BackgroundWorker backgroundWorker=new BackgroundWorker();

        //ahiya string parameter pass krvnaa hata , te edittext.gettext karya vagar pass karelu etle object ni string jati
        //hati. edittext.gettext() karish to j tane eni value male so ena lidhe post parameters wrong banta hata tara
        //etle natu avtu kai. and blank [] response avtoto etle kaik parameters ma locha hy tyre j ave km? kmke mostly
        //mostly parameters wrong hy to case handled hy like data nathi error che and all but te kai handle natu karyu so [] aavela api
        //mathi, so a rite debug vichr krvnu hy ke bhul kya kya hoi ske e line by line debug karish to j avdshe
        //hu doinbackground valo class akho day debug karya karu pan uri.bilder ma paramter nati malti
        backgroundWorker.execute(username,password,null);
//yes ap
    }

    public void signup_click(View view) {

    }
}
