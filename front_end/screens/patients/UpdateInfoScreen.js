import React, { useState, useEffect } from 'react';
import { View, Text, TextInput, StyleSheet, Button, Alert, ScrollView, KeyboardAvoidingView, Platform } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import axios from 'axios';

const AccountScreen = () => {
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

  const handleUpdateInfo = async () => {
    try {
      const userData = {
        username,
        firstName,
        lastName,
        phone,
        email,
        dateOfBirth
      };
      const response = await axios.put(`http://192.168.11.106:8080/api/patients/${userId}`, userData);
      Alert.alert('Success', 'User information updated successfully');
    } catch (error) {
      console.error('Failed to update user information', error);
      Alert.alert('Error', 'Failed to update user information');
    }
  };

  return (
    <KeyboardAvoidingView
      behavior={Platform.OS === "ios" ? "padding" : "height"}
      style={{ flex: 1 }}
    >
      <ScrollView contentContainerStyle={styles.container}
                  keyboardShouldPersistTaps="handled">
        <Text style={styles.title}>Account Screen</Text>
        
        <TextInput
          placeholder="Username"
          value={username}
          onChangeText={setUsername}
          style={styles.input}
        />
        <TextInput
          placeholder="First Name"
          value={firstName}
          onChangeText={setFirstName}
          style={styles.input}
        />
        <TextInput
          placeholder="Last Name"
          value={lastName}
          onChangeText={setLastName}
          style={styles.input}
        />
        <TextInput
          placeholder="Phone"
          value={phone}
          onChangeText={setPhone}
          style={styles.input}
        />
        <TextInput
          placeholder="Email"
          value={email}
          onChangeText={setEmail}
          style={styles.input}
        />
        <TextInput
          placeholder="Date of Birth"
          value={dateOfBirth}
          onChangeText={setDateOfBirth}
          style={styles.input}
        />
        <Button title="Update Info" onPress={handleUpdateInfo} />
      </ScrollView>
    </KeyboardAvoidingView>
  );
};

const styles = StyleSheet.create({
  container: {
    flexGrow: 1,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 20,
  },
  title: {
    fontSize: 24,
    marginBottom: 20,
  },
  input: {
    width: '100%',
    marginVertical: 10,
    padding: 10,
    borderWidth: 1,
    borderColor: '#ccc',
    borderRadius: 5,
  },
});

export default AccountScreen;
