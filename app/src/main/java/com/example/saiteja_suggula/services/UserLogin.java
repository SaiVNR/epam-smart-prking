package com.example.saiteja_suggula.services;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.saiteja_suggula.config.AppConfig;
import com.example.saiteja_suggula.config.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class UserLogin {

    String role;

    public void validateUser(final String userName, final String password, final Context context) {
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response", response);
                if (response.equalsIgnoreCase("Invalid")) {
                    Toast.makeText(context, "Invalid User ID", Toast.LENGTH_SHORT).show();
                } else if (response.equalsIgnoreCase("Fail")) {
                    Toast.makeText(context, "Wrong Credentials", Toast.LENGTH_SHORT).show();
                } else {
                    //SLOT ID NULL - Normal
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String empID = jsonObject.getString("empID");
                        String slotID = jsonObject.getString("slotID");
                        SharedPreferences sharedPreferences = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                        final SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("empID", empID);
                        editor.putString("slotID", slotID);
                        editor.commit();
                        Intent intent = null;
                        if (slotID.equals("null")) {
                            Log.d("User Type", "Normal");
                            //TODO Normal User Navigation
                        } else {
                            Log.d("User Type", "Dedicated");
                            //TODO Dedicated User Navigation
                        }

                        sharedPreferences = context.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
                        final SharedPreferences.Editor editor1 = sharedPreferences.edit();
                        editor1.putString("loginStatus",true+"");
                        editor1.commit();
                        context.startActivity(intent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "Login Error: " + error.getMessage());

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("empID", userName);
                params.put("password", password);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, "Login Request");

    }
}
