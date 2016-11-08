package com.egco28.a13262;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewCookies extends AppCompatActivity implements SensorEventListener{

    //for intent to main page
    public static final String Fortune_Result = "Result";
    public static final String Fortune_DateTime = "DateTime";
    public static final String Fortune_PicIndex = "PicIndex";
    public static final int DETAIL_REQ_CODE = 1001;

    private SensorManager sensorManager;
    private long lastUpdate;
    FloatingActionButton shakeButton;
    TextView shakeTxt;
    TextView resultTxt;
    TextView dateTxt;
    ImageView cookieImg;
    boolean clicked = false;
    int count = 0;
    String[] fortuneData = {"You can do it!", "You will get A", "You're Lucky", "Don't cry, Life is pain",
                            "Today is not your day","Don't Panic"};
    Fortune fortuneTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cookies);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // set button back to home (line *)

        shakeButton = (FloatingActionButton)findViewById(R.id.fab);
        shakeTxt = (TextView)findViewById(R.id.shakeText);
        resultTxt = (TextView)findViewById(R.id.resultText);
        dateTxt = (TextView)findViewById(R.id.dateText);
        cookieImg = (ImageView)findViewById(R.id.cookieResImg);

        shakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shakeTxt.getText().toString().equals("Save")){
                    Intent intent2 = new Intent();
                    intent2.putExtra(Fortune_Result, fortuneTemp.getResults() );
                    intent2.putExtra(Fortune_DateTime, fortuneTemp.getDateTime() );
                    intent2.putExtra(Fortune_PicIndex, fortuneTemp.getPicIndex() );
                    setResult(RESULT_OK, intent2);
                    finish();
                    Toast.makeText(NewCookies.this, "Saved", Toast.LENGTH_SHORT).show();
                }
                else{
                    shakeTxt.setText("Shaking");
                    clicked = true;
                }
            }
        });

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //these codes for back to CookiesResults page
        int id = item.getItemId();
        if(id == android.R.id.home){ //home is back button id (from line *)
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            getAccelerometer(event);
        }
    }

    private void getAccelerometer(SensorEvent event){
        float[] values = event.values;
        float x = values[0];
        float y = values[1];
        float z = values[2];

        float accelationSquareRoot = (x * x + y * y + z * z) / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        long actualTime = System.currentTimeMillis();
        int random;
         if(clicked) {
             if (accelationSquareRoot > 2 && accelationSquareRoot <= 5) {
                 if (actualTime - lastUpdate < 700) {
                     return;
                 }
                 lastUpdate = actualTime;

                 if(count == 3) {
                    shakeTxt.setText("Save");
                     //random = (int)(Math.random()*5);   -> random number from 0 to 5 but less chance that result is 5
                    random = (int)(Math.random()*6);
                    if(random == 6) { random = 5; } // now we have chance to get these results {0,1,2,3,4,5}

                    String datetimeTotal = getDatetime(); //this method is below of this page
                    fortuneTemp = new Fortune(0,fortuneData[random],datetimeTotal,random);

                    resultTxt.setText("Result : " + fortuneTemp.getResults());
                    dateTxt.setText("Date : " + fortuneTemp.getDateTime());

                    int res = getResources().getIdentifier("pic"+fortuneTemp.getPicIndex(), "drawable", getPackageName() );
                    cookieImg.setImageResource(res);
                    clicked = false;
                 }
                 else{
                     Toast.makeText(this, "Shake continue...", Toast.LENGTH_SHORT).show();
                     count++;
                 }
             }
         }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){
        //this method for will able error with adding implements SensorEventListener
    }

    @Override
    public void onResume(){
        super.onResume();
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public String getDatetime(){
//        Calendar calendar = Calendar.getInstance();
//        int cDay = calendar.get(Calendar.DAY_OF_MONTH);
//        int cMonth = calendar.get(Calendar.MONTH)+1;
//        int cYear = calendar.get(Calendar.YEAR);
//        int cHour = calendar.get(Calendar.HOUR);
//        int cMinute = calendar.get(Calendar.MINUTE);
//        String date = cDay + "/" + cMonth + "/" + cYear;
//        String time = cHour + ":" + cMinute;
//        String datetime = date + "  " + time;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
        String datetime = dateFormat.format(new Date());
        return datetime;
    }

}
