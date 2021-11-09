# DIVRT Pinabox Gateway SDK for Android

The divrt Pinabox is a system used to control entry and exit of vehicles into a parking lot using their mobile. It consists of hardware components such as the divrt pinabox gateway as well as software components that run on a mobile. The divrt Pinabox SDK for Android is a software module that when integrated into Android application can interact with the divrt Pinabox gateway hardware.

# Features

1. Integration with a wide array of gate hardware
1. Works in tandem with divrt LPR system on any IP based LPR camera
1. Accurate lane detection to handle multi lane entries/exists seamlessly  
1. Simple software integration that gets your existing parking app **"Pinabox enabled"** within minutes

# Try the sample application
### PinaboxDemoApp 
In order to demonstrate the simplicity of integration and usage of the pinabox sdk, a sample app has been created. This app can be downloaded and tested using the following steps.
#### Download and Compile


1. Download the sample project repo [PinaboxSampleApp](https://github.com/divrt/pinabox_android_sdk_sample)
1. Open Android studio and navigate to project folder
1. Run the project on selected device or simulator

#### Testing the SDK 
The SDK works in tandem with the pinabox gateway. Since the developer may not have the hardware handy, the SDK is built with a demo mode option. In order to activate the simulation mode, navigate to the MainActivity.java in the project just downloaded and uncomment the simulation mode as shown below. Also replace the DIVRT_KEY with the actual client key received from DIVRT. If you do not have one, contact support@divrt.co

```java
PinaConfig pinaConfig = new PinaConfig();
pinaConfig.setSimulationMode(true); // <== UNCOMMENT THIS LINE IN PRODUCTION
pinaConfig.setDivrtClientKey("DIVRT_KEY");// <== Replace with DIVRT client key.
```

1. Compile and run the sample app
2. In the demo mode, the gate open buttons would turn green in ~2 seconds. Click the button to open the gate in simulation mode.
3. The SDK would provide the results of the gate open operation by invoking the onSuccess call back method in the sample app.

# How do I integrate the SDK into my app 

## Installation of SDK

### Requirements
- minSdk 15+ 
- Android Studio 3+
- Android device/emmulator to install app


### Steps to integrate

Make sure your rootlevel build.gradle looks like this


```bash
buildscript {
    
    repositories {
        ...
        google()
        jcenter()
        maven { url 'https://jitpack.io' }
    }
    dependencies {
        ...
    }
}

allprojects {
    repositories {
        ...
        google()
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}
```
Add the following dependency in app level build.gradle
```bash

repositories {
    maven { url "https://jitpack.io" }
}

dependencies {
    ...
    implementation 'com.gitlab.divrt:pina:11.1'
}
```

## Usage
Make sure the activity in which the sdk is meant to integrate looks like follows

```java
DivrtPinaSdk divrtPinaSdk;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
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

    divrtPinaSdk = new PinaSdk(getApplicationContext(),MainActivity.this);
    divrtPinaSdk.setPinaConfig(pinaConfig);

}

@Override
protected void onResume() {
    super.onResume();
    Intent intent = new Intent(this,getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    divrtPinaSdk.onResume(intent);
}

/*Call this method to open divrtpinasdk */
public void onButtonClick(View view) {
     divrtPinaSdk.gateHandler(new PinaInterface() {
            @Override
            public void onSuccess(String message) {
                Toast.makeText(getBaseContext(),"In Gate Opened",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(getBaseContext(),message,Toast.LENGTH_SHORT).show();
            }
            
            @Override
            public void onInfo(String message) {

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

```
Make sure your applications class looks like the following one( Please do create one if you have not used custom Application class used & copy the following code in it )
Example below connects to divrt sandbox. For production, the parameter has to be set to PinaConfig.DivrtSystem.Prod
```java

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        new PinaInitialiser(this, PinaConfig.DivrtSystem.Dev);
        ...
    }
}
```

Add the above created application class in your manifest file
```bash
<application
   android:name=".MyApp"
   ....
 </application>
```

Change logo to match your app by placing image in drawable with name as pina_logo

## Contact
In case of any queries in integration or usage, contact us on support@divrt.co

## License
Copyright (C) DIVRT, Inc - All Rights Reserved

Unauthorized copying of this file, via any medium is strictly prohibited.

Proprietary and confidential

Written by Roopesh <roopesh.a@divrt.co>, April 2021

## Author
roopesh.a@divrt.co
