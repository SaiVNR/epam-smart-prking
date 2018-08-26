package com.example.saiteja_suggula.smartparking;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.saiteja_suggula.services.RegisterUser;

import java.util.Calendar;

public class Register extends AppCompatActivity {

    EditText regUserText, regPasswordText, regCnfmPasswordText, regCheckInTimeText, regCheckOutTimeText;
    String userId, password, cnfmPassword, checkInTime, checkOutTime;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        regCheckInTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Register.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if(selectedMinute<10)
                            regCheckInTimeText.setText(selectedHour + ":0" + selectedMinute);
                        else
                            regCheckInTimeText.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Check In Time");
                mTimePicker.show();
            }
        });
        regCheckOutTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Register.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if(selectedMinute<10)
                            regCheckOutTimeText.setText(selectedHour + ":0" + selectedMinute);
                        else
                            regCheckOutTimeText.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Check Out Time");
                mTimePicker.show();
            }
        });

    }

    private void registerUser() {
        userId = regUserText.getText().toString();
        password = regPasswordText.getText().toString();
        cnfmPassword = regCnfmPasswordText.getText().toString();
        checkInTime = regCheckInTimeText.getText().toString();
        checkOutTime = regCheckOutTimeText.getText().toString();
        if (!TextUtils.isEmpty(userId)) {
            if (!TextUtils.isEmpty(password)) {
                if (!TextUtils.isEmpty(cnfmPassword)) {
                    if (password.equals(cnfmPassword)) {
                        if (!TextUtils.isEmpty(checkInTime)) {
                            if (!TextUtils.isEmpty(checkOutTime)) {
                                new RegisterUser().registerUser(userId, password, checkInTime, checkOutTime, Register.this);
                            } else {
                                Toast.makeText(Register.this, "Enter Check Out Time", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Register.this, "Enter Check In Time", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Register.this, "Passwords doesn't match", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Register.this, "Enter Confirm Password", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Register.this, "Enter Password", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Register.this, "Enter UserId", Toast.LENGTH_SHORT).show();
        }
    }

    public void init() {
        registerButton = (Button) findViewById(R.id.registerUser);
        regUserText = (EditText) findViewById(R.id.regUserId);
        regPasswordText = (EditText) findViewById(R.id.regPassword);
        regCnfmPasswordText = (EditText) findViewById(R.id.regCnfmpassword);
        regCheckInTimeText = (EditText) findViewById(R.id.checkInTime);
        regCheckOutTimeText = (EditText) findViewById(R.id.checkOutTime);
    }
}
