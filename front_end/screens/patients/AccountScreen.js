import React, { useState, useEffect } from 'react';
import { View, Text, StyleSheet, Button, Alert } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import axios from 'axios';

const AccountScreen = ({ navigation }) => {
  const [username, setUsername] = useState('');
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [phone, setPhone] = useState('');
  const [email, setEmail] = useState('');
  const [dateOfBirth, setDateOfBirth] = useState('');
  const [userId, setUserId] = useState(null);

  useEffect(() => {
    const getUserData = async () => {
      try {
        const storedUserId = await AsyncStorage.getItem('userId');
        if (storedUserId) {
          const userId = JSON.parse(storedUserId);
          setUserId(userId);
          const response = await axios.get(`http://192.168.11.106:8080/api/patients/${userId}`);
          const userData = response.data;
          console.log(userData);
          setUsername(userData.username);
          setFirstName(userData.firstName);
          setLastName(userData.lastName);
          setPhone(userData.phone);
          setEmail(userData.email);
          setDateOfBirth(userData.dateOfBirth);
        }
      } catch (error) {
        console.error('Failed to fetch user data', error);
      }
    };
    getUserData();
  }, []);
  
  return (
    <View style={styles.container}>
      <Text style={styles.title}>Account Information</Text>
      <Text>Username: {username}</Text>
      <Text>First Name: {firstName}</Text>
      <Text>Last Name: {lastName}</Text>
      <Text>Phone: {phone}</Text>
      <Text>Email: {email}</Text>
      <Text>Date of Birth: {dateOfBirth}</Text>
      <Button title="Update Info" onPress={() => navigation.navigate('UpdateInfo')} />
      <Button title="Update Password" onPress={() => navigation.navigate('UpdatePassword')} />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 20,
  },
  title: {
    fontSize: 24,
    marginBottom: 20,
  },
});

export default AccountScreen;
