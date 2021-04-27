
import 'dart:async';

import 'package:flutter/services.dart';

enum ChartboostEventListener {
  shouldRequestInterstitial,
  shouldDisplayInterstitial,
  didCacheInterstitial,
  didFailToLoadInterstitial,
  willDisplayInterstitial,
  didDismissInterstitial,
  didCloseInterstitial,
  didClickInterstitial,
  didDisplayInterstitial,
  didFailToRecordClick,
  shouldDisplayRewardedVideo,
  didCacheRewardedVideo,
  didFailToLoadRewardedVideo,
  didDismissRewardedVideo,
  didCloseRewardedVideo,
  didClickRewardedVideo,
  didCompleteRewardedVideo,
  didDisplayRewardedVideo,
  willDisplayVideo,
  didInitialize
}

typedef ChartboostListener(ChartboostEventListener listener);

class Chartboost {
  static const MethodChannel _channel =
      const MethodChannel('chartboost');

  static final Map<String, ChartboostEventListener> listeners = {
    'shouldRequestInterstitial': ChartboostEventListener.shouldRequestInterstitial,
    'shouldDisplayInterstitial': ChartboostEventListener.shouldDisplayInterstitial,
    'didCacheInterstitial': ChartboostEventListener.didCacheInterstitial,
    'didFailToLoadInterstitial': ChartboostEventListener.didFailToLoadInterstitial,
    'willDisplayInterstitial': ChartboostEventListener.willDisplayInterstitial,
    'didDismissInterstitial': ChartboostEventListener.didDismissInterstitial,
    'didCloseInterstitial': ChartboostEventListener.didCloseInterstitial,
    'didClickInterstitial': ChartboostEventListener.didClickInterstitial,
    'didDisplayInterstitial': ChartboostEventListener.didDisplayInterstitial,
    'didFailToRecordClick': ChartboostEventListener.didFailToRecordClick,
    'shouldDisplayRewardedVideo': ChartboostEventListener.shouldDisplayRewardedVideo,
    'didCacheRewardedVideo': ChartboostEventListener.didCacheRewardedVideo,
    'didFailToLoadRewardedVideo': ChartboostEventListener.didFailToLoadRewardedVideo,
    'didCloseRewardedVideo': ChartboostEventListener.didCloseRewardedVideo,
    'didClickRewardedVideo': ChartboostEventListener.didClickRewardedVideo,
    'didCompleteRewardedVideo': ChartboostEventListener.didCompleteRewardedVideo,
    'didDisplayRewardedVideo': ChartboostEventListener.didDisplayRewardedVideo,
    'willDisplayVideo': ChartboostEventListener.willDisplayVideo,
    'didInitialize': ChartboostEventListener.didInitialize,
  };

  static Future<void> init(String appId, String appSignature) async {
    try {
      await _channel.invokeMethod('init', {'appId': appId, 'appSignature': appSignature});
    } catch (e) {
      print(e.toString());
    }
  }
  
  static Future<void> handleMethod(MethodCall call, ChartboostListener listener) async {
    try {
      listener(listeners[call.method]);
    } catch (e) {
      print(e.toString());
    }
  }

  static Future<void> cacheInterstitial({ String location }) async {
    try {
      await _channel.invokeMethod('cacheInterstitial', {'location': location});
    } catch (e) {
      print(e.toString());
    }
  }

  static Future<void> showInterstitial({ String location, ChartboostListener listener }) async {
    try {
      _channel.setMethodCallHandler((MethodCall call) async => handleMethod(call, listener));
      await _channel.invokeMethod('showInterstitial', {'location': location});
    } catch (e) {
      print(e.toString());
    }
  }
}
