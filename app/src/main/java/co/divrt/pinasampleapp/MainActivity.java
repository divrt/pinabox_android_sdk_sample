package co.divrt.pinasampleapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import co.divrt.pinasdk.pina.PinaConfig;
import co.divrt.pinasdk.pina.PinaInterface;
import co.divrt.pinasdk.pina.PinaSdk;

public class MainActivity extends AppCompatActivity {
    PinaSdk divrtPinaSdkIn;
    PinaSdk divrtPinaSdkOut;

    String inOrOut = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PinaConfig pinaConfigEntry = new PinaConfig();
        pinaConfigEntry.setVehicleNumber("FAE6137");
        pinaConfigEntry.setState("New York");
        pinaConfigEntry.setZoneId("12345");
        pinaConfigEntry.setInOrOut("IN");
        pinaConfigEntry.setSimulationMode(true);
        pinaConfigEntry.setDivrtClientKey("DIVRT_KEY");
        pinaConfigEntry.setHelpText("Welcome to Commerce Garage");

        divrtPinaSdkIn = new PinaSdk(getApplicationContext(),MainActivity.this,pinaConfigEntry);


        PinaConfig pinaConfigExit = new PinaConfig();
        pinaConfigExit.setVehicleNumber("FAE6137");
        pinaConfigExit.setState("New York");
        pinaConfigExit.setZoneId("12345");
        pinaConfigExit.setInOrOut("OUT");
        pinaConfigExit.setSimulationMode(true);
        pinaConfigExit.setDivrtClientKey("DIVRT_KEY");
        pinaConfigExit.setHelpText("Thank you for exiting Commerce B Garage");

        divrtPinaSdkOut = new PinaSdk(getApplicationContext(),MainActivity.this,pinaConfigExit);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if(inOrOut.equals("IN")){
            divrtPinaSdkIn.onResume(intent);
        }else if(inOrOut.equals("OUT")){
            divrtPinaSdkOut.onResume(intent);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(inOrOut.equals("IN")){
            divrtPinaSdkIn.onResult(requestCode, resultCode, data);
        }else if(inOrOut.equals("OUT")){
            divrtPinaSdkOut.onResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(inOrOut.equals("IN")){
            divrtPinaSdkIn.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }else if(inOrOut.equals("OUT")){
            divrtPinaSdkOut.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(inOrOut.equals("IN")){
            divrtPinaSdkIn.onPause();
        }else if(inOrOut.equals("OUT")){
            divrtPinaSdkOut.onPause();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(inOrOut.equals("IN")){
            divrtPinaSdkIn.onNewIntent(intent);
        }else if(inOrOut.equals("OUT")){
            divrtPinaSdkOut.onNewIntent(intent);
        }
    }

    public void onClickEntry(View view) {

        inOrOut = "IN";
        divrtPinaSdkIn.gateHandler(new PinaInterface() {
            @Override
            public void onSuccess() {
                Toast.makeText(getBaseContext(),"In Gate Opened",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(getBaseContext(),message,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClickExit(View view) {

        inOrOut = "OUT";
        divrtPinaSdkOut.gateHandler(new PinaInterface() {
            @Override
            public void onSuccess() {
                Toast.makeText(getBaseContext(),"Out Gate Opened",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(getBaseContext(),message,Toast.LENGTH_SHORT).show();
            }
        });

    }


}
