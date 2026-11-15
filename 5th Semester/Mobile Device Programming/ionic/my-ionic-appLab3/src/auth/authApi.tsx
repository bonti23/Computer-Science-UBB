import axios from 'axios';
import { baseUrl, config, withLogs } from '../core';

const authUrl = `http://${baseUrl}/api/auth/login`;
const logoutUrl = `http://${baseUrl}/api/auth/logout`;

export interface AuthProps {
  token: string;
}

export const login = async (username?: string, password?: string): Promise<AuthProps> => {
  return withLogs(axios.post(authUrl, { username, password }, config), 'login');
};

export const logout = async (): Promise<void> => {
  return withLogs(axios.post(logoutUrl, null, config), 'logout');
};
