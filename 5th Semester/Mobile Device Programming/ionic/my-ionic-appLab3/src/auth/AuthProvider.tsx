import React, { useCallback, useEffect, useState } from 'react';
import { getLogger } from '../core';
import { login as loginApi, logout as logoutApi } from './authApi';
import { Preferences } from '@capacitor/preferences';

const log = getLogger('AuthProvider');

type LoginFn = (username?: string, password?: string) => void;
type LogoutFn = () => void;

export interface AuthState {
  authenticationError: Error | null;
  isAuthenticated: boolean;
  isAuthenticating: boolean;
  login?: LoginFn;
  logout?: LogoutFn;
  pendingAuthentication?: boolean;
  username?: string;
  password?: string;
  token: string;
  tokenFound: boolean;
}

const TOKEN_KEY = 'auth_token';

const initialState: AuthState = {
  isAuthenticated: false,
  isAuthenticating: false,
  authenticationError: null,
  pendingAuthentication: false,
  token: '',
  tokenFound: false,
};

export const AuthContext = React.createContext<AuthState>(initialState);

interface AuthProviderProps {
  children: React.ReactNode;
}

export const AuthProvider: React.FC<AuthProviderProps> = ({ children }) => {
  const [state, setState] = useState<AuthState>(initialState);
  const { isAuthenticated, isAuthenticating, authenticationError, pendingAuthentication, token, tokenFound } = state;
  const login = useCallback<LoginFn>(loginCallback, []);
  const logout = useCallback<LogoutFn>(logoutCallback, []);

  // 🔹 verifică tokenul salvat la pornire
  useEffect(() => {
    (async () => {
      if (tokenFound) return;
      const { value } = await Preferences.get({ key: TOKEN_KEY });
      if (value) {
        log('Token found on startup');
        setState(s => ({
          ...s,
          token: value,
          tokenFound: true,
          isAuthenticated: true,
        }));
      } else {
        setState(s => ({ ...s, tokenFound: true }));
      }
    })();
  }, [tokenFound]);

  useEffect(authenticationEffect, [pendingAuthentication, tokenFound]);

  const value = { isAuthenticated, login, logout, isAuthenticating, authenticationError, token, tokenFound };
  log('render');

  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  );

  // LOGIN
  function loginCallback(username?: string, password?: string): void {
    log('login callback');
    setState(s => ({
      ...s,
      pendingAuthentication: true,
      username,
      password,
    }));
  }

  // LOGOUT
  async function logoutCallback() {
    try {
      await logoutApi();
    } catch (_) { /* ignore */ }
    await Preferences.remove({ key: TOKEN_KEY });
    setState({ ...initialState });
  }

  // EFECT AUTENTIFICARE
  function authenticationEffect() {
    let canceled = false;
    authenticate();
    return () => { canceled = true; };

    async function authenticate() {
      if (!pendingAuthentication || !tokenFound) return;
      try {
        log('Authenticating...');
        setState(s => ({ ...s, isAuthenticating: true }));
        const { username, password } = state;
        const { token } = await loginApi(username, password);
        if (canceled) return;

        await Preferences.set({ key: TOKEN_KEY, value: token });
        log('Authentication succeeded');
        setState(s => ({
          ...s,
          token,
          pendingAuthentication: false,
          isAuthenticated: true,
          isAuthenticating: false,
          tokenFound: true,
        }));
      } catch (error) {
        if (canceled) return;
        log('Authentication failed', error);
        setState(s => ({
          ...s,
          authenticationError: error as Error,
          pendingAuthentication: false,
          isAuthenticating: false,
        }));
      }
    }
  }
};
