package com.androidintent;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import android.content.pm.PackageManager;
import android.content.Context;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.ActivityEventListener;

@ReactModule(name = AndroidIntentModule.NAME)
public class AndroidIntentModule extends ReactContextBaseJavaModule {
    private final ReactApplicationContext reactContext;
    private Promise intentPromise;
    private Context ctx;

  public static final String NAME = "AndroidIntent";
  
  public AndroidIntentModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    this.ctx = reactContext.getApplicationContext();
    reactContext.addActivityEventListener(mActivityEventListener);
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }

    @ReactMethod
    public void openLink(String url, String packageName, final Promise promise) {
        intentPromise = promise;
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (packageName != null) {
            intent.setPackage(packageName);
        }
        try {
            this.reactContext.startActivityForResult(intent, 5864, null);
        } catch (Exception e) {
            intentPromise.reject("Failed", "NO APP FOUND");
        }
    }

    @ReactMethod
    public void isPackageInstalled(String packageName, Callback cb) {
        PackageManager pm = this.ctx.getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            cb.invoke(true);
        } catch (Exception e) {
            cb.invoke(false);
        }
    }

    private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {
    //listener for activity
     @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (intentPromise != null) {
            if (data != null) {
                String res = data.getStringExtra("response");
                intentPromise.resolve(res);
            } else {
                intentPromise.reject("Failed", "");
            }
        }
    }
    };
}
