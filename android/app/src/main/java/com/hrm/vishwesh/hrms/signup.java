package com.hrm.vishwesh.hrms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        Integer uid=Integer.parseInt(id);
        String user=e2.getText().toString();
        String pass=e3.getText().toString();
        String email=e3.getText().toString();
        Intent i=new Intent(this,login_activity.class);

        startActivity(i);
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
