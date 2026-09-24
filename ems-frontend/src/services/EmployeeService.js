import apiClient from './AxiosConfig'

const REST_API_BASE_URL = `${import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080'}/api/employees`;

export const listEmployees = () => {
    return apiClient.get(REST_API_BASE_URL);
}

export function createEmployee(employee) {
  return apiClient.post(REST_API_BASE_URL, employee);
}

export function getEmployee(employeeId) {
  return apiClient.get(REST_API_BASE_URL + '/' + employeeId);
}

export const updateEmployee = (employeeId, employee) => {
  return apiClient.put(`${REST_API_BASE_URL}/${employeeId}`, employee);
}

export const deleteEmployee = (employeeId) =>{
  return apiClient.delete(`${REST_API_BASE_URL}/${employeeId}`);
}