import React from 'react';
import {View, Image, Text, StyleSheet} from 'react-native';
import LargeButton from '../components/Button';
import LineButton from '../components/Button';

const StartScreen = () => (
  <View>
    <View style={styles.logoView}>
      <Image
        source={require('../assets/img/wso2logo.png')}
        style={styles.logo}
      />
      <Text style={styles.logoText}>Verify</Text>
    </View>
    <View style={styles.titleView}>
      <Text style={styles.title}>Get Started</Text>
      <Text style={styles.introText}>
        Start by adding a new account. {'\n'}Press the button below to get
        started.
      </Text>
    </View>
    <View style={styles.button}>
      <LargeButton title="Start" />
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
  introText: {
    fontSize: 16,
    fontFamily: 'Roboto-Light',
    fontWeight: '200',
    textAlign: 'center',
    color: '#555',
    marginTop: 20,
  },
  titleView: {
    marginTop: '20%',
  },
  button: {
    top: '90%',
    paddingHorizontal: '30%',
  },
});

export default StartScreen;
