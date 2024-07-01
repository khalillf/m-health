import React, { useState } from 'react';
import { View, Text, TextInput, StyleSheet, Button, Alert } from 'react-native';
import axios from 'axios'; // Import axios for making HTTP requests
import AsyncStorage from '@react-native-async-storage/async-storage';  // Import AsyncStorage

const LoginScreen = ({ navigation }) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleLogin = () => {
        const userData = {
            username: username,
            password: password
        };

        axios({
            method: 'post',
            url: 'http://192.168.11.106:8080/login',
            data: JSON.stringify(userData),
            headers: {'Content-Type': 'application/json'}
        })
        .then(async response => {
            console.log("Response data:", response.data);

            // Assuming the response structure is like { message: "User logged in successfully", userType: "doctor", userId: 1 }
            if (response.data && response.data.userId) {
                // Store the user ID in AsyncStorage
                await AsyncStorage.setItem('userId', JSON.stringify(response.data.userId));
                console.log("Login successful, user ID:", response.data.userId);
                navigation.navigate('Home');
            } else {
                console.log("Login failed despite status 200: Response data might be unexpected.");
                Alert.alert('Login Failed', 'Please check your credentials and try again.');
            }
        })
        .catch(error => {
            console.error('Login error', error);
            if (error.response) {
                console.error("Error response:", error.response.data);
                Alert.alert('Login Failed', `Server responded with status: ${error.response.status}`);
            } else if (error.request) {
                console.error("Error request:", error.request);
                Alert.alert('Login Failed', 'No response received');
            } else {
                Alert.alert('Login Failed', error.message);
            }
        });
    };

    return (
        <View style={styles.container}>
            <Text style={styles.title}>Login</Text>
            <TextInput
                placeholder="Username"
                value={username}
                onChangeText={setUsername}
                style={styles.input}
            />
            <TextInput
                placeholder="Password"
                value={password}
                onChangeText={setPassword}
                secureTextEntry
                style={styles.input}
            />
            <Button title="Login" onPress={handleLogin} />
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
    }
});

export default LoginScreen;
