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

const AccountsScreen = () => {
  const renderItem = ({item}) => <AccountCard account={item} />;

  return (
    <View>
      <View style={styles.logoView}>
        <Image
          source={require('../assets/img/wso2logo.png')}
          style={styles.logo}
        />
        <Text style={styles.logoText}>Verify</Text>
      </View>
      <View style={styles.titleView}>
        <Text style={styles.title}>Accounts</Text>
      </View>
      <View>
        <FlatList
          data={data}
          renderItem={renderItem}
          keyExtractor={(item) => item.accountId.toString()}
          style={styles.accountsList}
        />
      </View>
      <TouchableOpacity activeOpacity={0.7}>
        <Image
          source={require('../assets/img/add-button.png')}
          style={styles.addButton}
        />
      </TouchableOpacity>
      {/* <View>
        <BottomNavigation screen="Activity" />
      </View> */}
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
    // fontWeight: '300',
    fontFamily: 'Roboto-Light',
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
    elevation: 20,
  },
  titleView: {
    marginTop: '5%',
    marginBottom: '10%',
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
    alignSelf: 'flex-end',
    marginRight: '5%',
  },
  accountsList: {
    height: '58.5%',
  },
});

export default AccountsScreen;
