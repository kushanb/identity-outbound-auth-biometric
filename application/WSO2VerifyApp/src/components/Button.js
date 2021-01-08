import React, {useState} from 'react';
import {
  View,
  Button,
  StyleSheet,
  TouchableOpacity,
  TouchableWithoutFeedback,
  Text,
} from 'react-native';

// [press, setPress] = useState(0);

const LargeButton = ({title}) => (
  <TouchableOpacity style={styles.largeButtonContainer} activeOpacity={0.8}>
    <Text style={styles.largeButtonText}>{title}</Text>
  </TouchableOpacity>
);

const styles = StyleSheet.create({
  largeButtonContainer: {
    elevation: 4,
    backgroundColor: '#FD7308',
    borderRadius: 30,
  },
  largeButtonText: {
    fontSize: 18,
    color: '#fff',
    fontWeight: 'bold',
    alignSelf: 'center',
    textTransform: 'uppercase',
    paddingVertical: 15,
    paddingHorizontal: 25,
  },
});

export default LargeButton;

// TODO: fix the button to change colour
