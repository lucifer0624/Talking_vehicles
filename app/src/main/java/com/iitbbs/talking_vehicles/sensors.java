package com.iitbbs.talking_vehicles;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.location.LocationListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

import static com.google.firebase.FirebaseApp.initializeApp;
import static java.lang.String.format;

import java.util.Timer;
import java.util.TimerTask;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.DateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class sensors extends Fragment implements SensorEventListener {


    private FirebaseApp mfirebaseApp;

    private Activity activity = getActivity();
    private Context context = getContext();
    private SensorManager mSensorManager;
    private Sensor mSensorAccelerometer;
    private Sensor mGyroscope;
    private Sensor mMagnatemeter;


    private float mAccelerometerX;
    private float mAccelerometerY;
    private float mAccelerometerZ;

    private float mGyroscopeX;
    private float mGyroscopeY;
    private float mGyroscopeZ;

    private float mMagnatemeterX;
    private float mMagnatemeterY;
    private float mMagnatemeterZ;

    private View viewq;




    private String USE_DEFAULT_INFORMATION = "USE_DEFAULT_INFORMATION";

    /*private RecyclerView recyclerView;

    private String[] stringInformation;
    private String[] stringValues;*/

   /* @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        this.activity = activity;
        this.context = context;
    }*/
/*   private DatabaseReference mFirebaseDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://talkingvehicles.firebaseio.com/");
   private String user = "hey";*/

    private DatabaseReference mDatabase;
    long millis;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sensors, container, false);
        viewq = view;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        /*mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();*/
        /* myRef.child("user").setValue("Hello");*/
        mDatabase.child("hey").setValue("hey there talking vehciles");


        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);


        /*List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);*/


        Button button = (Button) viewq.findViewById(R.id.gotomap);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent i;
                    i = new Intent(getContext(),map_activity.class);
                    startActivity(i);

            }
        });



        if (mSensorManager != null) {
            mSensorAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            mMagnatemeter = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            mSensorManager.registerListener((SensorEventListener) this, mSensorAccelerometer, 5000);
            mSensorManager.registerListener((SensorEventListener) this, mGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
            mSensorManager.registerListener((SensorEventListener) this, mMagnatemeter, SensorManager.SENSOR_DELAY_NORMAL);
        }


        /* runThread();*/


        return view;

    }



    /*private void runThread(){
        getActivity().runOnUiThread (new Thread(new Runnable() {
            public void run() {
                while(true){

                }
            }
        }));
    }*/


    @Override
    public final void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        switch (sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:

                mAccelerometerX = event.values[0];
                mAccelerometerY = event.values[1];
                mAccelerometerZ = event.values[2];

              /* new Timer().schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                post(mAccelerometerX,mAccelerometerY,mAccelerometerZ);
                            }
                        },
                        1000
                );*/

                break;
            case Sensor.TYPE_GYROSCOPE:
                mGyroscopeX = event.values[0];
                mGyroscopeY = event.values[1];
                mGyroscopeZ = event.values[2];
               /* mDatabase.child("sensors").child("gyr").child("gyrx").push().setValue(mGyroscopeX);
                mDatabase.child("sensors").child("gyr").child("gyry").push().setValue(mGyroscopeY);
                mDatabase.child("sensors").child("gyr").child("gyrz").push().setValue(mGyroscopeZ);*/
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                mMagnatemeterX = event.values[0];
                mMagnatemeterY = event.values[1];
                mMagnatemeterZ = event.values[2];
                /*mDatabase.child("sensors").child("mag").child("magx").push().setValue(mMagnatemeterX);
                mDatabase.child("sensors").child("mag").child("magy").push().setValue(mMagnatemeterY);
                mDatabase.child("sensors").child("mag").child("magz").push().setValue(mMagnatemeterZ);*/
                break;
        }

        setValuesacc(mAccelerometerX, mAccelerometerY, mAccelerometerZ);
        setValuesgyro(mGyroscopeX, mGyroscopeY, mGyroscopeZ);
        setValuesmag(mMagnatemeterX, mMagnatemeterY, mMagnatemeterZ);

        int delta = (int) (System.currentTimeMillis() - millis);
        if (delta > 500 && delta < 700) {
            post(mAccelerometerX, mAccelerometerY, mAccelerometerZ);
            postgyro(mGyroscopeX, mGyroscopeY, mGyroscopeZ);
            postnag(mMagnatemeterX, mMagnatemeterY, mMagnatemeterZ);
        } else if (delta > 700)
            millis = System.currentTimeMillis();
    }

   /*public void getLocation() {

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        String mprovider;
        mprovider = locationManager.getBestProvider(criteria, false);

        if (mprovider != null && !mprovider.equals("")) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Location location = locationManager.getLastKnownLocation(mprovider);
            locationManager.requestLocationUpdates(mprovider, 15000, 1, (android.location.LocationListener) this);

            if (location != null)
                onLocationChanged(location);
            else
                Toast.makeText(getContext(), "No Location Provider Found Check Your Code", Toast.LENGTH_SHORT).show();
        }
    }*/
   /* public void startReading(){

            new Timer().schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            post(mAccelerometerX, mAccelerometerY, mAccelerometerZ);
                        }
                    },
                    1
            );

    }*/

    public void post(final float x, float y, float z){
        /*final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 5000);*/


        mDatabase.child("sensors").child("acc").child("accx").push().setValue(x);
        mDatabase.child("sensors").child("acc").child("accy").push().setValue(y);
        mDatabase.child("sensors").child("acc").child("accz").push().setValue(z);
    }

    public void postgyro(final float x,float y,float z){
        mDatabase.child("sensors").child("gyr").child("gyrx").push().setValue(x);
        mDatabase.child("sensors").child("gyr").child("gyry").push().setValue(y);
        mDatabase.child("sensors").child("gyr").child("gyrz").push().setValue(z);
    }

    public void postnag(final float x,float y,float z){
        mDatabase.child("sensors").child("mag").child("magx").push().setValue(x);
        mDatabase.child("sensors").child("mag").child("magy").push().setValue(y);
        mDatabase.child("sensors").child("mag").child("magz").push().setValue(z);
    }

    public void setValuesacc(float x,float y,float z){
        DecimalFormat df = new DecimalFormat("#.##");
        TextView accx = viewq.findViewById(R.id.accx);
        accx.setText("Aceelometerx - " + df.format(x));
        TextView accy = viewq.findViewById(R.id.accy);
        accy.setText("Aceelometery - " + df.format(y));
        TextView accz = viewq.findViewById(R.id.accz);
        accz.setText("Aceelometerz - "+  df.format(z));
    }

    public void setValuesgyro(float gx,float gy,float gz){
        DecimalFormat df = new DecimalFormat("#.##");
        TextView gyrox = viewq.findViewById(R.id.Gyrox);
        gyrox.setText("Gyroscopex - " + df.format(gx));
        TextView gyroy = viewq.findViewById(R.id.Gyroy);
        gyroy.setText("Gyroscopey - " +df.format(gy));
        TextView gyroz = viewq.findViewById(R.id.Gyroz);
        gyroz.setText("Gyroscopez - " +df.format(gz));
    }

    public void setValuesmag(float mx,float my,float mz){
        DecimalFormat df = new DecimalFormat("#.##");
        TextView magx = viewq.findViewById(R.id.magx);
        magx.setText("Magnotometerx - "+df.format(mx));
        TextView magy = viewq.findViewById(R.id.magy);
        magy.setText("Magnotometery - "+df.format(my));
        TextView magz = viewq.findViewById(R.id.magz);
        magz.setText("Magnotometerz - "+df.format(mz));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener((SensorEventListener) this, mSensorAccelerometer,5000);
        mSensorManager.registerListener((SensorEventListener) this, mGyroscope,SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener((SensorEventListener) this, mMagnatemeter,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener((SensorEventListener) this);
    }

    @Override
    public void onCreate( @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        millis = System.currentTimeMillis();



    }


}