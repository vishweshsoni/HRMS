package com.hrm.vishwesh.hrms;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by vishw on 26-11-2017.
 */

public class BackgroundWorker extends AsyncTask<String,String,String> {

    @Override
    protected String doInBackground(String... strings) {
        String name=strings[0];
        String password=strings[1];
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
         String result=null;
        try {
            URL url=new URL("192.168.43.128:3000/api/login");//smbhlo have aai ne vat mare bhar javanu
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);


            // Append parameters to URL
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("username", strings[0])
                    .appendQueryParameter("password", strings[1]);
            String query = builder.build().getEncodedQuery();
            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();
            StringBuffer buffer = new StringBuffer();
            urlConnection.connect();
InputStream is=urlConnection.getInputStream();
    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
    String line;
    while ((line=bufferedReader.readLine())!=null){
        // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
        // But it does make debugging a *lot* easier if you print out the completed
        // buffer for debugging.
        buffer.append(line + "\n");
    }
            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            String r= buffer.toString();
            return r;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }
        finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
        return null;//return null thay che, internet connection thatu nathi etle tari api hit nathi thati, so wrong in http call..do correct that.
    }

   @Override
   protected void onPostExecute(String s){
            super.onPostExecute(s);
            if ( s != null)
                Log.i("Json",s);
            else {
                Log.d("BACKGROUNDWORKER----->","Failed, something went wrong!");
            }
   }
    }

