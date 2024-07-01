import axios from 'axios';

const API = axios.create({
  baseURL: 'http://192.168.11.106:8080/api',
  headers: {
    'Content-Type': 'application/json',
  },
  auth: {
    username: 'user', 
    password: 'pass', 
  },
});

export default API;
