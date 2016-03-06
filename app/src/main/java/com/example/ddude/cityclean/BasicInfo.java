package com.example.ddude.cityclean;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class BasicInfo extends Activity {

    EditText et_name,et_phone;
    String name,phone;
    DbHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info);

        et_name=(EditText)findViewById(R.id.nameinfo);
        et_phone=(EditText)findViewById(R.id.phoneinfo);

        name=et_name.getText().toString();
        phone=et_phone.getText().toString();

        dbHandler = new DbHandler(this,null,null,1);
    }

    public void enter(View view)
    {
        UserDb userDb=new UserDb(name,phone);
        dbHandler.enterdetail(userDb);
        Intent intent=new Intent(this,HOME.class);
        startActivity(intent);
    }

}
