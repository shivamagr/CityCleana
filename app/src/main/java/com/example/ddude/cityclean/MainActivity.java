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
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity {

    EditText ET_NAME,ET_DESCRIPTION,ET_LOCATION;
    String et_name,et_desc,et_locat,msg;
    String lat="", lon="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ET_NAME=(EditText)findViewById(R.id.et_name);
        ET_DESCRIPTION=(EditText)findViewById(R.id.et_description);
        ET_LOCATION=(EditText)findViewById(R.id.et_location);
               LocationManager locationManager = (LocationManager) MainActivity.this.getSystemService(Context.LOCATION_SERVICE);
                LocationListener locationListener = new LocationListener() {
                    public void onLocationChanged(Location location) {
                        lat = Double.toString(location.getLatitude());
                        lon = Double.toString(location.getLongitude());
                    }

                    public void onStatusChanged(String provider, int status, Bundle extras) {}
                    public void onProviderEnabled(String provider) {}
                    public void onProviderDisabled(String provider) {}
                };
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }


   public void setText(View v) {
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean en = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!en){
            Toast.makeText(this,"Enable GPS",Toast.LENGTH_LONG).show();
        }
        else{

            ET_LOCATION.setText(GetAddress(lat, lon));
        }

    }
    public void postData(String la, String lo) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet htget = new HttpGet("http://myappurl.com/Home/Book/"+la+"/"+lo);

        try {
            HttpResponse response = httpclient.execute(htget);
            String resp = response.getStatusLine().toString();
            Toast.makeText(this, resp, Toast.LENGTH_LONG).show();


        } catch (ClientProtocolException e) {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
        }
    }
    public String GetAddress(String lat, String lon)
    {
        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
        String ret = "";
        try {
            List<Address> addresses = geocoder.getFromLocation(Double.parseDouble(lat), Double.parseDouble(lon), 1);
            if(addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("Address:\n");
                for(int i=0; i<returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                ret = strReturnedAddress.toString();
            }
            else{
                ret = "No Address returned!";
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ret = "Can't get Address!";
        }
        return ret;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void sendInfo(View view){
        et_name=ET_NAME.getText().toString();
        et_desc=ET_DESCRIPTION.getText().toString();
        et_locat=ET_LOCATION.getText().toString();
        msg="We want to draw your attention toward the issue~"+et_name+"~ faced by people of the following locality~" + et_locat + "~, The detailed description is given below~"+
                et_desc +"~. Try to fix the same as soon as possible in order for proper functioning of the city";

        Intent it=new Intent(MainActivity.this,camera.class);
        it.putExtra("message",msg);
        it.putExtra("gen",et_name);
        it.putExtra("temp","hello");
        startActivity(it);
    }
}
