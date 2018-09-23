package com.a9gag.nick.testapplication;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpRequest {

    private static final String TAG = HttpRequest.class.getSimpleName();

    public HttpRequest() {
    }

    public String makeServiceCall() {
        String response = null;
        try {
            URL url = new URL("https://mock-api.9gaginc.com/");
            // Setup a URLConnection with support for HTTP-specific features
            //Returns a URLConnection instance that represents a connection to the remote object referred to by the URL
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // read the response
            //Get the response as an internal buffer array
            InputStream in = new BufferedInputStream(conn.getInputStream());

            // Converts the buffer array to String
            response = convertStreamToString(in);
            //the string could not be parsed.
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
            //TCP error
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
            //I/O exception of some sort has occurred
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
            // Any other errors
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    protected String convertStreamToString(InputStream is) {
        //Reads text from a character-input stream
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
