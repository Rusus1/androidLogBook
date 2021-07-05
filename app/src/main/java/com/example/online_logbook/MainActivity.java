package com.example.online_logbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog,datePickerDialogTo;

    String fromDate,toDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initDatePickerFrom();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.activity_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.add_flight_menu:
                Intent intentAddFlight = new Intent(this,AddFlight.class);
                startActivity(intentAddFlight);
                return true;

            case R.id.see_flights_menu:
                Intent intentFlightViewer = new Intent(this,FlightViewer.class);
                intentFlightViewer.putExtra("action","all");
                startActivity(intentFlightViewer);
                return true;

            case R.id.search_by_aircraft:
                searchAircraft();
                return true;

            case R.id.search_by_date:
                searchByDate();
                return true;



            default:
                return super.onOptionsItemSelected(item);
        }
    }






    public void searchAircraft()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);

        LayoutInflater inflater=this.getLayoutInflater();
        View view=inflater.inflate(R.layout.layout_dialog_search_by_aircraft,null);

        builder.setView(view);
        builder.setTitle("Type your aircraft");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        }) ;


        AlertDialog alertDialog=builder.create();

        alertDialog.show();

        Button search=alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        search.setOnClickListener(new SearchDialog(alertDialog));

    }











    private void initDatePickerFrom()
    {
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month=month+1;
                        fromDate=makeDateString(dayOfMonth,month,year);
                        //aici am data de care ma folosesc
                        //Log.wtf("hashtag",date);
                        testNextDate(dayOfMonth,month-1,year);
            }
        };

        Calendar cal=Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DAY_OF_MONTH);




        int style=AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog=new DatePickerDialog(this,style,dateSetListener,year,month,day);
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


    public void searchByDate()
    {
        datePickerDialog.setTitle("Select first date");
        datePickerDialog.show();

    }



    public void testNextDate(int dayOfMonth, int month, int year)
    {
        Intent intentFlightViewer = new Intent(this,FlightViewer.class);


        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                toDate=makeDateString(dayOfMonth,month,year);



                SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");

                try {

                    Date date1=formatter.parse(fromDate);
                    Date date2=formatter.parse(toDate);

                    if(date1.after(date2))
                    {
                        String aux=fromDate;
                        fromDate=toDate;
                        toDate=aux;
                    }


                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String dates="Ai ales from "+ fromDate + " si to " + toDate;

                Log.wtf("hashtag","dau mai departe spre flightviewr informatia asta "+dates);

                //trimit mai departe datele la flightviewr
                intentFlightViewer.putExtra("action","search_by_date");
                intentFlightViewer.putExtra("date1",fromDate);
                intentFlightViewer.putExtra("date2",toDate);
                startActivity(intentFlightViewer);


            }
        };

        int style=AlertDialog.THEME_HOLO_LIGHT;


        datePickerDialogTo=new DatePickerDialog(this,style,dateSetListener,year,month,dayOfMonth);
        datePickerDialogTo.setTitle("Select second date");
        datePickerDialogTo.show();

    }
}