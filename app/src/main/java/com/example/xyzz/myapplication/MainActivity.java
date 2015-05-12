package com.example.xyzz.myapplication;

import android.content.Context;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        TableLayout tlb = (TableLayout)findViewById(R.id.tlb);
        String api_call = "http://www.fbcredibility.com/cloudobject/user1/find/student";
        try {
            URL url = new URL(api_call);
            Scanner sc = new Scanner(url.openStream());
            StringBuffer buf = new StringBuffer();
            while(sc.hasNext()){
                buf.append(sc.next());
            }
            JSONObject jsonObject = new JSONObject(buf.toString());
            JSONArray sysArr = jsonObject.getJSONArray("data");
            int tl = sysArr.length();
            int i;
            String tt;
            ArrayList<String> StorageA = new ArrayList<String>();
            for(i = 0 ; i < tl ; i++){
                tt = sysArr.getString(i);
                JSONObject ti = new JSONObject(tt);
                StorageA.add(ti.getString("name"));

            }
            try {
                FileOutputStream fOut = this.openFileOutput("data.obj", Context.MODE_PRIVATE);
                ObjectOutputStream objOut = new ObjectOutputStream(fOut);
                objOut.writeObject(StorageA);
                objOut.close();
                fOut.close();

                FileInputStream fIn = this.openFileInput("data.obj");
                ObjectInputStream objIn = new ObjectInputStream(fIn);
                List<String> lstArray = (List<String>)objIn.readObject();
                for(String str: lstArray){
                    TableRow taRow = new TableRow(this);
                    TextView text = new TextView(this);
                    text.setText(str);
                    taRow.addView(text);
                    tlb.addView(taRow);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();

        }
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
}