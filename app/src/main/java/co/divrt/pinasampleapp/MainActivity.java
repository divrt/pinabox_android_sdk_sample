package co.divrt.pinasampleapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

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

        try {
            PinaConfig pinaConfig = new PinaConfig();

            JSONObject pinaConfigParams = new JSONObject();
            pinaConfigParams.put("zid", "12345");
            pinaConfigParams.put("gateType", "IN");
            pinaConfigParams.put("secret_key", "DIVRT_KEY");
            pinaConfigParams.put("ostype","Android");
            pinaConfigParams.put("uniqueID","PinaTest");
            pinaConfigParams.put("simulationMode",true);

            JSONObject pinaSdkParams = new JSONObject();
            pinaSdkParams.put("helpText","Welcome to ABC Garage");

            pinaConfig.setPinaConfigParams(pinaConfigParams);
            pinaConfig.setPinaSdkParams(pinaSdkParams);
            pinaConfig.setPinaClientParams(new JSONObject());
            pinaConfig.setPinaMiscParams(new JSONObject());

            divrtPinaSdkIn = new PinaSdk(getApplicationContext(),MainActivity.this);
            divrtPinaSdkIn.setPinaConfig(pinaConfig);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            PinaConfig pinaConfig = new PinaConfig();

            JSONObject pinaConfigParams = new JSONObject();
            pinaConfigParams.put("zid", "12345");
            pinaConfigParams.put("gateType", "OUT");
            pinaConfigParams.put("secret_key", "DIVRT_KEY");
            pinaConfigParams.put("ostype","Android");
            pinaConfigParams.put("uniqueID","PinaTest");
            pinaConfigParams.put("simulationMode",true);

            JSONObject pinaSdkParams = new JSONObject();
            pinaSdkParams.put("helpText","Thank you for visiting ABC Garage");

            pinaConfig.setPinaConfigParams(pinaConfigParams);
            pinaConfig.setPinaSdkParams(pinaSdkParams);
            pinaConfig.setPinaClientParams(new JSONObject());
            pinaConfig.setPinaMiscParams(new JSONObject());

            divrtPinaSdkOut = new PinaSdk(getApplicationContext(),MainActivity.this);
            divrtPinaSdkOut.setPinaConfig(pinaConfig);
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
        divrtPinaSdkIn.pinaGateHandler(new PinaInterface() {
            @Override
            public void onSuccess(String s) {
                Toast.makeText(getBaseContext(),"In Gate Opened",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(getBaseContext(),message,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInfo(String s) {

            }
        });
    }

    public void onClickExit(View view) {

        inOrOut = "OUT";
        divrtPinaSdkOut.pinaGateHandler(new PinaInterface() {
            @Override
            public void onSuccess(String s) {
                Toast.makeText(getBaseContext(),"Out Gate Opened",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(getBaseContext(),message,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInfo(String s) {

            }
        });

    }


}
