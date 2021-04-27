# Chartboost

**Note: Currently only Android platform is supported.**

## Getting Started

### 1. Initialization

Call `Chartboost.init;` during app initialization.

```dart
Chartboost.init('YOUR APP ID', 'YOUR APP SIGNATURE');
```

** OPTIONAL
Preload ads on your users’ devices to boost their experience! Ads will load faster when requested.
Do not call `showInterstitial` directly after `cacheInterstitial` for the same location, or the SDK will fail silently.

```dart
Chartboost.cacheInterstitial();
```

### 2. Show Interstitial Ad

```dart
 listener(ChartboostEventListener event) {
    print('EVENT: $event');
    if(event == ChartboostEventListener.didCacheInterstitial)
        print('Cached interstitial!');
 }
Chartboost.showInterstitial(listener: listener);
```

## Events
```dart
// Called before requesting an interstitial via the Chartboost API server.
shouldRequestInterstitial

// Called before an interstitial will be displayed on the screen.
shouldDisplayInterstitial

// Called after an interstitial has been displayed on the screen.
didDisplayInterstitial

// Called after an interstitial has been loaded from the Chartboost API
// servers and cached locally.
didCacheInterstitial

// Called after an interstitial has attempted to load from the Chartboost API
// servers but failed.
didFailToLoadInterstitial

// Called after an interstitial has been dismissed.
didDismissInterstitial

// Called after an interstitial has been closed.
didCloseInterstitial

// Called after an interstitial has been clicked.
didClickInterstitial

// Called before a rewarded video will be displayed on the screen.
shouldDisplayRewardedVideo

// Called after a rewarded video has been displayed on the screen.
didDisplayRewardedVideo

// Called after a rewarded video has been loaded from the Chartboost API
// servers and cached locally.
didCacheRewardedVideo

// Called after a rewarded video has attempted to load from the Chartboost API
// servers but failed.
didFailToLoadRewardedVideo

// Called after a rewarded video has been dismissed.
didDismissRewardedVideo

// Called after a rewarded video has been closed.
didCloseRewardedVideo

// Called after a rewarded video has been clicked.
didClickRewardedVideo

// Called after a rewarded video has been viewed completely and user is eligible for reward.
didCompleteRewardedVideo

// Implement to be notified of when a video will be displayed on the screen for
// a given CBLocation. You can then do things like mute effects and sounds.
willDisplayVideo

// Called if a click is registered by the app, but the user is not forwarded to the App Store.
didFailToRecordClick

//Called after the SDK has been successfully initialized and video prefetching has been completed.
didInitialize
```

