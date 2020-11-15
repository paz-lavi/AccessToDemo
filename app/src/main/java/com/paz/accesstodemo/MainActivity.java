package com.paz.accesstodemo;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.paz.accesstolib.DialogListener;
import com.paz.accesstolib.GiveMe;
import com.paz.accesstolib.GrantListener;


public class MainActivity extends AppCompatActivity {
   private MaterialButton main_BTN_requestPermissions, main_BTN_requestPermissionsWithForce, main_BTN_requestPermissionsWithDialog;
  private   GiveMe giveMe;
  private final String TAG = "MY_TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        giveMe = new GiveMe(this);
        giveMe.setDebug(true);

        setViews();
        setListeners();

    }


    private final GrantListener listener = new GrantListener() {
        @Override
        public void onGranted(boolean allGranted) {
            Log.d(TAG, "onGranted = " + allGranted);

        }

        @Override
        public void onNotGranted(String[] permissions) {
            Log.d(TAG, "onNotGranted");
            for (String permission : permissions) {
                Log.d(TAG, permission);

            }
        }

        @Override
        public void onNeverAskAgain(String[] permissions) {
            Log.d(TAG, "onNeverAskAgain");
            giveMe.askPermissionsFromSetting("give me permissions",permissions ,new DialogListener() {
                @Override
                public void onPositiveButton() {
                    Log.d(TAG, "onReTry");
                }

                @Override
                public void onNegativeButton() {
                    Log.d(TAG, "onImSure");

                }
            });


        }

    };

    private final GrantListener listener2 = new GrantListener() {
        @Override
        public void onGranted(boolean allGranted) {
            Log.d(TAG, "onGranted = " + allGranted);

        }

        @Override
        public void onNotGranted(String[] permissions) {
            Log.d(TAG, "onNotGranted");
            for (String permission : permissions) {
                Log.d(TAG, permission);

            }
        }

        @Override
        public void onNeverAskAgain(String[] permissions) {
            Log.d("pttt", "onNeverAskAgain");

        }
    };

    private void toast() {
        Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
    }

    private void onRequestPermissions() {

        giveMe.requestPermissions(new String[]{Manifest.permission.CAMERA}, listener);

    }

    private void onRequestPermissionsWithDialog() {
        giveMe.requestPermissionsWithDialog(new String[]{ Manifest.permission.WRITE_CALENDAR, Manifest.permission.READ_CALENDAR,
                        Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS,},listener2,
                "Permission needed", "need permission for testing the functions", new DialogListener() {
                    @Override
                    public void onPositiveButton() {
                        Log.d(TAG, "agree");

                    }

                    @Override
                    public void onNegativeButton() {
                        Log.d(TAG, "decline");

                    }
                });

    }

    private void onRequestPermissionsWithForce() {
        giveMe.requestPermissionsWithForce(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,} , listener2,"need permission for testing the functions" ,  new DialogListener() {
            @Override
            public void onPositiveButton() {
                Log.d(TAG, "agree");

            }

            @Override
            public void onNegativeButton() {
                Log.d(TAG, "decline");

            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        giveMe.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        giveMe.onActivityResult(requestCode, resultCode, data);
    }


    private void setListeners() {
        main_BTN_requestPermissions.setOnClickListener(e -> onRequestPermissions());
        main_BTN_requestPermissionsWithForce.setOnClickListener(e -> onRequestPermissionsWithForce());
        main_BTN_requestPermissionsWithDialog.setOnClickListener(e -> onRequestPermissionsWithDialog());

    }


    private void setViews() {
        main_BTN_requestPermissions = findViewById(R.id.main_BTN_requestPermissions);
        main_BTN_requestPermissionsWithForce = findViewById(R.id.main_BTN_requestPermissionsWithForce);
        main_BTN_requestPermissionsWithDialog = findViewById(R.id.main_BTN_requestPermissionsWithDialog);

    }
}