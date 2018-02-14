package com.example.vittal.studentattendance.api;


import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.MultipartEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class JSONParser {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    // constructor
    public JSONParser() {

    }
    public JSONObject getJSONFromUrl(final String url) {
        // Making HTTP request
        try {
            URL url1 = new URL(url);

            HttpURLConnection connection = (HttpURLConnection)url1.openConnection();
            connection.setRequestProperty("User-Agent", "");
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            is = connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            // Create a BufferedReader to parse through the inputStream.
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            // Declare a string builder to help with the parsing.
            StringBuilder sb = new StringBuilder();
            // Declare a string to store the JSON object data in string form.
            String line;

            // Build the string until null.
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            // Close the input stream.
            is.close();
            // Convert the string builder data to an actual string.
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // Try to parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // Return the JSON Object.
        return jObj;
    }

    // function get json from url by making HTTP POST or GET mehtod
    public JSONObject makeHttpRequest(String url, String method, List<NameValuePair> params) {
        // Making HTTP request
        try {
            // check for request method
            if(method.equals("POST")){
                // request method is POST

                URL url1 = new URL(url);
                HttpURLConnection connection = (HttpURLConnection)url1.openConnection();
                connection.setRequestProperty("User-Agent", "");
                connection.setRequestMethod("POST");
                connection.setDoInput(true);

                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(getQuery(params));
                writer.flush();
                writer.close();
                os.close();

                connection.connect();

                is = connection.getInputStream();

            }else if(method.equals("GET")){

                URL url1 = new URL(url);

                HttpURLConnection urlConnection = (HttpURLConnection) url1.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader isw = new InputStreamReader(in);

                int data = isw.read();
                while (data != -1) {
                    char current = (char) data;
                    data = isw.read();
                    System.out.print(current);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jObj;

    }

    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }
        return result.toString();
    }


    //function for multipat entity
    public JSONObject multipost(String urlString, MultipartEntity reqEntity) {

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(60 * 1000);
            conn.setConnectTimeout(60 * 1000);
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.addRequestProperty("Content-length", reqEntity.getContentLength()+"");
            conn.addRequestProperty(reqEntity.getContentType().getName(), reqEntity.getContentType().getValue());

            OutputStream os = conn.getOutputStream();
            reqEntity.writeTo(conn.getOutputStream());
            os.close();
            conn.connect();

            is = conn.getInputStream();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return readStream(conn.getInputStream());
            }

        } catch (Exception e) {
            Log.e("Odometer activity", "multipart post error " + e + "(" + urlString + ")");
        }
        return null;
    }

    public JSONObject makeHttpJSONPost(String url, String method, JSONObject params) {
        // Making HTTP request
        try {
            // check for request method
            if(method.equals("POST")){
                // request method is POST
                // defaultHttpClient
               /* DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(params));
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();*/

                URL url1 = new URL(url);
                HttpURLConnection connection = (HttpURLConnection)url1.openConnection();
                connection.setReadTimeout(60 * 1000);
                connection.setConnectTimeout(60 * 1000);
                connection.setRequestProperty("User-Agent", "");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestMethod("POST");
                connection.setDoInput(true);

                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(params.toString());
                writer.flush();
                writer.close();
                os.close();

                connection.connect();

                is = connection.getInputStream();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jObj;

    }

    private static JSONObject readStream(InputStream in) {
        /*BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return builder.toString();*/
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jObj;

    }
}