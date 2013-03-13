
package com.fridge.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser
{
    private JSONObject jsonObject;

    private InputStream inputStream;

    private String jsonString;

    private DefaultHttpClient defaultHttpClient;

    public JSONParser()
    {
        defaultHttpClient = new DefaultHttpClient();
    }

    public JSONObject requestPost(String url, ArrayList<NameValuePair> parameters)
    {
        try
        {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(parameters));
            HttpResponse httpResponse = defaultHttpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();
        }
        catch(UnsupportedEncodingException ex)
        {
            ex.printStackTrace();
        }
        catch(ClientProtocolException ex)
        {
            ex.printStackTrace();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }

        readJSON();
        convertToJSON();

        return jsonObject;
    }

    public JSONObject requestGet(String url, ArrayList<NameValuePair> parameters)
    {
        try
        {
            String parameterString = URLEncodedUtils.format(parameters, "utf-8");
            url += "?" + parameterString;
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = defaultHttpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();
        }
        catch(UnsupportedEncodingException ex)
        {
            ex.printStackTrace();
        }
        catch(ClientProtocolException ex)
        {
            ex.printStackTrace();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }

        readJSON();
        convertToJSON();

        return jsonObject;
    }

    private void readJSON()
    {
        try
        {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,
                    "iso-8859-1"), 8);
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;

            while(bufferedReader.readLine() != null)
                stringBuilder.append(line + "\n");

            inputStream.close();
            jsonString = stringBuilder.toString();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void convertToJSON()
    {
        try
        {
            jsonObject = new JSONObject(jsonString);
        }
        catch(JSONException ex)
        {
            ex.printStackTrace();
        }
    }
}