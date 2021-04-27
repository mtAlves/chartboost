package br.mtalves.chartboost;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;

import io.flutter.Log;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

import com.chartboost.sdk.Chartboost;
import com.chartboost.sdk.CBLocation;
import com.chartboost.sdk.ChartboostDelegate;
import com.chartboost.sdk.Privacy.model.CCPA;
import com.chartboost.sdk.Privacy.model.GDPR;
import com.chartboost.sdk.Libraries.CBLogging.Level;
import com.chartboost.sdk.Model.CBError.CBClickError;
import com.chartboost.sdk.Model.CBError.CBImpressionError;
import com.chartboost.sdk.Tracking.CBAnalytics;
import com.chartboost.sdk.CBImpressionActivity;
import com.chartboost.sdk.Model.CBError;

/** ChartboostPlugin */
public class ChartboostPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware  {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private static ChartboostPlugin _instance;
  private static MethodChannel _channel;
  private static Activity _activity;
  private FlutterPluginBinding flutterPluginBinding;

  static ChartboostPlugin getInstance() {
    return _instance;
  }

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    this.flutterPluginBinding = flutterPluginBinding;
    this.OnAttachedToEngine(flutterPluginBinding.getBinaryMessenger());
  }

  public static void registerWith(Registrar registrar) {
    if (_activity == null) _activity = registrar.activity();
    if (_instance == null) _instance = new ChartboostPlugin();
    _instance.OnAttachedToEngine(registrar.messenger());
  }

  private void OnAttachedToEngine(BinaryMessenger messenger) {
      if (ChartboostPlugin._instance == null)
        ChartboostPlugin._instance = new ChartboostPlugin();
      if (ChartboostPlugin._channel != null)
          return;
      ChartboostPlugin._channel = new MethodChannel(messenger, "chartboost");
      ChartboostPlugin._channel.setMethodCallHandler(this);
  }

  void OnMethodCallHandler(final String method) {
    try {
      ChartboostPlugin._activity.runOnUiThread(new Runnable() {
        @Override
        public void run() {
          _channel.invokeMethod(method, null);
        }
      });
    } catch (Exception e) {
      Log.e("Chartboost", "Error " + e.toString());
    }
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    try {
        switch (call.method) {
            case "init":
                this.init((String) call.argument("appId"), (String) call.argument("appSignature"));
                break;
            case "cacheInterstitial":
              if(call.argument("location") != null)
                this.cacheInterstitial((String) call.argument("location"));
              else 
                this.cacheInterstitial();
              break;
            case "showInterstitial":
              if(call.argument("location") != null)
                this.showInterstitial((String) call.argument("location"));
              else 
                this.showInterstitial();
              break;
        }
        result.success(Boolean.TRUE);
    } catch (Exception e) {
        Log.e("Chartboost", "Error:" + e.toString());
        result.notImplemented();
    }
  }


  private void init(final String appId, final String appSignature) {
    try {
      Chartboost.setDelegate(delegate);
      Chartboost.startWithAppId(flutterPluginBinding.getApplicationContext(), appId, appSignature);
    } catch (Exception e) {
        Log.e("Chartboost", "Initialization error:" + e.toString());
    }
  }

  public ChartboostDelegate delegate = new ChartboostDelegate() {
      @Override
      public boolean shouldRequestInterstitial(String location) {
          Log.i("Chartboost", "Should request interstitial at " + location + "?");
          ChartboostPlugin.getInstance().OnMethodCallHandler("shouldRequestInterstitial");
          return true;
      }

      @Override
      public boolean shouldDisplayInterstitial(String location) {
          Log.i("Chartboost", "Should display interstitial at " + location + "?");
          ChartboostPlugin.getInstance().OnMethodCallHandler("shouldDisplayInterstitial");
          return true;
      }

      @Override
      public void didCacheInterstitial(String location) {
          Log.i("Chartboost", "Interstitial cached at " + location);
          ChartboostPlugin.getInstance().OnMethodCallHandler("didCacheInterstitial");
      }

      @Override
      public void didFailToLoadInterstitial(String location, CBError.CBImpressionError error) {
          Log.i("Chartboost", "Interstitial failed to load at " + location + " with error: " + error.name());
          ChartboostPlugin.getInstance().OnMethodCallHandler("didFailToLoadInterstitial");
      }

      @Override
      public void willDisplayInterstitial(String location) {
          Log.i("Chartboost", "Will display interstitial at " + location);
          ChartboostPlugin.getInstance().OnMethodCallHandler("willDisplayInterstitial");
      }

      @Override
      public void didDismissInterstitial(String location) {
          Log.i("Chartboost", "Interstitial dismissed at " + location);
          ChartboostPlugin.getInstance().OnMethodCallHandler("didDismissInterstitial");
      }

      @Override
      public void didCloseInterstitial(String location) {
          Log.i("Chartboost", "Interstitial closed at " + location);
          ChartboostPlugin.getInstance().OnMethodCallHandler("didCloseInterstitial");
      }

      @Override
      public void didClickInterstitial(String location) {
          Log.i("Chartboost", "Interstitial clicked at " + location );
          ChartboostPlugin.getInstance().OnMethodCallHandler("didClickInterstitial");
      }

      @Override
      public void didDisplayInterstitial(String location) {
          Log.i("Chartboost", "Interstitial displayed at " + location);
          ChartboostPlugin.getInstance().OnMethodCallHandler("didDisplayInterstitial");
      }

      @Override
      public void didFailToRecordClick(String uri, CBError.CBClickError error) {
          Log.i("Chartboost", "Failed to record click " + (uri != null ? uri : "null") + ", error: " + error.name());
          ChartboostPlugin.getInstance().OnMethodCallHandler("didFailToRecordClick");
      }

      @Override
      public boolean shouldDisplayRewardedVideo(String location) {
          Log.i("Chartboost", "Should display rewarded video at " + location + "?");
          ChartboostPlugin.getInstance().OnMethodCallHandler("shouldDisplayRewardedVideo");
          return true;
      }

      @Override
      public void didCacheRewardedVideo(String location) {
          Log.i("Chartboost", "Did cache rewarded video " + location);
          ChartboostPlugin.getInstance().OnMethodCallHandler("didCacheRewardedVideo");
      }

      @Override
      public void didFailToLoadRewardedVideo(String location,
                                            CBError.CBImpressionError error) {
          Log.i("Chartboost", "Rewarded Video failed to load at " + location + " with error: " + error.name());
          ChartboostPlugin.getInstance().OnMethodCallHandler("didFailToLoadRewardedVideo");
      }

      @Override
      public void didDismissRewardedVideo(String location) {
          Log.i("Chartboost", "Rewarded video dismissed at " + location);
          ChartboostPlugin.getInstance().OnMethodCallHandler("didDismissRewardedVideo");
      }

      @Override
      public void didCloseRewardedVideo(String location) {
          Log.i("Chartboost", "Rewarded video closed at " + location);
          ChartboostPlugin.getInstance().OnMethodCallHandler("didCloseRewardedVideo");
      }

      @Override
      public void didClickRewardedVideo(String location) {
        Log.i("Chartboost", "Rewarded video clicked at " + location);
        ChartboostPlugin.getInstance().OnMethodCallHandler("didClickRewardedVideo");
      }

      @Override
      public void didCompleteRewardedVideo(String location, int reward) {
        Log.i("Chartboost", "Rewarded video completed at " + location + "for reward: " + reward);
        ChartboostPlugin.getInstance().OnMethodCallHandler("didCompleteRewardedVideo");
      }

      @Override
      public void didDisplayRewardedVideo(String location) {
        Log.i("Chartboost", "Rewarded video displayed at " + location);
        ChartboostPlugin.getInstance().OnMethodCallHandler("didDisplayRewardedVideo");
      }

      @Override
      public void willDisplayVideo(String location) {
        Log.i("Chartboost", "Will display video at " + location);
        ChartboostPlugin.getInstance().OnMethodCallHandler("willDisplayVideo");
      }

      @Override
      public void didInitialize() {
          Log.i("Chartboost", "SDK is initialized and ready!");
          ChartboostPlugin.getInstance().OnMethodCallHandler("didInitialize");
      }
  };

  private void cacheInterstitial() {
    try {
        Chartboost.cacheInterstitial(CBLocation.LOCATION_DEFAULT);
    } catch (Exception e) {
        Log.e("Chartboost", "cacheInterstitial error:" + e.toString());
    }
  }

  private void cacheInterstitial(String location) {
    try {
        Chartboost.cacheInterstitial(location);
    } catch (Exception e) {
        Log.e("Chartboost", "cacheInterstitial error:" + e.toString());
    }
  }

  private void showInterstitial() {
    try {
        Chartboost.showInterstitial(CBLocation.LOCATION_DEFAULT);
    } catch (Exception e) {
        Log.e("Chartboost", "showInterstitial error:" + e.toString());
    }
  }

  private void showInterstitial(String location) {
    try {
        Chartboost.showInterstitial(location);
    } catch (Exception e) {
        Log.e("Chartboost", "showInterstitial error:" + e.toString());
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
  }

  @Override
  public void onAttachedToActivity(ActivityPluginBinding binding) {
      ChartboostPlugin._activity = binding.getActivity();
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {

  }

  @Override
  public void onReattachedToActivityForConfigChanges(ActivityPluginBinding binding) {
      ChartboostPlugin._activity = binding.getActivity();
  }

  @Override
  public void onDetachedFromActivity() {

  }
}
