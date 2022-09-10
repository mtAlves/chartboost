import 'package:chartboost/banner.dart';
import 'package:flutter/material.dart';
import 'dart:async';

import 'package:chartboost/chartboost.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  void initState() {
    super.initState();
    Chartboost.init('618181cedc1e7307f1f532f9',
            '334287e70eec872e50adf8662b445d3f68f1c424')
        .then((value) => Chartboost.cacheInterstitial());
  }

  listener(ChartboostEventListener event) {
    print('EVENT: $event');
    if (event == ChartboostEventListener.didCacheInterstitial)
      print('Cached interstitial!');
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Chartboost Plugin example'),
        ),
        body: Center(
          child: ListView(
            children: <Widget>[
              ElevatedButton(
                onPressed: () async {
                  await Chartboost.showInterstitial(listener: listener);
                },
                child: Text('Show Interstitial'),
              ),
              ChartboostBanner(BannerAdSize.STANDARD, 'Default'),
              Container(
                  alignment: Alignment.center,
                  child: Text('Club de Regatas Vasco da Gama')),
            ],
          ),
        ),
      ),
    );
  }
}
