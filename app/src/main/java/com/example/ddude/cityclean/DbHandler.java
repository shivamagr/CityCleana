package com.example.ddude.cityclean;

/**
 * Created by ddude on 05-Mar-16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Calendar;
import java.util.Date;

public class  DbHandler extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="AppMe.db";
    public static final String TABLE_NAME="user";
    public static final String COLUMN_ID="_id";
    public static final String COLUMN_NAME="_name";
    public static final String COLUMN_PHONE="_phoneno";

    public static final String TABLE2_NAME="pastissues";
    public static final String COLUMN2_ID="_2id";
    public static final String COLUMN2_ISSUENAME="_issuename";
    public static final String COLUMN2_LOCATION="_location";
    public static final String COLUMN2_DISCRIPTION="_discription";
    public static final String COLUMN2_TIME="_date";

    public DbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query="CREATE TABLE "+TABLE_NAME+"("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_NAME+
                " TEXT NOT NULL, "+COLUMN_PHONE+" TEXT );";
        sqLiteDatabase.execSQL(query);
        String query1="CREATE TABLE "+TABLE2_NAME+"("+COLUMN2_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN2_ISSUENAME+
                " TEXT NOT NULL, "+COLUMN2_LOCATION+" TEXT, "+COLUMN2_DISCRIPTION+" TEXT, "+COLUMN2_TIME+" TEXT );";
        sqLiteDatabase.execSQL(query1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE2_NAME);
        onCreate(sqLiteDatabase);

    }

    public void enterdetail(UserDb userDb)
    {
        ContentValues values =new ContentValues();
        values.put(COLUMN_NAME,userDb.get_name());
        values.put(COLUMN_PHONE, userDb.get_phoneno());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public boolean check()
    {
        SQLiteDatabase db=getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE 1;";
        Cursor c=db.rawQuery(query, null);
        if(c.getCount()<=0)
        {
            db.close();
            c.close();
            return false;
        }
        db.close();
        c.close();
        return true;
    }

    public void EnterIssue(PastIssue pastIssue)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN2_ISSUENAME,pastIssue.get_pastissue());
        values.put(COLUMN2_LOCATION,pastIssue.get_location());
        values.put(COLUMN2_DISCRIPTION, pastIssue.get_discription());

        Calendar calendar=Calendar.getInstance();
        Date d=calendar.getTime();
        String time=d.toString();
        values.put(COLUMN2_TIME,time);

        SQLiteDatabase db=getWritableDatabase();
        db.insert(TABLE2_NAME,null,values);
        db.close();
    }
    public String getIssueName() {

        String selectQuery = "SELECT * FROM " + TABLE2_NAME+" WHERE 1 ;";
        SQLiteDatabase db  = getWritableDatabase();
        String data="" ;

        Cursor c1 = db.rawQuery(selectQuery, null);
        //Move to the first row in your results
        c1.moveToFirst();

        //Position after the last row means the end of the results
        while (!c1.isAfterLast()) {
            if (c1.getString(c1.getColumnIndex(COLUMN2_ISSUENAME)) != null) {
                data += "Name:"+c1.getString(c1.getColumnIndex(COLUMN2_ISSUENAME))+"\n"+c1.getString(c1.getColumnIndex(COLUMN2_LOCATION))
                        +"\nDiscription:"+c1.getString(c1.getColumnIndex(COLUMN2_DISCRIPTION))+"\nTime:"+c1.getString(c1.getColumnIndex(COLUMN2_TIME));
                data += "\t";
            }
            c1.moveToNext();
        }
        db.close();
        return data;
    }
}
