import React from 'react';
import {View, Image, Text, StyleSheet} from 'react-native';
import LargeButton from '../components/Button';

const AddAccountScreen = () => (
  <View>
    <View style={styles.logoView}>
      <Image
        source={require('../assets/img/wso2logo.png')}
        style={styles.logo}
      />
      <Text style={styles.logoText}>Verify</Text>
    </View>
    <View style={styles.titleView}>
      <Text style={styles.title}>Add Account</Text>
    </View>
    <View style={styles.mainCardView}>
      <View>
        <Image
          source={require('../assets/img/add_account_image.png')}
          style={styles.addAccImage}
        />
      </View>
      <View>
        <Image
          source={require('../assets/img/add_acc_steps.png')}
          style={styles.addAccSteps}
        />
      </View>
      <View style={styles.button}>
        <LargeButton title="Scan QR Code" />
      </View>
    </View>
  </View>
);

const styles = StyleSheet.create({
  buttonRoot: {
    marginTop: 64,
  },
  buttonView: {
    marginVertical: 10,
    marginHorizontal: '20%',
  },
  container: {
    marginVertical: '40%',
    alignContent: 'center',
    flexDirection: 'column',
    justifyContent: 'center',
  },
  logo: {
    alignSelf: 'center',
    width: '25%',
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
    marginTop: '5%',
  },
  title: {
    fontSize: 36,
    fontWeight: '300',
    fontFamily: 'Helvetica',
    textAlign: 'center',
  },
  mainCardView: {
    backgroundColor: '#FFF',
    height: '70%',
    shadowColor: '#000',
    shadowOpacity: 0.2,
    shadowOffset: {height: -2, width: 0},
    shadowRadius: 6,
    marginTop: '15%',
    borderTopLeftRadius: 50,
    borderTopRightRadius: 50,
    padding: '10%',
  },
  titleView: {
    marginTop: '5%',
  },
  button: {
    top: '-35%',
    paddingHorizontal: '20%',
  },
  addAccImage: {
    alignSelf: 'center',
  },
  addAccSteps: {
    alignSelf: 'center',
    width: '85%',
    resizeMode: 'contain',
    top: '-15%',
  },
});

export default AddAccountScreen;
