package com.vaccine.mrms.app;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onSendSmsClick(View v) {
        TextView out = (TextView) findViewById(R.id.textView);
        new GetSMSRemindersAPI(out).call();

        /*PendingIntent pi = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage("2135902152", null, "This works", pi, null);
        */



    }

    public class GetSMSRemindersAPI extends MrmsPostAPI {
        private TextView m_out;

        GetSMSRemindersAPI(TextView out){
            m_out = out;
        }
        protected void call(){
            Map<String, String> params = new HashMap<String, String>();
            String session_token = ((MyApplication)getApplicationContext()).getmSessionToken();
            params.put("session_token", session_token );

            String json = new GsonBuilder().create().toJson(params, Map.class);

            execute("/api/reminder/getSMSReminders", json);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("ApiResponseChild", result);
            String abc = "defaulters";

            try {
                JSONObject jsonObj = new JSONObject(result);
                JSONObject list = jsonObj.getJSONObject(abc);
                Iterator<String> iter = list.keys();
                SmsManager sms = SmsManager.getDefault();
                while (iter.hasNext()) {
                    String key = iter.next();
                    Object value = list.get(key);
                    m_out.append(key);
                    if(key != "") {
                        sms.sendTextMessage(key, null, value.toString(), null, null);
                    }
                }

            }
            catch(JSONException e) {

            }
            //m_out.setText(result);
            //  SmsManager sms = SmsManager.getDefault();
            // sms.sendTextMessage("2135902152", null, "This works", null, null);

        }
    }

}

