package com.ashish.jsonparsingpostsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidNetworking.initialize(this);
        AndroidNetworking.post("https://postman-echo.com/post")
                .addBodyParameter("Name", "Ashish")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("MyRES", response.toString());
                        try {
                            String JsonURL = response.getString("url");
                            JSONObject objHeader = response.getJSONObject("headers");
                            String HostName = objHeader.getString("host");
                            Log.d("Host: ", HostName + " , URL: " + JsonURL);

                            JSONObject objForm = response.getJSONObject("form");
                            String Myname = objForm.getString("Name");
                            Log.d("MyName", Myname);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        anError.printStackTrace();
                        Log.e("Error", anError.toString());
                    }
                });
    }
}