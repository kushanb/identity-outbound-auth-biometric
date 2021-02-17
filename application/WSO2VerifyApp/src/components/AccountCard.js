import React from 'react';
import {View, StyleSheet, Text, Image, TouchableOpacity} from 'react-native';

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
      <TouchableOpacity style={styles.deleteButton} activeOpacity={0.7}>
        <Image source={require('../assets/img/icon-material-delete.png')} />
      </TouchableOpacity>
    </View>
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
