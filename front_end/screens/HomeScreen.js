import React, { useEffect, useState } from 'react';
import { View, Text, FlatList, ActivityIndicator, StyleSheet } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import PatientService from '../services/PatientService';
import PatientNavBar from './patients/PatientNavBar';

const HomeScreen = () => {
  const [patients, setPatients] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [userId, setUserId] = useState(null); // State to hold the user ID

  useEffect(() => {
    fetchUserId();  // Fetch user ID on component mount
    fetchPatients();
  }, []);

  const fetchUserId = async () => {
    try {
      const storedUserId = await AsyncStorage.getItem('userId');
      if (storedUserId) {
        setUserId(JSON.parse(storedUserId)); // Deserialize the user ID
      }
    } catch (error) {
      console.error('Failed to fetch user ID from storage:', error);
    }
  };

  const fetchPatients = async () => {
    try {
      const response = await PatientService.getAllPatients();
      console.log('Patients response:', response.data); // Log the response data
      setPatients(response.data);
    } catch (error) {
      console.error('Failed to fetch patients:', error);
      setError(error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <ActivityIndicator size="large" color="#0000ff" />;
  }

  if (error) {
    return (
      <View style={styles.container}>
        <Text style={styles.errorText}>Error fetching patients: {error.message}</Text>
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <PatientNavBar />
      <Text style={styles.userIdText}>User ID: {userId}</Text> 
      <FlatList
        data={patients}
        keyExtractor={(item, index) => index.toString()} // Use index as a key if id is not present
        renderItem={({ item }) => (
          <View style={styles.itemContainer}>
            <Text style={styles.itemText}>{item.username}</Text>
          </View>
        )}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  itemContainer: {
    padding: 10,
    borderBottomWidth: 1,
    borderBottomColor: '#ccc',
  },
  itemText: {
    fontSize: 18,
  },
  errorText: {
    color: 'red',
    fontSize: 16,
  },
  userIdText: { // Style for the user ID display
    fontSize: 18,
    marginTop: 20,
    marginBottom: 20,
  },
});

export default HomeScreen;
