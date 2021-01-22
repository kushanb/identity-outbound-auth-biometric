/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React from 'react';
import {
  SafeAreaView,
  StyleSheet,
  ScrollView,
  View,
  Text,
  StatusBar,
} from 'react-native';

import {
  Header,
  LearnMoreLinks,
  Colors,
  DebugInstructions,
  ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';
import {NavigationContainer} from '@react-navigation/native';
import {createStackNavigator} from '@react-navigation/stack';

import StartScreen from './src/screens/StartScreen';
import AddAccountScreen from './src/screens/AddAccountScreen';
import AddAccountSuccessScreen from './src/screens/AddAccountSuccessScreen';
import AddAccountFailedScreen from './src/screens/AddAccountFailedScreen';
import AuthFailedScreen from './src/screens/AuthFailedScreen';
import AccountsScreen from './src/screens/AccountsScreen';
import AuthRequestScreen from './src/screens/AuthRequestScreen';
import ActivityScreen from './src/screens/ActivityScreen';
import MainScreen from './src/screens/MainScreen';
import QRScannerScreen from './src/screens/QRScannerScreen';

import {createBottomTabNavigator} from '@react-navigation/bottom-tabs';

// import TabViewExample from './src/screens/TabScreenTest';
const Tab = createBottomTabNavigator();

const accounts = [];

const Stack = createStackNavigator();

const accountsAvailable = () => {
  console.log(accounts.length);
  return accounts.length == 0 ? 'Main' : 'Start';
};

const App: () => React$Node = () => {
  // const accountsAvailable = 'Start';

  return (
    <>
      <StatusBar barStyle="default" />
      {/* <SafeAreaView> */}
      <NavigationContainer>
        <Stack.Navigator initialRouteName="Main" headerMode="none">
          <Stack.Screen name="Start" component={StartScreen} />
          <Stack.Screen name="Main" component={MainScreen} />
          <Stack.Screen name="Add Account" component={AddAccountScreen} />
          <Stack.Screen name="QR Scanner" component={QRScannerScreen} />
          <Stack.Screen
            name="Add Success"
            component={AddAccountSuccessScreen}
          />
          <Stack.Screen name="Add Failed" component={AddAccountFailedScreen} />

          <Stack.Screen
            name="Authorization Request"
            component={AuthRequestScreen}
          />

          {/* <StartScreen /> */}
          {/* <AddAccountScreen /> */}
          {/* <AddAccountSuccessScreen /> */}
          {/* <AddAccountFailedScreen /> */}
          {/* <AuthFailedScreen /> */}
          {/* <AccountsScreen /> */}
          {/* <AuthRequestScreen /> */}
          {/* <ActivityScreen /> */}
          {/* <NavigationContainer> */}
          {/* <MainScreen /> */}
          {/* <QRScannerScreen /> */}
        </Stack.Navigator>
      </NavigationContainer>

      {/* <TabViewExample /> */}
      {/* </SafeAreaView> */}
    </>
  );
};

const styles = StyleSheet.create({
  scrollView: {
    backgroundColor: Colors.lighter,
  },
  engine: {
    position: 'absolute',
    right: 0,
  },
  body: {
    backgroundColor: Colors.white,
  },
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: '600',
    color: Colors.black,
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
    color: Colors.dark,
  },
  highlight: {
    fontWeight: '700',
  },
  footer: {
    color: Colors.dark,
    fontSize: 12,
    fontWeight: '600',
    padding: 4,
    paddingRight: 12,
    textAlign: 'right',
  },
});

export default App;
