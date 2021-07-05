package com.example.online_logbook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

public class SearchDialog implements View.OnClickListener {

    Dialog dialog;


    public SearchDialog(Dialog dialog)
    {
        this.dialog=dialog;
    }


    @Override
    public void onClick(View v) {

        Intent intentFlightViewer = new Intent(v.getContext(),FlightViewer.class);

        EditText aircraft=(EditText)dialog.findViewById(R.id.aircraft_search);
        Log.wtf("hashtag",aircraft.getText().toString());

        String aircraftModel=aircraft.getText().toString();
        intentFlightViewer.putExtra("action","search_by_aircraft");
        intentFlightViewer.putExtra("model",aircraftModel);

        v.getContext().startActivity(intentFlightViewer);

    }

}
