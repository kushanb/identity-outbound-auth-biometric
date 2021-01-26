import React from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  TouchableOpacity,
  Linking,
  View,
  Image,
} from 'react-native';
import {
  widthPercentageToDP as wp,
  heightPercentageToDP as hp,
} from 'react-native-responsive-screen';

import QRCodeScanner from 'react-native-qrcode-scanner';
import {RNCamera} from 'react-native-camera';

import {Accounts} from 'wso2-mobile-auth-sdk';

const QRScannerScreen = ({navigation}) => {
  onSuccess = (e) => {
    console.log('Scanned: ', e.data);
    // let result = JSON.parse(e.data);
    // console.log('Object: ', result.one);

    try {
      let account = new Accounts();
      // account.getFCMToken();
      account.addAccount(
        JSON.parse(e.data),
        'fuRr8s_eQrmB88nu5Tz8oa:APA91bFMqYbuzDYyOGK28VoiLHWYXZYzGNVg3tfxfNwKPH-jDIFpNDdUHkmq5wqBUySYZnuHfpycyQvUrPhwV3UZ1YzjUNLvb9gzFZudfJd1N3fWuU0w2nq_hVJc0UPRabvNPuJy8wMB',
      );

      if (e.data) {
        navigation.navigate('Add Success');
      } else {
        navigation.navigate('Add Failed');
      }
    } catch (err) {
      console.log(err);
      navigation.navigate('Add Failed');
    }
  };

  return (
    <View
      style={{
        flexDirection: 'column',
        height: hp('97%'),
        justifyContent: 'center',
        marginTop: '3%',
        flex: 1,
      }}>
      <View style={{flex: 1}}>
        <View style={styles.logoView}>
          <Image
            source={require('../assets/img/wso2logo.png')}
            style={styles.logo}
          />
        </View>
        <View>
          <TouchableOpacity
            onPress={() => {
              console.log('Back Pressed!');
              navigation.goBack();
            }}
            activeOpacity={0.9}
            style={styles.backButton}>
            <Image source={require('../assets/img/material-arrow-back.png')} />
          </TouchableOpacity>
        </View>
        <View style={styles.titleView}>
          <Text style={styles.title}>Scan QR Code</Text>
        </View>
      </View>
      <View style={{flex: 4, alignSelf: 'flex-end'}}>
        <QRCodeScanner
          onRead={onSuccess}
          showMarker={true}
          flashMode={RNCamera.Constants.FlashMode.off}
          // cameraType={RNCamera.Constants.Type.back}
          // topContent={
          //   <Text style={styles.centerText}>
          //     <Text style={styles.textBold}>Scan QR Code</Text>
          //   </Text>
          // }
          // bottomContent={
          //   <TouchableOpacity style={styles.buttonTouchable}>
          //     <Text style={styles.buttonText}></Text>
          //   </TouchableOpacity>
          // }
          cameraStyle={{
            marginTop: hp('3%'),
            height: hp('75%'),
            alignSelf: 'flex-end',
          }}
        />
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  logo: {
    alignSelf: 'center',
    width: '20%',
    resizeMode: 'contain',
  },
  logoText: {
    color: '#f47b20',
    textAlign: 'center',
    fontWeight: '600',
    fontSize: 18,
    top: -30,
    left: 20,
  },
  logoView: {
    flex: 3,
  },
  title: {
    fontSize: 36,
    // fontWeight: '300',
    fontFamily: 'Roboto-Light',
    textAlign: 'center',
  },
  centerText: {
    flex: 1,
    fontSize: 18,
    padding: 32,
    color: '#777',
  },
  textBold: {
    fontWeight: '500',
    color: '#000',
  },
  buttonText: {
    fontSize: 21,
    color: 'rgb(0,122,255)',
  },
  buttonTouchable: {
    padding: 16,
  },
  backButton: {
    left: wp('5%'),
    bottom: hp('5%'),
    alignSelf: 'flex-start',
    position: 'absolute',
  },
});

export default QRScannerScreen;

/* 
TODO: Complete the RN - QR - Scanner installation. 
    react-native-camera & react-native-qr-scanner were installed
*/
