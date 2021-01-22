import React from 'react';
import {StyleSheet, TouchableOpacity, Text} from 'react-native';

// [press, setPress] = useState(0);

export const LargeButton = ({title, action}) => (
  <TouchableOpacity
    style={styles.largeButtonContainer}
    activeOpacity={0.8}
    onPress={action}>
    <Text style={styles.largeButtonText}>{title}</Text>
  </TouchableOpacity>
);

export const LineButton = ({title, action}) => (
  <TouchableOpacity
    style={styles.lineButtonContainer}
    activeOpacity={0.8}
    onPress={action}>
    <Text style={styles.lineButtonText}>{title}</Text>
  </TouchableOpacity>
);

export const WhiteButton = ({title, action}) => (
  <TouchableOpacity
    style={styles.whiteButtonContainer}
    activeOpacity={0.8}
    onPress={action}>
    <Text style={styles.whiteButtonText}>{title}</Text>
  </TouchableOpacity>
);

const styles = StyleSheet.create({
  largeButtonContainer: {
    elevation: 0,
    backgroundColor: '#FD7308',
    borderRadius: 30,
    margin: 10,
  },
  lineButtonContainer: {
    elevation: 0,
    backgroundColor: '#FFF',
    borderWidth: 4,
    borderColor: '#FD7308',
    borderRadius: 30,
    margin: 10,
  },
  whiteButtonContainer: {
    elevation: 0,
    backgroundColor: '#FFF',
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
  whiteButtonText: {
    fontSize: 18,
    color: '#585858',
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
