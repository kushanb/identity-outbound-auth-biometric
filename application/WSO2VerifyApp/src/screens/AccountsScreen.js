import React from 'react';
import {
  View,
  Image,
  Text,
  StyleSheet,
  FlatList,
  TouchableOpacity,
} from 'react-native';
import AccountCard from '../components/AccountCard';
import BottomNavigation from '../components/BottomNavigation';
import {NavigationContainer} from '@react-navigation/native';
import {createStackNavigator} from '@react-navigation/stack';

import {
  widthPercentageToDP as wp,
  heightPercentageToDP as hp,
} from 'react-native-responsive-screen';

const data = [
  {
    accountId: 1,
    username: 'kushanb@wso2.com',
    displayName: 'Kushan Bhareti',
    organization: 'WSO2',
  },
  {
    accountId: 2,
    username: 'blue@wso2.com',
    displayName: 'Blue Hudsen',
    organization: 'WSO2',
  },
  {
    accountId: 3,
    username: 'jonathan@wso2.com',
    displayName: 'Jonathan Swiss',
    organization: 'WSO2',
  },
  {
    accountId: 4,
    username: 'nike@google.com',
    displayName: 'Nike Crane',
    organization: 'Google',
    device: 'Phone',
  },
];

const AccountsScreen = ({navigation}) => {
  const renderItem = ({item}) => <AccountCard account={item} />;

  return (
    <View
      style={{
        flexDirection: 'column',
        height: hp('97%'),
        justifyContent: 'center',
        marginTop: '3%',
      }}>
      <View style={styles.logoView}>
        <Image
          source={require('../assets/img/wso2logo.png')}
          style={styles.logo}
        />
      </View>
      <View style={styles.titleView}>
        <Text style={styles.title}>Accounts</Text>
      </View>
      <View style={{flex: 13, flexDirection: 'column'}}>
        <FlatList
          data={data}
          renderItem={renderItem}
          keyExtractor={(item) => item.accountId.toString()}
          style={styles.accountsList}
        />
      </View>
      <TouchableOpacity
        activeOpacity={0.9}
        onPress={() => {
          console.log('Add button pressed');
          navigation.navigate('Add Account');
        }}
        style={styles.addButton}>
        <Image source={require('../assets/img/add-button.png')} />
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  buttonRoot: {
    marginTop: 64,
  },
  buttonView: {
    marginVertical: 10,
    marginHorizontal: '10%',
  },
  container: {
    marginVertical: '40%',
    alignContent: 'center',
    flexDirection: 'column',
    justifyContent: 'center',
  },
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
  titleView: {
    flex: 2,
  },
  button: {
    top: '-35%',
    paddingHorizontal: '10%',
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
  addButton: {
    position: 'absolute',
    bottom: hp('10%'),
    right: wp('5%'),
  },
  accountsList: {
    flexGrow: 1,
  },
});

export default AccountsScreen;
