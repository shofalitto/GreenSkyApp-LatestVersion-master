package com.example.user.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.tapadoo.alerter.Alerter;

public class InformationActivity extends AppCompatActivity {
    private Button mealsButton, infoButton, menuButton, notificationAlert;
    private User logedInUser;
    private Flight currentFlight;
    private String uId,currentTakeOff,currentLanding;

    private void init() {
        mealsButton = (Button) findViewById(R.id.chooseMeal);
        infoButton = (Button) findViewById(R.id.watchWeather);
        menuButton = (Button)findViewById(R.id.main_menu);
        notificationAlert = (Button) findViewById(R.id.notificationBtn);
        logedInUser = (User) getIntent().getSerializableExtra("logedInUser");
        currentFlight = (Flight) getIntent().getSerializableExtra("flight");
        uId = DataBaseHelper.getInstance().getmAuth().getCurrentUser().getUid();


        if (currentFlight.getPassengersList().get(uId) != 0)
            mealsButton.setEnabled(false);

        mealsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  //need to close this option after choosing it
                Intent na = new Intent(InformationActivity.this, MealsActivity.class);
                na.putExtra("logedInUser", logedInUser);
                na.putExtra("flight", currentFlight);
                finish();
                startActivity(na);
            }
        });

        notificationAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alerter.create(InformationActivity.this)
                        .setTitle("Notification")
                        .setText("Flight departure - status: On Time")
                        .setBackgroundColorRes(R.color.colorAccent)
                        .enableSwipeToDismiss()
                        .setDuration(7000)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {Toast.makeText(InformationActivity.this, "Notification Alert Clicked", Toast.LENGTH_LONG).show();
                            }
                        })
                        .show();
                /*if(currentFlight.getTakeOff()  != getCurrentTakeOff())
                    Alerter.create(InformationActivity.this).setText("Flight departure - status: Delayed");

                if(currentFlight.getLanding()  != getCurrentLanding())
                    Alerter.create(InformationActivity.this).setText("Flight Landing - status: Delayed");*/
            }
        });

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent na = new Intent(InformationActivity.this, FlightInfoAndWeatherActivity.class);
                na.putExtra("logedInUser", logedInUser);
                na.putExtra("flight", currentFlight);
                finish();
                startActivity(na);
            }
        });

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent na = new Intent(InformationActivity.this, UserFlightsActivity.class);
                na.putExtra("user", logedInUser);
                finish();
                startActivity(na);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        init();

    }
    public String getCurrentTakeOff() {
        return currentTakeOff;
    }
    public String getCurrentLanding() {
        return currentLanding;
    }
}
