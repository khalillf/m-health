import React from 'react';
import { View, Text, StyleSheet } from 'react-native';

const ResultatLaboScreen = () => {
  return (
    <View style={styles.container}>
      <Text style={styles.text}>Résultat Labo Screen</Text>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  text: {
    fontSize: 24,
  },
});

export default ResultatLaboScreen;
