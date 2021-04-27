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
    Chartboost.init('6086e39e215211081fc6dfe8', '8eb71a4126b098c0f85b2073e2cbf37db25d3045')
      .then((value) => Chartboost.cacheInterstitial());
  }

  listener(ChartboostEventListener event) {
    print('EVENT: $event');
    if(event == ChartboostEventListener.didCacheInterstitial)
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
            ],
          ),
        ),
      ),
    );
  }
}
