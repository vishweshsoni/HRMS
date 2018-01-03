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

import java.text.DateFormat;
import java.util.Calendar;

/**
 * Created by vishwesh on 3/1/18.
 */

public class Apply_leave extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    boolean fired=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply_leave);
        Button button1=(Button)findViewById(R.id.req_to_date);
        Button button2=(Button)findViewById(R.id.req_from_date);
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


    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

        Calendar c=Calendar.getInstance();
                if(fired==true){
                    c.set(Calendar.YEAR,i);
                    c.set(Calendar.MONTH,i1);
                    c.set(Calendar.DAY_OF_MONTH,i2);
                    String req_from_Date= DateFormat.getDateInstance().format(c.getTime());
                    Log.d("Req_from_date---->",req_from_Date);

                }else{

        c.set(Calendar.YEAR,i);
        c.set(Calendar.MONTH,i1);
        c.set(Calendar.DAY_OF_MONTH,i2);
        String req_to_date= DateFormat.getDateInstance().format(c.getTime());
        Log.d("Req_to_date---->",req_to_date);}
    }
}
