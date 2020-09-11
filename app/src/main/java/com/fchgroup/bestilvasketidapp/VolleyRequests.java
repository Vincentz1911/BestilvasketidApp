package com.fchgroup.bestilvasketidapp;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.fchgroup.bestilvasketidapp.Models.LoginUser;
import com.fchgroup.bestilvasketidapp.Models.User;
import com.fchgroup.bestilvasketidapp.Models.UserDTO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.fchgroup.bestilvasketidapp.MainActivity.RQ;

public class VolleyRequests {

    MainActivity activity;

    public VolleyRequests(MainActivity activity) {
        this.activity = activity;
    }

    public VolleyRequests() {
    }

    void requestLogin(LoginUser loginUser) {
        String URL = "https://bestilvasketid.ml/api/user/login";
        final String requestBody = new Gson().toJson(loginUser);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                response -> {
                    UserDTO user = new Gson().fromJson(response, UserDTO.class);
                    Log.i("VOLLEY", response);
                },
                error -> Log.e("VOLLEY", error.toString())) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                return requestBody == null ? null : requestBody.getBytes(StandardCharsets.UTF_8);
            }

            @Override
            protected Response < String > parseNetworkResponse(NetworkResponse response) {
                String parsed;
                try {
                    parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                } catch (UnsupportedEncodingException e) {
                    parsed = new String(response.data);
                }
                return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        RQ.add(stringRequest);
    }

    void requestUserFromID(RequestQueue RQ, int id) {
        String url = "https:bestilvasketid.ml/api/user/" + id;

        RQ.add(new JsonObjectRequest(Request.Method.GET, url, null,
                response -> Log.i("VOLLEY", response.toString()),
                error -> Log.e("VOLLEY", error.toString())));
    }

    void requestLaundryFromPIN(RequestQueue RQ, int pin) {
        String url = "https://bestilvasketid.ml/api/laundry/pin/" + pin;
        RQ.add(new JsonArrayRequest(Request.Method.GET, url, null,
                response -> activity.showText(response),
                error -> Log.e("VOLLEY", error.toString())));
    }
}
