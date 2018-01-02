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
    URL url = null;
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
             url=new URL("http://192.168.43.127:3000/login");
             //This line will open connection to network using http protocol
            urlConnection = (HttpURLConnection) url.openConnection();

            //This line will set method for http connection
            urlConnection.setRequestMethod("POST");

            //This line will enable output means you can write query parameters to outputstream
            //Below you will find BufferedOutputStream to write 'query' variable to o/p stream to send it to server
            urlConnection.setDoOutput(true);

            //This line will enable input that means you will be able to read whatever data returns from the network call
            //Look InputStream code, you will loop through each line from BufferedInputStream
            urlConnection.setDoInput(true);

            //These commented lines are timeout setting, if you're server or internet is slow then how much time should be spend
            //to request for JSON data using API, that it will decide
            //   urlConnection.setReadTimeout(10000 /* milliseconds */);
           // urlConnection.setConnectTimeout(15000 /* milliseconds */);

            //Building POST parameters, think for GET Too, what if get request arrives?
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("username", strings[0])
                    .appendQueryParameter("password", strings[1]);
            String query = builder.build().getEncodedQuery();



            // Append parameters to URL, see this will only work if you set urlConnection.setOutput(true), so that you can write query parameters.
            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            //see writing query
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();
            StringBuffer buffer = new StringBuffer();
            //This will now connect to server, that means actual send button clicked from postman like thing
            urlConnection.connect();

            //Now this will return data, and you will need to read, so urlConnection.setInput(true) then and only then
            //you will read your JSON otherwise exception will be occurred.
            InputStream is=urlConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is,"UTF-8"));
            String line;
            while ((line=bufferedReader.readLine())!=null){
                buffer.append(line);
            }
            if (buffer.length() == 0) {
                 return null;
            }
            String r= buffer.toString(); // Actual response converted to string
            return r; // This returned value will be parameter for OnPostExecute () method argument


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
   protected void onPostExecute(String s){ //see
            super.onPostExecute(s);
            if ( s != null)
                Log.i("Json",s);
            else {
                Log.d("BACKGROUNDWORKER----->","Failed, something went wrong!");
            }
   }
    }

