package com.example.online_logbook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FlightViewer extends AppCompatActivity {

    DataBaseHelper logBookDB;
    ArrayAdapter<String> adapter;
    ArrayList<String> flights=new ArrayList<String>();

    String aircraftModel;

    TextView picTimeTextView,underInstructionTextView,totalTimeTextView,numberOfFlightsTextView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_viewer);
        logBookDB = new DataBaseHelper(this);
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,flights);
        ListView listView=(ListView)findViewById(R.id.flights);


        Bundle getInfoFromSearchDialog=getIntent().getExtras();

        if(getInfoFromSearchDialog.getString("action").equals("all"))
            retrieveAllData();
         else if(getInfoFromSearchDialog.getString("action").equals("search_by_aircraft"))
            searchByAircraft(getInfoFromSearchDialog.getString("model"));
         else if(getInfoFromSearchDialog.getString("action").equals("search_by_date"))
             searchByDate(getInfoFromSearchDialog.getString("date1"),getInfoFromSearchDialog.getString("date2"));

        //            data cea mai mica apoi data cea mai mare
        //searchByDate("1999-04-12","1999-04-12");

            //retrieveAllData();

        selectFlight(listView);

    }



    public void retrieveAllData()
    {
        Cursor res=logBookDB.getAllData();
        if(res.getCount()==0)
        {
            //show message
            showMessage("Ops","There are no flights inserted.");

        }
        else
        {
            ListView listView=(ListView)findViewById(R.id.flights);

            int numberOfFlights=0;
            int picTime=0;
            int underInstructionTime=0;

            while(res.moveToNext())
            {


                StringBuilder builder=new StringBuilder();

                //buffer.append("Index: "+res.getString(0)+"\n");
                builder.append("Index:"+res.getString(0)+"  ");
               builder.append("Date: "+res.getString(2)+"  ");
                builder.append("Registration: "+res.getString(4));
               // buffer.append("Pic name: "+res.getString(9)+"\n\n");

                picTime+=Integer.parseInt(res.getString(10));
                underInstructionTime+=Integer.parseInt(res.getString(11));


                numberOfFlights++;

                flights.add(builder.toString());

            }

            listView.setAdapter(adapter);

            putTimeInTextView(picTime,underInstructionTime,numberOfFlights);


        }
    }



    public void searchByAircraft(String aircraftName)
    {
        Cursor res=logBookDB.searchInDbByAircraft(aircraftName);
        if(res.getCount()==0)
        {
            //show message
            showMessage("Ops","There are no flights inserted.");

        }
        else
        {
            ListView listView=(ListView)findViewById(R.id.flights);
            int numberOfFlights=0;
            int picTime=0;
            int underInstructionTime=0;

            while(res.moveToNext())
            {
                StringBuilder builder=new StringBuilder();

                //buffer.append("Index: "+res.getString(0)+"\n");
                builder.append("Index:"+res.getString(0)+"  ");
                builder.append("Date: "+res.getString(2)+"  ");
                builder.append("Registration: "+res.getString(4));
                // buffer.append("Pic name: "+res.getString(9)+"\n\n");

                picTime+=Integer.parseInt(res.getString(10));
                underInstructionTime+=Integer.parseInt(res.getString(11));

                numberOfFlights++;
                flights.add(builder.toString());

            }

            listView.setAdapter(adapter);
            putTimeInTextView(picTime,underInstructionTime,numberOfFlights);


        }
    }



    public void searchByDate(String date1,String date2)
    {
        Cursor res=logBookDB.searchInDbByDate(date1,date2);
        if(res.getCount()==0)
        {
            //show message
            showMessage("Ops","There are no flights inserted.");

        }
        else
        {
            ListView listView=(ListView)findViewById(R.id.flights);
            int numberOfFlights=0;
            int picTime=0;
            int underInstructionTime=0;

            while(res.moveToNext())
            {
                StringBuilder builder=new StringBuilder();

                //buffer.append("Index: "+res.getString(0)+"\n");
                builder.append("Index:"+res.getString(0)+"  ");
                builder.append("Date: "+res.getString(2)+"  ");
                builder.append("Registration: "+res.getString(4));
                // buffer.append("Pic name: "+res.getString(9)+"\n\n");

                picTime+=Integer.parseInt(res.getString(10));
                underInstructionTime+=Integer.parseInt(res.getString(11));

                numberOfFlights++;
                flights.add(builder.toString());

            }

            listView.setAdapter(adapter);
            putTimeInTextView(picTime,underInstructionTime,numberOfFlights);


        }
    }




    public void selectFlight(ListView listView)
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem=(String) parent.getItemAtPosition(position);

                int indexNumber=getNumberFromString(selectedItem);

                Cursor res=logBookDB.getDataById(indexNumber);

                StringBuffer builder=new StringBuffer();
                
                ArrayList<String> flightToSend = new ArrayList<String>();

                while(res.moveToNext())
                {

                    //buffer.append("Index: "+res.getString(0)+"\n");
                    builder.append("Index in database: "+res.getString(0)+"\n");
                    flightToSend.add(res.getString(0));

                    builder.append("Number of flights: "+res.getString(1)+"\n");
                    flightToSend.add(res.getString(1));

                    builder.append("Date: "+res.getString(2)+"\n");
                    flightToSend.add(res.getString(2));

                    builder.append("Glider type: "+res.getString(3)+"\n");
                    flightToSend.add(res.getString(3));

                    builder.append("Registration: "+res.getString(4)+"\n");
                    flightToSend.add(res.getString(4));

                    builder.append("Departure: "+res.getString(5)+"\n");
                    flightToSend.add(res.getString(5));

                    builder.append("Arrival: "+res.getString(6)+"\n");
                    flightToSend.add(res.getString(6));

                    builder.append("Launch type: "+res.getString(7)+"\n");
                    flightToSend.add(res.getString(7));

                    builder.append("Crew capacity: "+res.getString(8)+"\n");
                    flightToSend.add(res.getString(8));

                    builder.append("PIC name: "+res.getString(9)+"\n");
                    flightToSend.add(res.getString(9));

                    builder.append("PIC time: "+res.getString(10)+"\n");
                    flightToSend.add(res.getString(10));

                    builder.append("Under instruction time: "+res.getString(11)+"\n");
                    flightToSend.add(res.getString(11));

                    builder.append("Observations: "+res.getString(12)+"\n");
                    flightToSend.add(res.getString(12));


                }


                showFlight("Flight",builder.toString(),flightToSend);

            }
        });
    }


    public void showMessage(String title,String Message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);

        builder.setTitle(title);
        builder.setMessage(Message);

        builder.show();
    }





    public int getNumberFromString(String string)
    {
        int indexOfPoints=string.indexOf(":");
        int indexOfSpace=string.indexOf(" ");



        String numbers=string.replaceAll("\\D+","").substring(0,indexOfSpace-indexOfPoints-1);
        int indexNumber=Integer.parseInt(numbers);


        return indexNumber;
    }





    public void showFlight(String title,String Message,ArrayList<String> flightInfo)
    {
        //Log.wtf("hashtag",index);
        Intent intentToUpdateFlight=new Intent(this,UpdateFlight.class);



        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);

        builder.setTitle(title);
        builder.setMessage(Message);

        builder.setPositiveButton("Update flight", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //trimit mesaj cu zborul posibil, sau cu un index


                intentToUpdateFlight.putExtra("flight_info",flightInfo);
                Bundle getInfoFromSearchDialog=getIntent().getExtras();

                if(getInfoFromSearchDialog.getString("action").equals("all"))
                {
                    intentToUpdateFlight.putExtra("action","all");

                }

                else if(getInfoFromSearchDialog.getString("action").equals("search_by_aircraft"))
                {
                    intentToUpdateFlight.putExtra("action","search_by_aircraft");
                    intentToUpdateFlight.putExtra("model",getInfoFromSearchDialog.getString("model"));
                }

                    else if(getInfoFromSearchDialog.getString("action").equals("search_by_date"))
                    {
                        intentToUpdateFlight.putExtra("action","search_by_date");
                        intentToUpdateFlight.putExtra("date1",getInfoFromSearchDialog.getString("date1"));
                        intentToUpdateFlight.putExtra("date2",getInfoFromSearchDialog.getString("date2"));

                    }


            //Log.wtf("hashtag",getInfoFromSearchDialog.getString("action"));





                startActivity(intentToUpdateFlight);
                finish();
            }
        });


        builder.setNegativeButton("Delete flight", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Integer deleteOneFlight=logBookDB.deleteFlight(flightInfo.get(0));

                if(deleteOneFlight>0)
                {
                    Toast.makeText(FlightViewer.this, "Flight deleted", Toast.LENGTH_LONG).show();

                    finish();
                    startActivity(getIntent());

                }

                else
                    Toast.makeText(FlightViewer.this, "Couldn't delete flight", Toast.LENGTH_LONG).show();

            }
        });

        builder.show();
    }


    public void putTimeInTextView(int picTime,int underInstructionTime, int numberOfFlights)
    {
        int totalTime=picTime+underInstructionTime;
        int hours,minutes;

        picTimeTextView=(TextView)findViewById(R.id.pic_time_viewer);


        String text;

        hours=picTime/60;
        minutes=picTime%60;


        if(minutes<10)
            text="PIC time: "+String.valueOf(hours)+" : 0"+String.valueOf(minutes);
        else
             text="PIC time: "+String.valueOf(hours)+" : "+String.valueOf(minutes);

        picTimeTextView.setText(text);



        hours=underInstructionTime/60;
        minutes=underInstructionTime%60;

        if(minutes<10)
            text="Under instruction time: "+String.valueOf(hours)+" : 0"+String.valueOf(minutes);
        else
            text="Under instruction time: "+String.valueOf(hours)+" : "+String.valueOf(minutes);

        underInstructionTextView=(TextView)findViewById(R.id.under_instruction_time_viewer);
        underInstructionTextView.setText(text);



        hours=totalTime/60;
        minutes=totalTime%60;

        if(minutes<10)
            text="Total time: "+String.valueOf(hours)+" : 0"+String.valueOf(minutes);
        else
            text="Total time:  "+String.valueOf(hours)+" : "+String.valueOf(minutes);

        totalTimeTextView=(TextView)findViewById(R.id.total_time_viewer);
        totalTimeTextView.setText(text);



        if(numberOfFlights<10)
            text="Number of flights: 0"+String.valueOf(numberOfFlights);
        else
            text="Number of flights: "+String.valueOf(numberOfFlights);

        numberOfFlightsTextView=(TextView)findViewById(R.id.total_flights_viewer);
       numberOfFlightsTextView.setText(text);




    }


}