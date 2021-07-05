package com.example.online_logbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class UpdateFlight extends AppCompatActivity {

    ArrayList<String> flightInfoToUpdate=new ArrayList<>();
    EditText numberFlights,date,gliderType,registration,departure,arrival,launchType,crewCapacity,picName,pic,underInstruction,observations;
    DataBaseHelper logBookDB;
    String indexOfFlight;
    Bundle getInfoFromSearchDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_flight);
        logBookDB = new DataBaseHelper(this);

        getInfoFromSearchDialog=getIntent().getExtras();


        Intent intent=getIntent();
        flightInfoToUpdate=intent.getStringArrayListExtra("flight_info");
       //Log.wtf("hashtag",intent.getStringExtra("index"));

        indexOfFlight=flightInfoToUpdate.get(0);


        numberFlights=(EditText)findViewById(R.id.update_number_flights);
        numberFlights.setText(flightInfoToUpdate.get(1));


        date=(EditText)findViewById(R.id.update_date);
        date.setText(flightInfoToUpdate.get(2));


        gliderType=(EditText)findViewById(R.id.update_glider_type);
        gliderType.setText(flightInfoToUpdate.get(3));


        registration=(EditText)findViewById(R.id.update_registration);
        registration.setText(flightInfoToUpdate.get(4));

        departure=(EditText)findViewById(R.id.update_departure);
        departure.setText(flightInfoToUpdate.get(5));

        arrival=(EditText)findViewById(R.id.update_arrival);
        arrival.setText(flightInfoToUpdate.get(6));


        launchType=(EditText)findViewById(R.id.update_launch_type);
        launchType.setText(flightInfoToUpdate.get(7));

        crewCapacity=(EditText)findViewById(R.id.update_crew_capacity);
        crewCapacity.setText(flightInfoToUpdate.get(8));

        picName=(EditText)findViewById(R.id.update_pic_name);
        picName.setText(flightInfoToUpdate.get(9));

        pic=(EditText)findViewById(R.id.update_pic_time);
        pic.setText(flightInfoToUpdate.get(10));

        underInstruction=(EditText)findViewById(R.id.update_under_instruction_time);
        underInstruction.setText(flightInfoToUpdate.get(11));

        observations=(EditText)findViewById(R.id.update_observations);
        observations.setText(flightInfoToUpdate.get(12));





    }



    public void onClick(View view)
    {
       //Log.wtf("hashtag",register.getText().toString()+" "+date.getText().toString());
        boolean isUpdated=logBookDB.updateFlight(indexOfFlight,numberFlights.getText().toString(),date.getText().toString(),gliderType.getText().toString(),
                registration.getText().toString(),departure.getText().toString(),arrival.getText().toString(),launchType.getText().toString(),
                Integer.parseInt(crewCapacity.getText().toString()),picName.getText().toString(),Integer.parseInt(pic.getText().toString()),
                Integer.parseInt(underInstruction.getText().toString()),observations.getText().toString());


        if (isUpdated)
        {
            Toast.makeText(UpdateFlight.this, "Flight updated", Toast.LENGTH_LONG).show();
            Intent intentToUpdateFlight = new Intent(this, FlightViewer.class);

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


            startActivity(intentToUpdateFlight);
            finish();
        }
        else
            Toast.makeText(UpdateFlight.this,"Error updating flight",Toast.LENGTH_LONG).show();



    }




}