package com.example.online_logbook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AddFlight extends AppCompatActivity {


    DataBaseHelper logBookDB;
    EditText numberFlights,date,gliderType,registration,departure,arrival,launchType,crewCapacity,picName,pic,underInstruction,observations;
    Button insertButton;
    Button retrieve_data;
    String fromDate, toDate;
    DatePickerDialog datePickerDialog;
    boolean empty=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flight);
        logBookDB = new DataBaseHelper(this);

        numberFlights=(EditText)findViewById(R.id.flights_number);
        date=(EditText)findViewById(R.id.date);
        gliderType=(EditText)findViewById(R.id.glider_type);
        registration=(EditText)findViewById(R.id.registration);
        departure=(EditText)findViewById(R.id.departure);
        arrival=(EditText)findViewById(R.id.arrival);
        launchType=(EditText)findViewById(R.id.launch_type);
        crewCapacity=(EditText)findViewById(R.id.crew_capacity);
        picName=(EditText)findViewById(R.id.pic_name);
        pic=(EditText)findViewById(R.id.pic_hours);
        underInstruction=(EditText)findViewById(R.id.under_instruction_hours);
        observations=(EditText)findViewById(R.id.observations);

        insertButton=(Button)findViewById(R.id.insert_button);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDatePickerFrom();
            }
        });


        addData();
    }


    public void addData()
    {


            insertButton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            if(TextUtils.isEmpty(numberFlights.getText()) || TextUtils.isEmpty(date.getText()) || TextUtils.isEmpty(gliderType.getText()) ||
                                    TextUtils.isEmpty(registration.getText()) || TextUtils.isEmpty(departure.getText()) || TextUtils.isEmpty(arrival.getText()) ||
                                    TextUtils.isEmpty(launchType.getText()) || TextUtils.isEmpty(crewCapacity.getText()) || TextUtils.isEmpty(picName.getText()) ||
                                    TextUtils.isEmpty(pic.getText())) {
                                Toast.makeText(AddFlight.this, "You have incomplete fields!", Toast.LENGTH_SHORT).show();

                                empty=false;
                            }

                            if(empty==true) {

                                boolean isInserted = logBookDB.insertFlight(numberFlights.getText().toString(), date.getText().toString(), gliderType.getText().toString(),
                                        registration.getText().toString(), departure.getText().toString(), arrival.getText().toString(), launchType.getText().toString(),
                                        Integer.parseInt(crewCapacity.getText().toString()), picName.getText().toString(), Integer.parseInt(pic.getText().toString()),
                                        Integer.parseInt(underInstruction.getText().toString()), observations.getText().toString());

                                if (isInserted && empty)
                                    Toast.makeText(AddFlight.this, "Data inserted", Toast.LENGTH_LONG).show();
                                else
                                    Toast.makeText(AddFlight.this, "Data not inserted", Toast.LENGTH_LONG).show();

                            }
                            empty=true;

                        }
                    }

            );

    }

    private void initDatePickerFrom()
    {


        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                fromDate=makeDateString(dayOfMonth,month,year);
                //pun data in input
                date.setText(fromDate);
            }
        };

        Calendar cal=Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DAY_OF_MONTH);




        int style= AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog=new DatePickerDialog(this,style,dateSetListener,year,month,day);

        datePickerDialog.setTitle("Select a date");
        datePickerDialog.show();
    }
    private String makeDateString(int dayOfMonth, int month, int year)
    {
        String madeDate,dayString,monthString;


        if(dayOfMonth<10)
            dayString="0"+dayOfMonth;
        else dayString= String.valueOf(dayOfMonth);

        if( month<10)
            monthString="0"+month;
        else monthString=String.valueOf(month);


        madeDate=year+"-"+monthString+"-"+dayString;

        return madeDate;
    }



}