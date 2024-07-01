import api from './api';

export async function login(email: string, password: string): Promise<void> {
  try {
    const response = await api.post('user/login', { email, password });

    if (response.status !== 201) {
      throw new Error('Login failed');
    }

    const token: string = response.data;
    sessionStorage.setItem('token', token);
  } catch (error: any) {
    throw new Error(error.response?.data || 'Login failed');
  }
} 
