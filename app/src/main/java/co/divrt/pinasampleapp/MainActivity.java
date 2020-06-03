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
    PinaSdk divrtPinaSdk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PinaConfig pinaConfig = new PinaConfig();
        pinaConfig.setVehicleNumber("FAE6137");
        pinaConfig.setState("New York");
        pinaConfig.setZoneId("12345");
        pinaConfig.setInOrOut("IN");
        pinaConfig.setSimulationMode(true);
        pinaConfig.setDivrtClientKey("DIVRT_KEY");
        pinaConfig.setHelpText("Welcome to Commerce Garage");

        divrtPinaSdk = new PinaSdk(getApplicationContext(),MainActivity.this,pinaConfig);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        divrtPinaSdk.onResume(intent);
    }

    public void onClickEntry(View view) {

        divrtPinaSdk.gateHandler(new PinaInterface() {
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

    @Override
    protected void onPause() {
        super.onPause();
        divrtPinaSdk.onPause();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        divrtPinaSdk.onNewIntent(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        divrtPinaSdk.onResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        divrtPinaSdk.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
