package com.hrm.vishwesh.hrms;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vishw on 29-11-2017.
 */

public class BackgroundSignup extends AsyncTask<Httpcall,String,String> {

    @Override



    protected String doInBackground(Httpcall... params) {
        HttpURLConnection urlConnection=null;
        Httpcall httpcall =params[0];
        StringBuilder response = new StringBuilder();
        try {
             String dataParams=getPostDataString(httpcall.getParams());
             URL url=new URL("http://192.168.43.128:3000/signup");
             urlConnection = (HttpURLConnection) url.openConnection();
             urlConnection.setRequestMethod("POST");
             urlConnection.setDoOutput(true);
             urlConnection.setDoInput(true);

            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);

            if(httpcall.getParams()!=null){

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.append(dataParams);
                writer.flush();
                writer.close();
                os.close();
            }

            int responseCode = urlConnection.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK){
                String line ;
                BufferedReader br = new BufferedReader( new InputStreamReader(urlConnection.getInputStream()));
                while ((line = br.readLine()) != null){
                    response.append(line);
                }
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            urlConnection.disconnect();
        }
         String res=response.toString();

        return res;
    }





    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }


}
