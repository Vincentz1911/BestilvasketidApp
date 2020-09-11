package com.fchgroup.bestilvasketidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.fchgroup.bestilvasketidapp.Models.LaundryDTO;
import com.fchgroup.bestilvasketidapp.Models.LoginUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static RequestQueue RQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RQ = com.android.volley.toolbox.Volley.newRequestQueue(this);

        LocalDateTime ldt = LocalDateTime.now();
        Date jdate = new Date();
        ((TextView) findViewById(R.id.txt_output)).setText("Local Date Time: " + jdate);

        VolleyRequests vr = new VolleyRequests(this);
        Button btn = findViewById(R.id.btn_laundry);
        btn.setOnClickListener(v -> vr.requestLaundryFromPIN(RQ, 1234));

        Button loginButton = findViewById(R.id.btn_login);
        loginButton.setOnClickListener(v -> {
            LoginUser loginUser = new LoginUser(
                    ((EditText) findViewById(R.id.edit_email)).getText().toString(),
                    ((EditText) findViewById(R.id.edit_password)).getText().toString());
            //TESTING
            //loginUser = new LoginUser("ch@ch.dk", "Passw0rd");
            //END TESTING
            vr.requestLogin(loginUser);
        });
    }

    void showText(JSONArray response) {
        List<LaundryDTO> laundryDTO = new Gson().fromJson(String.valueOf(response),
                new TypeToken<ArrayList<LaundryDTO>>() {
                }.getType());
        String str = String.valueOf(laundryDTO.get(0).laundry.id);
//        LaundryDTO laundryDTO = new Gson().fromJson(response, LaundryDTO.class);
        //   ((TextView)findViewById(R.id.txt_output)).setText(laundryDTO.get(0).Laundry.Name);
    }
}