import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'dart:io';

enum BannerAdSize {
  STANDARD,
  MEDIUM,
  LEADERBOARD,
}

class BannerPx {
  final double width;
  final double height;
  BannerPx(this.width, this.height);
}

class ChartboostBanner extends StatelessWidget {
  final Map<BannerAdSize, String> sizes = {
    BannerAdSize.STANDARD: 'STANDARD',
    BannerAdSize.MEDIUM: 'MEDIUM',
    BannerAdSize.LEADERBOARD: 'LEADERBOARD'
  };
  final Map<BannerAdSize, BannerPx> sizesNum = {
    BannerAdSize.STANDARD: BannerPx(320, 50),
    BannerAdSize.MEDIUM: BannerPx(300, 250),
    BannerAdSize.LEADERBOARD: BannerPx(728, 90)
  };
  final BannerAdSize size;
  final String location;

  ChartboostBanner(this.size, this.location, {Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final AndroidView androidView = AndroidView(
        viewType: '/Banner',
        key: UniqueKey(),
        creationParams: {'bannerSize': sizes[size], 'location': location},
        creationParamsCodec: const StandardMessageCodec(),
        onPlatformViewCreated: (int i) {
          print('Created banner!');
        });
    return Container(
        width: sizesNum[size]?.width,
        height: sizesNum[size]?.height,
        child: Platform.isAndroid ? androidView : null);
  }
}
