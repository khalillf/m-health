import React from 'react';
import { createStackNavigator } from '@react-navigation/stack';
import LoginScreen from '../screens/LoginScreen';
import HomeScreen from '../screens/HomeScreen';
import PatientScreen from '../screens/PatientScreen';
import AccountScreen from '../screens/patients/AccountScreen'
import DossierMedicalScreen from '../screens/patients/DossierMedicalScreen'
import RendezVousScreen from '../screens/patients/appointment/RendezVousScreen'
import AppointmentDetailScreen from '../screens/patients/appointment/AppointmentDetailScreen'
import MessageScreen from '../screens/patients/MessageScreen'
import UpdatePasswordScreen from '../screens/patients/UpdatePasswordScreen'
import ResultatLaboScreen from '../screens/patients/ResultatLaboScreen'
import UpdateInfoScreen from '../screens/patients/UpdateInfoScreen'
const Stack = createStackNavigator();

const AppNavigator = () => {
  return (
    <Stack.Navigator initialRouteName="Login">
      <Stack.Screen name="Login" component={LoginScreen} />
      <Stack.Screen name="Home" component={HomeScreen} />
      <Stack.Screen name="Patient" component={PatientScreen} />
      <Stack.Screen name="Account" component={AccountScreen} />
      <Stack.Screen name="Message" component={MessageScreen} />
      <Stack.Screen name="RendezVous" component={RendezVousScreen} />
      <Stack.Screen name="DossierMedical" component={DossierMedicalScreen} />
      <Stack.Screen name="ResultatLabo" component={ResultatLaboScreen} />
      <Stack.Screen name="UpdatePassword" component={UpdatePasswordScreen} />
      <Stack.Screen name="UpdateInfo" component={UpdateInfoScreen} />
      <Stack.Screen name="AppointmentDetail" component={AppointmentDetailScreen} />
    </Stack.Navigator>
  );
};

export default AppNavigator;
