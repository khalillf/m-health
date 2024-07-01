import React, { useState, useEffect } from 'react';
import { View, Text, FlatList, StyleSheet, TouchableOpacity } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import axios from 'axios';

const RendezvousScreen = ({ navigation }) => {
  const [appointments, setAppointments] = useState([]);
  const [userId, setUserId] = useState(null);
  const [doctors, setDoctors] = useState({});

  useEffect(() => {
    const getUserData = async () => {
      try {
        const storedUserId = await AsyncStorage.getItem('userId');
        if (storedUserId) {
          const userId = JSON.parse(storedUserId);
          setUserId(userId);
          const response = await axios.get(`http://192.168.11.106:8080/api/patients/${userId}/appointments`);
          const appointmentsData = response.data;
          setAppointments(appointmentsData);

          // Fetch doctor information for each appointment
          const doctorIds = [...new Set(appointmentsData.map(appointment => appointment.doctorId))];
          const doctorsData = await Promise.all(doctorIds.map(async id => {
            const response = await axios.get(`http://192.168.11.106:8080/api/doctors/${id}`);
            return response.data;
          }));

          // Map doctor information by doctor ID
          const doctorsMap = doctorsData.reduce((acc, doctor) => {
            acc[doctor.id] = {
              name: `${doctor.firstName} ${doctor.lastName}`,
              specialty: doctor.specialty,
              email: doctor.email
            };
            return acc;
          }, {});

          setDoctors(doctorsMap);
        }
      } catch (error) {
        console.error('Failed to fetch appointments or doctors', error);
      }
    };
    getUserData();
  }, []);

  // Map specialties to colors
  const specialtyColors = {
    Cardiology: '#FF6347',
    Dermatology: '#8A2BE2',
    Neurology: '#FFD700',
    Pediatrics: '#3CB371',
    // Add more specialties and their corresponding colors here
    Default: '#87CEEB', // Default color if specialty is not in the map
  };

  const getSpecialtyColor = (specialty) => {
    return specialtyColors[specialty] || specialtyColors['Default'];
  };

  const renderAppointment = ({ item }) => {
    const doctor = doctors[item.doctorId];
    const borderColor = getSpecialtyColor(doctor?.specialty);

    return (
      <TouchableOpacity onPress={() => navigation.navigate('AppointmentDetail', { appointmentId: item.id })}>
        <View style={[styles.appointmentCard, { borderColor }]}>
          <Text style={styles.appointmentText}>Doctor: {doctor?.name}</Text>
          <Text style={styles.appointmentText}>Specialty: {doctor?.specialty}</Text>
          <Text style={styles.appointmentText}>Email: {doctor?.email}</Text>
          <Text style={styles.appointmentText}>Date: {item.date}</Text>
          <Text style={styles.appointmentText}>Time: {item.time}</Text>
          <Text style={styles.appointmentText}>Notes: {item.notes}</Text>
        </View>
      </TouchableOpacity>
    );
  };

  return (
    <View style={styles.container}>
      <View style={styles.navBar}>
        <TouchableOpacity onPress={() => navigation.navigate('NewRendezvous')}>
          <Text style={styles.navBarButton}>New Rendezvous</Text>
        </TouchableOpacity>
      </View>
      <FlatList
        data={appointments}
        renderItem={renderAppointment}
        keyExtractor={(item) => item.id.toString()}
        contentContainerStyle={styles.listContainer}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#f8f8f8',
  },
  navBar: {
    height: 60,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#fff',
    borderBottomWidth: 1,
    borderBottomColor: '#ccc',
  },
  navBarButton: {
    fontSize: 18,
    color: '#007BFF',
  },
  listContainer: {
    padding: 10,
  },
  appointmentCard: {
    backgroundColor: '#fff',
    padding: 15,
    marginVertical: 8,
    marginHorizontal: 16,
    borderRadius: 10,
    borderWidth: 2, // Add border width to show the border color
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 5,
    elevation: 3,
  },
  appointmentText: {
    fontSize: 16,
    marginBottom: 5,
  },
});

export default RendezvousScreen;
