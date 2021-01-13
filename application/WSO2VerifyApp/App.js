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

import StartScreen from './src/screens/StartScreen';
import AddAccountScreen from './src/screens/AddAccountScreen';
import AddAccountSuccessScreen from './src/screens/AddAccountSuccessScreen';
import AddAccountFailedScreen from './src/screens/AddAccountFailedScreen';
import AuthFailedScreen from './src/screens/AuthFailedScreen';
import AccountsScreen from './src/screens/AccountsScreen';

const App: () => React$Node = () => {
  return (
    <>
      <StatusBar barStyle="default" />
      <SafeAreaView>
        {/* <NavigationContainer>
          <Stack.Navigator initialRouteName="Home">
            <Stack.Screen name="Home" component={HomeScreen} />
            <Stack.Screen name="Register" component={RegisterScreen} />
            <Stack.Screen name="QRScreen" component={ScanScreen} />
            <Stack.Screen name="Authorization Request" component={AuthScreen} />
          </Stack.Navigator>
        </NavigationContainer> */}
        {/* <StartScreen /> */}
        {/* <AddAccountScreen /> */}
        {/* <AddAccountSuccessScreen /> */}
        {/* <AddAccountFailedScreen /> */}
        {/* <AuthFailedScreen /> */}
        <AccountsScreen />
      </SafeAreaView>
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
