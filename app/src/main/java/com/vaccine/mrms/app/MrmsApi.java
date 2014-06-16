package com.vaccine.mrms.app;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;


/**
 * Created by amittal on 5/19/14.
 */
abstract public class MrmsApi extends AsyncTask<String, Void, String> {

    abstract void call();

    protected String doInBackground(String... urls) {
        InputStream inputStream = null;
        String result = "";
        String url = "http://mrms.herokuapp.com" + urls[0];

        try {
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpGet request = new HttpGet(url);


            HttpResponse httpResponse = httpclient.execute(request);

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);



        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }


        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
}
