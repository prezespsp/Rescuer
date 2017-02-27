package com.example.dawtre.rescuer;

import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MorseActivity extends AppCompatActivity {

    private Camera camera;
    private EditText editTextMorse;
    private Camera.Parameters params;

    private static final int LONG_FLASH_DRUGATION = 1400;
    private static final int SHORT_FLASH_DURATION = 750;

    private Map<Character, String> mMorseMapping;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morse);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextMorse = (EditText) findViewById(R.id.editTextMorse);
        mMorseMapping = new HashMap<>();
        getCamera();
        SetMorseCode();
    }

    private void SetMorseCode()
    {
        mMorseMapping.put('a', "SL");
        mMorseMapping.put('b', "LSSS");
        mMorseMapping.put('c', "LSLS");
        mMorseMapping.put('d', "LSS");
        mMorseMapping.put('e', "S");
        mMorseMapping.put('f', "SSLS");
        mMorseMapping.put('g', "LLS");
        mMorseMapping.put('h', "SSSS");
        mMorseMapping.put('i', "SS");
        mMorseMapping.put('j', "SLLL");
        mMorseMapping.put('k', "LSL");
        mMorseMapping.put('l', "SLSS");
        mMorseMapping.put('m', "LL");
        mMorseMapping.put('n', "LS");
        mMorseMapping.put('o', "LLL");
        mMorseMapping.put('p', "SLLS");
        mMorseMapping.put('q', "LLSL");
        mMorseMapping.put('r', "SLS");
        mMorseMapping.put('s', "SSS");
        mMorseMapping.put('t', "L");
        mMorseMapping.put('u', "SSL");
        mMorseMapping.put('v', "SSSL");
        mMorseMapping.put('w', "SLL");
        mMorseMapping.put('x', "LSSL");
        mMorseMapping.put('y', "LSLL");
        mMorseMapping.put('z', "LLSS");
    }

    private void getCamera()
    {
        if (camera == null) {
            try {
                camera = Camera.open();
                params = camera.getParameters();
            } catch (RuntimeException e) {
                Log.e("Camera Error. Failed to Open. Error: ", e.getMessage());
            }
        }
    }

    public void buttonSwitchToMorseClicked( View v)
    {
        ShowMorseCode();
    }

    private void ShowMorseCode()
    {
        String text = editTextMorse.getText().toString();
        int current_time = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            c = Character.toLowerCase(c);
            if (mMorseMapping.containsKey(c)) {
                for (int iter = 0; iter < mMorseMapping.get(c).length(); iter++) {
                    char character = mMorseMapping.get(c).charAt(iter);
                    if (character == 'L') {
                        performFlash(current_time, current_time + LONG_FLASH_DRUGATION);
                        current_time += LONG_FLASH_DRUGATION;
                    } else if (character == 'S') {
                        performFlash(current_time, current_time + SHORT_FLASH_DURATION);
                        current_time += SHORT_FLASH_DURATION;
                    }
                }
            }
        }

        Toast.makeText(MorseActivity.this, "Done!", Toast.LENGTH_LONG).show();
    }

    private void performFlash(int startTime, int milliseconds)
    {
        params = camera.getParameters();

        new Handler().postDelayed(new Runnable()
        {
            public void run() {
                params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(params);
                camera.startPreview();
            }
        }, startTime);

        new Handler().postDelayed(new Runnable()
        {
            public void run() {
                params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(params);
                camera.stopPreview();
            }
        }, milliseconds - 500);
    }
}