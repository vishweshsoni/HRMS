package com.hrm.vishwesh.hrms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

public class signup extends AppCompatActivity {
EditText e1,e2,e3,e4;
Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        e1=(EditText)findViewById(R.id.editText4);
        e2=(EditText)findViewById(R.id.editText5);
        e3=(EditText)findViewById(R.id.editText6);
        e4=(EditText)findViewById(R.id.editText7);

    }




    public void register_click(View view){
        String id=e1.getText().toString();

        String user=e2.getText().toString();
        String pass=e3.getText().toString();
        String email=e4.getText().toString();

          Intent i=new Intent(this,login_activity.class);

           if(TextUtils.isEmpty(id)){
                 e1.setError("Please enter the id");
                 e1.requestFocus();
                 return;
           }
           if(TextUtils.isEmpty(user)){
               e2.setError("Please enter the name");
               e2.requestFocus();
               return;
           }
        if(TextUtils.isEmpty(pass)){
            e3.setError("Please enter the password");
            e3.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(email)){
             e4.setError("Please enter the email");
             e4.requestFocus();
             return;
        }

        startActivity(i);
        Integer uid=Integer.parseInt(id);
        HashMap<String,String> params=new HashMap<>();
        params.put("username",user);
        params.put("password",pass);
        params.put("email",email);
        Httpcall httpCall = new Httpcall();
        httpCall.setParams(params);


        BackgroundSignup backgroundSignup=new BackgroundSignup();
        backgroundSignup.execute(httpCall);
    }
}
