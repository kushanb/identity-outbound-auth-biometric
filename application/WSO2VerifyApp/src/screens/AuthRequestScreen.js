import React from 'react';
import {View, Image, Text, StyleSheet, TouchableOpacity} from 'react-native';
import {Authorization} from 'wso2-mobile-auth-sdk';

const AuthRequestScreen = ({route, navigation}) => {
  let authData = Authorization.processAuthRequest(route.params);

  return (
    <View>
      {/* Timer view */}
      <View></View>

      {/* Logo view */}
      <View style={styles.logoView}>
        <Image
          source={require('../assets/img/wso2logo.png')}
          style={styles.logo}
        />
      </View>

      {/* Auth request information view */}
      <View>
        <View style={styles.titleView}>
          <Text style={styles.title}>Are you trying to sign in?</Text>
        </View>

        <View>
          <View style={[styles.center, styles.connectionCodeView]}>
            <Text style={[styles.connectionCodeTitle, styles.center]}>
              Connection Code
            </Text>
            <Text style={styles.connectionCode}>
              {authData.connectionCode} {/* 216 765 */}
            </Text>
          </View>
        </View>

        {/* Information cards */}
        <View style={styles.infoSection}>
          <View style={styles.infoCardSection}>
            <View style={styles.infoCardView}>
              <Image
                source={require('../assets/img/awesome-user.png')}
                style={styles.infoCardImage}
              />
              <View style={styles.infoCardTextView}>
                <Text style={styles.infoCardTextBig}>Johnn Doe</Text>
                <Text style={styles.infoCardTextSmall}>john@wso2.com</Text>
              </View>
            </View>

            <View style={styles.infoCardView}>
              <Image
                source={require('../assets/img/awesome-building.png')}
                style={[styles.infoCardImage, {height: '100%'}]}
              />
              <View style={styles.infoCardTextView}>
                <Text style={styles.infoCardTextBig}>WSO2</Text>
              </View>
            </View>

            <View style={styles.infoCardView}>
              <Image
                source={require('../assets/img/material-web-asset.png')}
                style={styles.infoCardImage}
              />
              <View style={styles.infoCardTextView}>
                <Text style={styles.infoCardTextBig}>Pickup-Dispatch</Text>
                <Text style={styles.infoCardTextSmall}>
                  pickup-dispatch.com
                </Text>
              </View>
            </View>
          </View>

          <View style={[styles.infoCardSection, {marginTop: '10%'}]}>
            <View style={styles.infoCardView}>
              <Image
                source={require('../assets/img/material-laptop-mac.png')}
                style={styles.infoCardImage}
              />
              <View style={styles.infoCardTextView}>
                <Text style={styles.infoCardTextBig}>MacBook Pro</Text>
                <Text style={styles.infoCardTextSmall}>Chrome</Text>
              </View>
            </View>

            <View style={styles.infoCardView}>
              <Image
                source={require('../assets/img/material-location.png')}
                style={styles.infoCardImage}
              />
              <View style={styles.infoCardTextView}>
                <Text style={styles.infoCardTextBig}>192.168.1.1</Text>
                <Text style={styles.infoCardTextSmall}>Near Colombo</Text>
              </View>
            </View>
          </View>
        </View>
      </View>

      <View style={styles.responseButtonContainer}>
        <TouchableOpacity
          style={styles.responseButton}
          activeOpacity={0.7}
          onPress={() => {
            Authorization.sendAuthRequest(authData, 'DENIED');
            navigation.navigate('Main');
          }}>
          <Image source={require('../assets/img/deny-button.png')} />
          <Text style={[styles.responseButtonText, {color: '#DB4234'}]}>
            No
          </Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={styles.responseButton}
          onPress={() => {
            Authorization.sendAuthRequest(authData, 'SUCCESSFUL');
            navigation.navigate('Main');
          }}
          activeOpacity={0.7}>
          <Image source={require('../assets/img/accept-button.png')} />
          <Text style={[styles.responseButtonText, {color: '#21AD03'}]}>
            Yes
          </Text>
        </TouchableOpacity>
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
    marginTop: '5%',
  },
  title: {
    fontSize: 20,
    fontFamily: 'Roboto-Light',
    textAlign: 'center',
  },
  titleView: {
    marginTop: '5%',
    marginBottom: '2%',
  },
  connectionCodeView: {
    margin: '5%',
  },
  connectionCodeTitle: {
    color: '#FD7322',
    fontFamily: 'Roboto-Regular',
    fontSize: 16,
    textAlign: 'center',
  },
  connectionCode: {
    fontFamily: 'Roboto-Regular',
    fontSize: 40,
    textAlign: 'center',
    color: '#7C7C7C',
  },
  infoSection: {
    alignSelf: 'center',
  },
  infoCardSection: {
    alignSelf: 'flex-start',
    marginLeft: '5%',
  },
  infoCardView: {
    flexDirection: 'row',
    marginBottom: '6%',
  },
  infoCardImage: {
    marginVertical: '3.5%',
    height: '70%',
    width: '20%',
    alignSelf: 'center',
    resizeMode: 'contain',
  },
  infoCardTextView: {
    marginLeft: '10%',
    justifyContent: 'center',
  },
  infoCardTextBig: {
    fontFamily: 'Roboto-Regular',
    fontSize: 18,
    color: '#000',
  },
  infoCardTextSmall: {
    fontFamily: 'Roboto-Regular',
    fontSize: 16,
    color: '#FD7322',
  },
  responseButtonContainer: {
    flexDirection: 'row',
    justifyContent: 'space-evenly',
    paddingHorizontal: '10%',
    marginTop: '5%',
  },
  responseButton: {
    alignItems: 'center',
    width: '90%',
  },
  responseButtonText: {
    fontFamily: 'Roboto-Bold',
    fontSize: 16,
    marginTop: '3%',
  },
});

export default AuthRequestScreen;
