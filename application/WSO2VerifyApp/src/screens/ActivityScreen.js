import React, {useState} from 'react';
import {
  View,
  Image,
  Text,
  StyleSheet,
  FlatList,
  TouchableOpacity,
} from 'react-native';
import ActivityCard from '../components/ActivityCard';
import BottomNavigation from '../components/BottomNavigation';
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
    status: 'Declined',
  },
  {
    accountId: 2,
    username: 'blue@wso2.com',
    displayName: 'Blue Hudsen',
    organization: 'WSO2',
    status: 'Accepted',
  },
  {
    accountId: 3,
    username: 'jonathan@wso2.com',
    displayName: 'Jonathan Swiss',
    organization: 'WSO2',
    status: 'Missed',
  },
  {
    accountId: 4,
    username: 'nike@google.com',
    displayName: 'Nike Crane',
    organization: 'Google',
    device: 'Phone',
    status: 'Accepted',
  },
];

const ActivityScreen = () => {
  const [selectedMenu, setSelectedMenu] = useState('Accepted');
  // const [filteredData, setFilteredData] = useState(0);

  const filterData = () => {
    console.log('Filter data called');
    console.log(selectedMenu);
    // console.log(filteredData);
    return data.filter((item) => item.status == selectedMenu);
  };

  // if (selectedMenu == '') {
  //   setSelectedMenu('Allowed');
  //   setFilteredData(filterData());
  //   console.log('Status: ' + selectedMenu + filteredData);
  // }

  const renderItem = ({item}) => {
    return <ActivityCard account={item} />;
  };

  let selectData = filterData();

  return (
    <View
      style={{
        flexDirection: 'column',
        height: hp('97%'),
        justifyContent: 'center',
      }}>
      <View style={styles.logoView}>
        <Image
          source={require('../assets/img/wso2logo.png')}
          style={styles.logo}
        />
        <Text style={styles.logoText}>Verify</Text>
      </View>
      <View style={styles.titleView}>
        <Text style={styles.title}>Activity</Text>
      </View>
      <View style={{flex: 13, flexDirection: 'column'}}>
        <View
          style={{
            flexDirection: 'row',
            justifyContent: 'space-evenly',
          }}>
          <Text
            style={{
              fontSize: 20,
              fontFamily:
                selectedMenu === 'Accepted' ? 'Roboto-Regular' : 'Roboto-Light',
              borderBottomColor: '#FA668A',
              borderBottomWidth: 3,
            }}
            onPress={() => {
              setSelectedMenu('Accepted');
              // setFilteredData(filterData(data));
              console.log('Accepted Pressed');
              console.log(selectedMenu);
            }}>
            Accepted
          </Text>
          <Text
            style={{
              fontSize: 20,
              fontFamily:
                selectedMenu === 'Declined' ? 'Roboto-Regular' : 'Roboto-Light',
            }}
            onPress={() => {
              setSelectedMenu('Declined');
              // setFilteredData(filterData(data));
            }}>
            Declined
          </Text>
          <Text
            style={{
              fontSize: 20,
              fontFamily:
                selectedMenu === 'Missed' ? 'Roboto-Regular' : 'Roboto-Light',
            }}
            onPress={() => {
              setSelectedMenu('Missed');
              // setFilteredData(filterData(data));
            }}>
            Missed
          </Text>
        </View>
        <View
          style={{
            borderBottomColor: 'black',
            borderBottomWidth: 1,
            width: '80%',
            alignSelf: 'center',
            marginVertical: 5,
          }}
        />
        <FlatList
          data={selectData}
          renderItem={renderItem}
          keyExtractor={(item) => item.accountId}
          extraData={selectedMenu}
          style={styles.accountsList}
        />
      </View>

      <View style={{flex: 2, justifyContent: 'flex-start'}}>
        <BottomNavigation screen="Activity" />
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
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
    flex: 3,
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
    flex: 2,
  },
});

export default ActivityScreen;
