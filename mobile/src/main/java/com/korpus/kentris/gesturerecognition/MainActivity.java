package com.korpus.kentris.gesturerecognition;

import android.app.usage.UsageEvents;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor magnetometer;
    private float[] gravity;
    private float[] geomagnetic;
    private float azimut, pitch, roll;
    private EventData eventData;
    private ArrayList<EventData> eventDataList;
    private ArrayList<String> eventDataStringList;
    private ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        eventData = new EventData();

        // String[] items = {"one, two, three", "four, five, six"};
        eventDataList = new ArrayList<EventData>();
        eventDataStringList = new ArrayList<String>();
        ListView listView = (ListView) findViewById(R.id.listView);
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, eventDataStringList);
        listView.setAdapter(listAdapter);


    }

    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            gravity = event.values;
            eventData.setAccel(event.values[0], event.values[1], event.values[2]);
        }
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            geomagnetic = event.values;
            eventData.setMagnet(event.values[0], event.values[1], event.values[2]);
        }

        // if (gravity != null && geomagnetic != null) {
        if (eventData.getAccel() != null && eventData.getMagnet() != null) {
            float R[] = new float[9];
            float I[] = new float[9];
            // boolean success = SensorManager.getRotationMatrix(R, I, gravity, geomagnetic);
            boolean success = SensorManager.getRotationMatrix(R, I, eventData.getAccel(), eventData.getMagnet());
            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                /*azimut = orientation[0];
                pitch = orientation[1];
                roll = orientation[2];*/
                eventData.setOrientation(orientation[0], orientation[1], orientation[2]);
            }

            eventDataList.add(eventData);
            eventDataStringList.add(eventData.getDetailedPrintOut());
            if( eventDataStringList.size() > 20){
                eventDataStringList.remove(0);
            }

            listAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
