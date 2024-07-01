import React from 'react';
import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';
import { useNavigation } from '@react-navigation/native';

const PatientNavBar = () => {
  const navigation = useNavigation();

  const navigateToScreen = (screenName) => {
    navigation.navigate(screenName);
  };

  return (
    <View style={styles.navBar}>
      <TouchableOpacity style={styles.navItem} onPress={() => navigateToScreen('Account')}>
        <Text style={styles.navText}>Account</Text>
      </TouchableOpacity>
      <TouchableOpacity style={styles.navItem} onPress={() => navigateToScreen('Message')}>
        <Text style={styles.navText}>Message</Text>
      </TouchableOpacity>
      <TouchableOpacity style={styles.navItem} onPress={() => navigateToScreen('RendezVous')}>
        <Text style={styles.navText}>Rendez Vous</Text>
      </TouchableOpacity>
      <TouchableOpacity style={styles.navItem} onPress={() => navigateToScreen('DossierMedical')}>
        <Text style={styles.navText}>Dossier Médical</Text>
      </TouchableOpacity>
      <TouchableOpacity style={styles.navItem} onPress={() => navigateToScreen('ResultatLabo')}>
        <Text style={styles.navText}>Résultat Labo</Text>
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  navBar: {
    flexDirection: 'row',
    justifyContent: 'space-around',
    alignItems: 'center',
    backgroundColor: '#4CAF50', // Green color
    height: 60,
    elevation: 3, // for shadow on Android
    shadowColor: '#000', // for shadow on iOS
    shadowOpacity: 0.5, // for shadow on iOS
    shadowRadius: 3, // for shadow on iOS
  },
  navItem: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  navText: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#FFFFFF', // White text color
  },
});

export default PatientNavBar;
