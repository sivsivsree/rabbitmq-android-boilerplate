package com.sivsivsree.reatimeandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    int counter = 0;
    String tripID = "TP" + System.currentTimeMillis();
    String[] tevents = {"start", "stop", "running"};
    TextView data;
    Button button;
    boolean activeTask = false;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        data = findViewById(R.id.data);
        button = findViewById(R.id.button);
        reset();
        timer = new Timer();

    }

    public void addToOfflineQueue(View view) {

        activeTask = !activeTask;


        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                sendDataEventually();
                reset();
            }
        };

        if (activeTask) {
            timer.scheduleAtFixedRate(timerTask, 0, 100);
            button.setText("Stop Sending/adding to Queue");
        } else {
            timer.cancel();
            timer.purge();
            timer = null;
            reset();
            button.setText("Add to Offline Queue");
        }


    }

    public void sendDataEventually() {


        try {
            List<Integer> l = Arrays.asList(getRandomInRange(-180, 180, 6).intValue(),
                    getRandomInRange(-180, 180, 6).intValue());

            ParseObject tripStream = new ParseObject("StreamEvents");

            tripStream.put("TripID", tripID);
            tripStream.put("bearing", Math.floor((Math.random() * 100) + 1));
            tripStream.put("drivingHours", 0);
            tripStream.put("elapsedTime", 0);
            tripStream.put("g", "asdf");
            tripStream.put("km", getRandomInRange(0, 3, 10));
            tripStream.put("l", l);
            tripStream.put("speed", Math.random());
            tripStream.put("stopElapsedTime", 0);
            tripStream.put("stops", Math.floor((Math.random() * 1) + 1));
            tripStream.put("timestamp", System.currentTimeMillis());
            tripStream.put("tripEvent", tevents[(int) Math.floor(Math.random() * tevents.length)]);

            tripStream.saveEventually(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null)
                        Log.e("ERR", e.getMessage(), e);
                    else {
                        reset();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private BigDecimal getRandomInRange(int from, int to, int fixed) {
        return new BigDecimal((Math.random() * (to - from) + from)).setScale(fixed * 1, RoundingMode.HALF_EVEN);
    }

    public void reset(View view) {
        tripID = "TP" + System.currentTimeMillis();
        reset();
    }

    public void reset() {
        try {
            counter = ParseQuery.getQuery("StreamEvents").fromLocalDatastore().count();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        data.setText("TripID:" + tripID + "\nLocations in queue : " + counter + "\n");
    }
}
