package com.hrm.vishwesh.hrms;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by vishwesh on 3/1/18.
 */

public class Apply_leave extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    boolean fired=false;
    Button button1,button2;
    String req_to_date;
    String req_from_Date;
    RadioGroup radioGroup;
    RadioButton radioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply_leave);
         button1=(Button)findViewById(R.id.req_to_date);
         button2=(Button)findViewById(R.id.req_from_date);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.DialogFragment datePicker =new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date Picker");
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.DialogFragment datePicker =new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date Picker2");
                 fired=true;
            }

        });

        //radio button
         radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
         radioButton=(RadioButton)findViewById(R.id.radioButton);
        HashMap<String,String> params=new HashMap<>();
        params.put("typeOfLeave",radioButton.getText().toString());
        params.put("req_to_date",req_to_date);
        params.put("req_from_date",req_from_Date);
        params.put("emp_id","1");
        Httpcall httpcall=new Httpcall();
        httpcall.setParams(params);

        BackgroundApplyLeave bg_apply_leave=new BackgroundApplyLeave();
        bg_apply_leave.execute(httpcall);


    }
    public void onRadioButtonClicked(View view){
         int radioButtonId=radioGroup.getCheckedRadioButtonId();
         radioButton=(RadioButton)findViewById(radioButtonId);
         Log.d("HERE SELECTED----->",radioButton.getText().toString());

    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

        Calendar c=Calendar.getInstance();
                if(fired==true){
                    c.set(Calendar.YEAR,i);
                    c.set(Calendar.MONTH,i1);
                    c.set(Calendar.DAY_OF_MONTH,i2);
                    req_from_Date= DateFormat.getDateInstance().format(c.getTime());
                    Log.d("Req_from_date---->",req_from_Date);
                     button2.setText(req_from_Date);
                }else{

        c.set(Calendar.YEAR,i);
        c.set(Calendar.MONTH,i1);
        c.set(Calendar.DAY_OF_MONTH,i2);
         req_to_date= DateFormat.getDateInstance().format(c.getTime());
        Log.d("Req_to_date---->",req_to_date);
                button1.setText(req_to_date);}
    }
}
