import React, { useState, useEffect } from 'react';
import { View, Text, FlatList, StyleSheet } from 'react-native';
import API from '../services/api';

const PatientScreen = () => {
  const [patients, setPatients] = useState([]);

  useEffect(() => {
    fetchPatients();
  }, []);

  const fetchPatients = async () => {
    try {
      const response = await API.get('/patients/');
      setPatients(response.data);
    } catch (error) {
      console.error('Error fetching patients:', error);
    }
  };

  const renderPatient = ({ item }) => (
    <Text style={styles.patient}>{item.username}</Text>
  );

  return (
    <View style={styles.container}>
      <Text style={styles.title}>All Patients</Text>
      <FlatList
        data={patients}
        renderItem={renderPatient}
        keyExtractor={(item, index) => (item.id ? item.id.toString() : index.toString())}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 20,
    backgroundColor: '#f5f5f5',
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 20,
  },
  patient: {
    fontSize: 18,
    marginBottom: 10,
  },
});

export default PatientScreen;
