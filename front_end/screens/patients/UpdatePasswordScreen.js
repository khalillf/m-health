import React, { useState, useEffect } from 'react';
import { View, Text, TextInput, StyleSheet, Button, Alert } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import axios from 'axios';

const UpdatePasswordScreen = () => {
  const [password, setPassword] = useState('');
  const [userId, setUserId] = useState(null);

  useEffect(() => {
    const getUserId = async () => {
      try {
        const storedUserId = await AsyncStorage.getItem('userId');
        if (storedUserId) {
          const userId = JSON.parse(storedUserId);
          setUserId(userId);
        }
      } catch (error) {
        console.error('Failed to fetch user ID', error);
      }
    };
    getUserId();
  }, []);

  const handleUpdatePassword = async () => {
    try {
      const response = await axios.put(`http://192.168.11.106:8080/api/patients/${userId}/password`, { password });
      Alert.alert('Success', 'Password updated successfully');
    } catch (error) {
      console.error('Failed to update password', error);
      Alert.alert('Error', 'Failed to update password');
    }
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Update Password</Text>
      
      <TextInput
        placeholder="New Password"
        value={password}
        onChangeText={setPassword}
        secureTextEntry
        style={styles.input}
      />
      <Button title="Update Password" onPress={handleUpdatePassword} />
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
  input: {
    width: '100%',
    marginVertical: 10,
    padding: 10,
    borderWidth: 1,
    borderColor: '#ccc',
    borderRadius: 5,
  },
});

export default UpdatePasswordScreen;
