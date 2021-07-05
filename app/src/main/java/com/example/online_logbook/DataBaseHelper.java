package com.example.online_logbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Date;

public class DataBaseHelper  extends SQLiteOpenHelper {

    public static final String db_name="Logbook.db";
    public static final String table_name="logbook_table";

    public static final String ID="ID";
    public static final String flightNo="Flights_number";
    public static final String date="Date";
    public static final String gliderType="Glider_type";
    public static final String registration="Registration";
    public static final String departure="Departure";
    public static final String arrival="arrival";
    public static final String launchType="Launch_type";
    public static final String crewCap="Crew_capacity";
    public static final String picName="PIC_name";
    public static final String picTime="Pic";
    public static final String underInstructionTime="Under_instruction";
    public static final String observations="Observations";



    public DataBaseHelper(@Nullable Context context) {
        super(context, db_name, null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + table_name + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,Flights_number INTEGER,Date TEXT,Glider_type TEXT,Registration TEXT,Departure TEXT, Arrival TEXT,Launch_type TEXT,Crew_capacity INTEGER,PIC_name TEXT,PIC INTEGER,Under_Instruction INTEGER,Observations TEXT)");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS "+table_name );
        //onCreate(db);
        //nu fac asta fiindca nu vreau sa sterg informatiile puse anterior


    }


    public boolean insertFlight(String numberOfFlights, String dateIn, String gliderTypeIn, String registrationIn, String departureIn, String arrivalIn, String launchTypeIn,
                                int crewCapacity,String picNameIn,int picHours,int underInstructionHours,String observationsIn)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();


        contentValues.put(flightNo,numberOfFlights);
        contentValues.put(date,dateIn);
        contentValues.put(gliderType,gliderTypeIn);
        contentValues.put(registration,registrationIn);
        contentValues.put(departure,departureIn);
        contentValues.put(arrival,arrivalIn);
        contentValues.put(launchType,launchTypeIn);
        contentValues.put(crewCap,crewCapacity);
        contentValues.put(picName,picNameIn);
        contentValues.put(picTime,picHours);
        contentValues.put(underInstructionTime,underInstructionHours);
        contentValues.put(observations,observationsIn);

        long restul=db.insert(table_name,null,contentValues);

        if(restul== -1)
            return false;
        else
            return true;



    }



    public Cursor getAllData()
    {
        SQLiteDatabase db=this.getWritableDatabase();

        Cursor result=db.rawQuery("select * from "+table_name,null);

        return result;
    }


    public Cursor searchInDbByAircraft(String aircraftName)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        Cursor result=db.rawQuery("select * from "+table_name+" where Glider_type='"+aircraftName+"' COLLATE NOCASE",null);

        return result;
    }


    public Cursor getDataById(int index)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        String query="select * from "+table_name+" where ID='"+index+"'";


        Cursor result=db.rawQuery(query,null);

        return result;

    }

    public Cursor searchInDbByDate(String date1, String date2)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        Cursor result=db.rawQuery("select * from "+table_name+" where DATE(Date) BETWEEN '"+date1+"' AND '"+date2+"'",null);
        Log.wtf("hashtag","select * from "+table_name+" where Date(date, '%d-%m-%Y') BETWEEN '"+date1+"' AND '"+date2+"'" );

        return result;
    }


    public boolean updateFlight(String index,String numberOfFlights, String dateIn, String gliderTypeIn,
                                String registrationIn, String departureIn, String arrivalIn, String launchTypeIn,
                                int crewCapacity,String picNameIn,int picHours,int underInstructionHours,String observationsIn)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();


        contentValues.put(ID,index);
        contentValues.put(flightNo,numberOfFlights);
        contentValues.put(date,dateIn);
        contentValues.put(gliderType,gliderTypeIn);
        contentValues.put(registration,registrationIn);
        contentValues.put(departure,departureIn);
        contentValues.put(arrival,arrivalIn);
        contentValues.put(launchType,launchTypeIn);
        contentValues.put(crewCap,crewCapacity);
        contentValues.put(picName,picNameIn);
        contentValues.put(picTime,picHours);
        contentValues.put(underInstructionTime,underInstructionHours);
        contentValues.put(observations,observationsIn);



        db.update(table_name,contentValues, "ID = ?",new String[] {index});


        return true;
    }



    public Integer deleteFlight(String id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Integer response;
        response=db.delete(table_name,"ID = ?", new String[] {id} );

        return response;
    }




}
