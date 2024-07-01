import React, { useState, useEffect } from 'react';
import { View, Text,TextInput, StyleSheet, Button, Alert, TouchableOpacity, Platform } from 'react-native';
import axios from 'axios';
import moment from 'moment';
import DateTimePicker from '@react-native-community/datetimepicker';
const AppointmentDetailScreen = ({ route, navigation }) => {
  const { appointmentId } = route.params;
  const [appointment, setAppointment] = useState(null);
  const [doctor, setDoctor] = useState(null);
  const [showDatePicker, setShowDatePicker] = useState(false);
  const [showTimePicker, setShowTimePicker] = useState(false);

  useEffect(() => {
    const fetchAppointmentDetails = async () => {
      try {
        const response = await axios.get(`http://192.168.11.106:8080/api/appointments/${appointmentId}`);
        const appData = response.data;
        const validDate = new Date(appData.date);
        const validTime = new Date(appData.time); // Assuming time is also stored in a way that new Date can parse it
    
        if (!isNaN(validDate.getTime()) && !isNaN(validTime.getTime())) {
          setAppointment({ ...appData, date: validDate, time: validTime });
        } else {
          console.error("Invalid date or time received", appData.date, appData.time);
          Alert.alert('Error', 'Received invalid date or time data');
        }
    
        const doctorResponse = await axios.get(`http://192.168.11.106:8080/api/doctors/${appData.doctorId}`);
        setDoctor(doctorResponse.data);
      } catch (error) {
        console.error('Failed to fetch appointment details', error);
        Alert.alert('Error', 'Failed to fetch appointment details. Please try again later.');
      }
    };
    
    
    fetchAppointmentDetails();
  }, [appointmentId]);

  const handleUpdate = async () => {
    try {
      const updatedData = {
        date: appointment.date,
        time: appointment.time,
        notes: appointment.notes
      };
      await axios.put(`http://192.168.11.106:8080/api/appointments/${appointmentId}`, updatedData);
      Alert.alert('Success', 'Appointment updated successfully');
    } catch (error) {
      console.error('Failed to update appointment', error);
      Alert.alert('Error', 'Failed to update appointment. Please try again later.');
    }
  };

  const handleSendEmail = () => {
    // Implement email sending functionality here
    Alert.alert('Info', 'Email sent to the doctor');
  };

  const handleDateChange = (event, selectedDate) => {
    const currentDate = selectedDate || appointment.date; // Fallback to current date if user cancels
    setShowDatePicker(false);
    setAppointment(prev => ({ ...prev, date: currentDate }));
  };
  
  const handleTimeChange = (event, selectedTime) => {
    const currentTime = selectedTime || appointment.time; // Fallback to current time if user cancels
    setShowTimePicker(false);
    setAppointment(prev => ({ ...prev, time: currentTime }));
  };
  

  if (!appointment || !doctor) {
    return <View style={styles.container}><Text>Loading...</Text></View>;
  }

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Appointment Details</Text>
      <Text style={styles.label}>Doctor: {doctor.firstName} {doctor.lastName}</Text>
      <Text style={styles.label}>Specialty: {doctor.specialty}</Text>
      <Text style={styles.label}>Email: {doctor.email}</Text>
      
     
        <DateTimePicker
          value={new Date(appointment.date)}
          mode="date"
          display="default"
          onChange={(event, selectedDate) => {
            setShowDatePicker(false);
            setAppointment(prev => ({ ...prev, date: selectedDate }));
          }}
        />
    
        <DateTimePicker
          value={new Date(appointment.time)}
          mode="time"
          display="default"
          onChange={(event, selectedTime) => {
            setShowTimePicker(false);
            setAppointment(prev => ({ ...prev, time: selectedTime }));
          }}
        />

      <TextInput
        style={styles.input}
        value={appointment.notes}
        onChangeText={(notes) => setAppointment(prev => ({ ...prev, notes }))}
        placeholder="Notes"
      />
      <Button title="Update Appointment" onPress={handleUpdate} />
      <TouchableOpacity style={styles.emailButton} onPress={handleSendEmail}>
        <Text style={styles.emailButtonText}>Send Email to Doctor</Text>
      </TouchableOpacity>
    </View>
  );
};


const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    backgroundColor: '#f8f8f8',
  },
  title: {
    fontSize: 24,
    marginBottom: 20,
  },
  label: {
    fontSize: 18,
    marginBottom: 10,
  },
  input: {
    width: '100%',
    padding: 10,
    marginVertical: 10,
    borderWidth: 1,
    borderColor: '#ccc',
    borderRadius: 5,
  },
  emailButton: {
    marginTop: 20,
    padding: 15,
    backgroundColor: '#007BFF',
    borderRadius: 5,
    alignItems: 'center',
  },
  emailButtonText: {
    color: '#fff',
    fontSize: 18,
  },
});

export default AppointmentDetailScreen;
