import React from 'react';
import {View, Image, Text, StyleSheet} from 'react-native';

const QRScannerScreen = () => {
  return (
    <View>
      <QRCodeScanner
        onRead={onSuccess}
        showMarker={true}
        flashMode={RNCamera.Constants.FlashMode.off}
        //cameraType={RNCamera.Constants.Type.back}
        topContent={
          <Text style={styles.centerText}>
            <Text style={styles.textBold}>Scan QR Code</Text>
          </Text>
        }
        bottomContent={
          <TouchableOpacity style={styles.buttonTouchable}>
            <Text style={styles.buttonText}></Text>
          </TouchableOpacity>
        }
      />
    </View>
  );
};

export default QRScannerScreen;

/* 
TODO: Complete the RN - QR - Scanner installation. 
    react-native-camera & react-native-qr-scanner were installed
*/
