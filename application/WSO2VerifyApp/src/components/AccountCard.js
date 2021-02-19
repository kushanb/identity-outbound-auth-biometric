import React from 'react';
import {
  View,
  StyleSheet,
  Text,
  Image,
  TouchableOpacity,
  Alert,
} from 'react-native';
import {Accounts} from 'wso2-mobile-auth-sdk';
import AsyncStorage from '@react-native-async-storage/async-storage';

let data;

const AccountCard = ({account}) => {
  return (
    <View style={styles.cardView}>
      <Text style={styles.textOrganization}>
        {account.tenantDomain ? account.tenantDomain : ''}
      </Text>
      <Text style={styles.textCardContent}>
        <Text style={styles.bold}>
          Name{'\t'}:{'\t'}
        </Text>{' '}
        {account.displayName}
      </Text>
      <Text style={styles.textCardContent}>
        <Text style={styles.bold}>
          Username{'\t'}:{'\t'}
        </Text>{' '}
        {account.username}
      </Text>
      <TouchableOpacity
        style={styles.deleteButton}
        activeOpacity={0.7}
        onPress={() => removeAccount(account)}>
        <Image source={require('../assets/img/icon-material-delete.png')} />
      </TouchableOpacity>
    </View>
  );
};

const filterData = (data, deviceId) => {
  console.log('Filter data called');
  console.log(deviceId);
  // console.log('Activity data: ' + JSON.stringify(data));
  return data.filter((item) => item.deviceID != deviceId);
};

const updateAccountsList = async (deviceId) => {
  let updatedList = await AsyncStorage.getItem('accounts').then((accounts) => {
    // console.log(JSON.stringify(data) + ' and ' + accounts);
    let data = JSON.parse(accounts);
    // let indexToRemove = data.findIndex(filterData(data, deviceId));
    // data.pop(indexToRemove);
    // console.log(deviceId);

    console.log(filterData(data, deviceId));
    return filterData(data, deviceId);
  });

  await AsyncStorage.setItem('accounts', JSON.stringify(updatedList));
};

const removeAccount = (account) => {
  console.log('Account to remove: ' + JSON.stringify(account.username));
  Alert.alert(
    'Warning',
    `Are you sure you want to remove account "${account.username}"?`,
    [
      {
        text: 'Cancel',
        onPress: () => console.log('Cancel Pressed'),
        style: 'cancel',
      },
      {
        text: 'OK',
        onPress: () => {
          console.log('OK Pressed');
          let manageAccounts = new Accounts();
          manageAccounts.removeAccount(account.deviceId, account.privateKey);
          // console.log(JSON.stringify(account));
          updateAccountsList(account.deviceID);
        },
      },
    ],
    {cancelable: false},
  );
};

const styles = StyleSheet.create({
  cardView: {
    // width: '90%',
    elevation: 4,
    shadowColor: 'black',
    shadowOffset: {width: 1, height: 2},
    shadowOpacity: 0.2,
    shadowRadius: 8,
    backgroundColor: '#FFF',
    marginHorizontal: '5%',
    paddingHorizontal: '4%',
    paddingVertical: '2%',
    marginVertical: '5%',
    borderRadius: 20,
    flex: 1,
  },
  textOrganization: {
    fontSize: 14,
    fontFamily: 'Roboto-Regular',
    marginVertical: 5,
    color: '#7C7C7C',
  },
  textCardContent: {
    fontSize: 14,
    fontFamily: 'Roboto-Regular',
    marginVertical: 5,
  },
  bold: {
    fontFamily: 'Roboto-Medium',
  },
  deleteButton: {
    alignSelf: 'flex-end',
  },
});

export default AccountCard;
