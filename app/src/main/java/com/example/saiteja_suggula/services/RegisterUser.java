package com.example.saiteja_suggula.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.saiteja_suggula.config.AppConfig;
import com.example.saiteja_suggula.config.AppController;

import java.util.HashMap;
import java.util.Map;

public class RegisterUser {
    public void registerUser(final String userId, final String password, final String checkInTime, final String checkOutTime, final Context context) {
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.register, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("Success")) {
                    Toast.makeText(context, "Registration Successful...Redirecting you to Users Page", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = context.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
                    final SharedPreferences.Editor editor1 = sharedPreferences.edit();
                    editor1.putString("loginStatus",true+"");
                    editor1.commit();
                    //TODO Navigate to Normal User page.

                } else if(response.equalsIgnoreCase("Exists")){
                    Toast.makeText(context,"User ID already Registered",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, "Unknown Error occurred..Please try again afer some time", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "Registration Error: " + error.getMessage());
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("empID", userId);
                params.put("password", password);
                params.put("loginTime", checkInTime);
                params.put("logoutTime", checkOutTime);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, "Register Request");
    }
}
