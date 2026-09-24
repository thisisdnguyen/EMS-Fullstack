import axios from 'axios'

const AUTH_API_BASE_URL = `${import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080'}/api/auth`;

export const register = (username, password) => {
  return axios.post(`${AUTH_API_BASE_URL}/register`, { username, password });
}

export const login = (username, password) => {
  return axios.post(`${AUTH_API_BASE_URL}/login`, { username, password });
}

export const saveToken = (token) => {
  localStorage.setItem('jwt_token', token);
}

export const getToken = () => {
  return localStorage.getItem('jwt_token');
}

export const removeToken = () => {
  localStorage.removeItem('jwt_token');
}

export const isLoggedIn = () => {
  return !!getToken();
}