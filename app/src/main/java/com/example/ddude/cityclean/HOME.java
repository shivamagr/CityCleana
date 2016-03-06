package com.example.ddude.cityclean;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class HOME extends Activity {

    DbHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        dbHandler=new DbHandler(this,null,null,1);
        if(!dbHandler.check())
        {
            Intent i=new Intent(this,BasicInfo.class);
            startActivity(i);
        }


    }


        public void enterIssueinto(View view)
    {
            Intent i=new Intent(this,MainActivity.class);
         //   i.putExtra("et_loca",etl);
            startActivity(i);

        //Intent intent=new Intent(this,MainActivity.class);
        //startActivity(intent);
    }

    public void ShowMyPastIssues(View view)
    {
        Intent i=new Intent(this,MyPastIssueShow.class);
        startActivity(i);
    }

}
