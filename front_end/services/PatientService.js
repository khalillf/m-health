import API from './api';

const PatientService = {
  createPatient: async (patientData) => {
    return API.post('/patients/', patientData);
  },
  getDoctorsByPatientId: async (patientId) => {
    return API.get(`/patients/${patientId}/doctors`);
  },
  getAllPatients: async () => {
    return API.get('/patients/');
  },
  getPatientsByDoctorId: async (doctorId) => {
    return API.get(`/patients/doctor/${doctorId}`);
  },
  getPatientById: async (id) => {
    return API.get(`/patients/${id}`);
  },
  updatePatient: async (id, patientData) => {
    return API.put(`/patients/${id}`, patientData);
  },
  deletePatient: async (id) => {
    return API.delete(`/patients/${id}`);
  },
  associateDoctorWithPatient: async (patientId, doctorId) => {
    return API.post(`/patients/${patientId}/associate-doctor/${doctorId}`);
  },
  getAllLabResultsByPatientId: async (patientId) => {
    return API.get(`/patients/${patientId}/lab-results`);
  },
  getAppointmentsByPatientId: async (patientId) => {
    return API.get(`/patients/${patientId}/appointments`);
  }
};

export default PatientService;
