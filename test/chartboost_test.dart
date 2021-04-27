import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:chartboost/chartboost.dart';

void main() {
  const MethodChannel channel = MethodChannel('chartboost');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await Chartboost.platformVersion, '42');
  });
}
