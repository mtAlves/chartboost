package br.mtalves.chartboost;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.flutter.Log;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewRegistry;

import com.chartboost.sdk.Banner.BannerSize;
import com.chartboost.sdk.CBLocation;
import com.chartboost.sdk.ChartboostBanner;
import com.chartboost.sdk.ChartboostBannerListener;
import com.chartboost.sdk.Events.ChartboostCacheError;
import com.chartboost.sdk.Events.ChartboostCacheEvent;
import com.chartboost.sdk.Events.ChartboostClickError;
import com.chartboost.sdk.Events.ChartboostClickEvent;
import com.chartboost.sdk.Events.ChartboostShowError;
import com.chartboost.sdk.Events.ChartboostShowEvent;

import java.util.HashMap;
import java.util.Map;

public class BannerAd extends FlutterActivity implements PlatformView, ChartboostBannerListener {
  final ChartboostBanner chartboostBanner;
  String location = CBLocation.LOCATION_DEFAULT;
  BannerSize bannerSize = BannerSize.STANDARD;
  int bannerWidth = 320;
  int bannerHeight = 50;

  public BannerAd(Context context, HashMap args) {
    try {
      if ((String) args.get("location") != null) {
        this.location = (String) args.get("location");
      }
      switch ((String) args.get("bannerSize")) {
        case "STANDARD":
          break;
        case "MEDIUM":
          bannerSize = BannerSize.MEDIUM;
          bannerWidth = 300;
          bannerHeight = 250;
          break;
        case "LEADERBOARD":
          bannerSize = BannerSize.LEADERBOARD;
          bannerWidth = 728;
          bannerHeight = 90;
          break;
      }
    } catch (Exception e) {
      Log.e("ChartboostBanner", e.toString());
    }

    this.chartboostBanner = new ChartboostBanner(context, location, bannerSize, null);
    this.chartboostBanner.setAutomaticallyRefreshesContent(true);
    this.cacheBanner();
  
    final FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(
      this.dpToPx(context, this.bannerWidth), this.dpToPx(context, this.bannerHeight)
    );
    layout.gravity = Gravity.CENTER;
    chartboostBanner.setLayoutParams(layout);

    this.showBanner();
  }

  int dpToPx(Context context, float dp) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
  }

  @Override
  protected void onStop() {
    super.onStop();
    if(chartboostBanner != null) {
        chartboostBanner.detachBanner();
    }
  }

  @Override
  public void dispose() {

  }

  private void cacheBanner() {
      if(chartboostBanner != null) {
          chartboostBanner.cache();
      }
  }

  private void showBanner() {
      if(chartboostBanner != null) {
          chartboostBanner.show();
      }
  }

  @Override
  public View getView() {
    return this.chartboostBanner;
  }

  @Override
  public void onAdCached(ChartboostCacheEvent chartboostCacheEvent, ChartboostCacheError chartboostCacheError) {
      if(chartboostCacheError != null) {
          Log.e("ChartboostBanner", "Banner cached error: " + chartboostCacheError.code.toString());
      } else {
          Log.i("ChartboostBanner", "Banner cached");
      }
  }

  @Override
  public void onAdShown(ChartboostShowEvent chartboostShowEvent, ChartboostShowError chartboostShowError) {
      if(chartboostShowError != null) {
          Log.e("ChartboostBanner", "Banner shown error: " + chartboostShowError.code.toString());
      } else {
          Log.i("ChartboostBanner", "Banner shown for location: " + chartboostShowEvent.location);
      }
  }

  @Override
  public void onAdClicked(ChartboostClickEvent chartboostClickEvent, ChartboostClickError chartboostClickError) {
      if(chartboostClickError != null) {
          Log.e("ChartboostBanner", "Banner clicked error: " + chartboostClickError.code.toString());
      } else {
          Log.i("ChartboostBanner", "Banner clicked");
      }
  }
}