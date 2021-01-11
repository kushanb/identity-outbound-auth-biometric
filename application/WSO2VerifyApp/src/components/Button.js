import React from 'react';
import {StyleSheet, TouchableOpacity, Text} from 'react-native';

// [press, setPress] = useState(0);

export const LargeButton = ({title}) => (
  <TouchableOpacity style={styles.largeButtonContainer} activeOpacity={0.8}>
    <Text style={styles.largeButtonText}>{title}</Text>
  </TouchableOpacity>
);

export const LineButton = ({title}) => (
  <TouchableOpacity style={styles.lineButtonContainer} activeOpacity={0.8}>
    <Text style={styles.lineButtonText}>{title}</Text>
  </TouchableOpacity>
);

const styles = StyleSheet.create({
  largeButtonContainer: {
    elevation: 4,
    backgroundColor: '#FD7308',
    borderRadius: 30,
    margin: 10,
  },
  lineButtonContainer: {
    elevation: 4,
    backgroundColor: '#FFF',
    borderWidth: 4,
    borderColor: '#FD7308',
    borderRadius: 30,
    margin: 10,
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
  lineButtonText: {
    fontSize: 18,
    color: '#FD7308',
    fontWeight: 'bold',
    alignSelf: 'center',
    textTransform: 'uppercase',
    paddingVertical: 15,
    paddingHorizontal: 25,
  },
});

// export default LargeButton;
// export LineButton as 'LineButton';

// TODO: fix the button to change colour
