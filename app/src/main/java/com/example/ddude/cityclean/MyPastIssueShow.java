package com.example.ddude.cityclean;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MyPastIssueShow extends Activity {

    DbHandler dbHandler ;
    TextView textView;
    ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_past_issue_show);

        listview = (ListView) findViewById(R.id.listView);

        dbHandler = new DbHandler(this,null,null,1);
        String st = dbHandler.getIssueName();

        String[] lv = st.split("\t");
/*       String[][] ll = new String[1000][1000];

        int k = 0;
        for(int i=0;i<lv.length;i++){
            ll[k++] = lv[i].split("/?");
        }

        String[] lk = new String[1000];
        for(int j=0;j<k;j++){
            lk[j] = "Name:"+ll[j][0]+"  Location:"+ll[j][1]+ "\n"+ll[j][2]+" Time:"+ll[j][3] ;
        }
*/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, lv);

        listview.setAdapter(adapter);



    }



}