import React, { useCallback, useEffect, useReducer, useContext } from 'react';
import { getLogger } from '../core';
import { getAllBooks, updateBookAPI, createBookAPI, deleteBookAPI, newWebSocket } from './BookApi';
import { BookProps } from './BookProps';
import { AuthContext } from '../auth';
import { useNetwork } from '../pages/useNetwork';
import { useIonToast } from "@ionic/react";
import { Preferences } from '@capacitor/preferences';

const log = getLogger('BookProvider');

type UpdateBookFn = (book: BookProps) => Promise<void>;
type DeleteBookFn = (id: string) => Promise<void>;

interface BooksState {
  books?: BookProps[];
  fetching: boolean;
  fetchingError?: Error | null;
  updating: boolean;
  updateError?: Error | null;
  updateBook?: UpdateBookFn;
  addBook?: UpdateBookFn;
  deleteBook?: DeleteBookFn;
  successMessage?: string;
  closeShowSuccess?: () => void;
}

interface ActionProps {
  type: string;
  payload?: any;
}

const initialState: BooksState = {
  fetching: false,
  updating: false,
};

const reducer = (state: BooksState, { type, payload }: ActionProps): BooksState => {
  switch (type) {
    case 'FETCH_BOOKS_STARTED':
      return { ...state, fetching: true, fetchingError: null };
    case 'FETCH_BOOKS_SUCCEEDED':
      return { ...state, fetching: false, books: payload.books };
    case 'FETCH_BOOKS_FAILED':
      return { ...state, fetching: false, fetchingError: payload.error };
    case 'UPDATE_BOOK_STARTED':
    case 'CREATE_BOOK_STARTED':
    case 'DELETE_BOOK_STARTED':
      return { ...state, updating: true, updateError: null };
    case 'UPDATE_BOOK_SUCCEEDED': {
      const books = [...(state.books || [])];
      const updated = payload.book;
      const i = books.findIndex(it => it._id === updated._id);
      if (i !== -1) books[i] = updated; else books.unshift(updated);
      return { ...state, books, updating: false };
    }
    case 'CREATE_BOOK_SUCCEEDED': {
      const books = [...(state.books || [])];
      const added = payload.book;
      if (!books.find(it => it._id === added._id)) books.unshift(added);
      return { ...state, books, updating: false };
    }
    case 'DELETE_BOOK_SUCCEEDED': {
      const books = (state.books || []).filter(it => it._id !== payload.book._id);
      return { ...state, books, updating: false };
    }
    case 'UPDATE_BOOK_FAILED':
    case 'CREATE_BOOK_FAILED':
    case 'DELETE_BOOK_FAILED':
      return { ...state, updating: false, updateError: payload.error };
    case 'SHOW_SUCCESS_MESSAGE':
      return { ...state, successMessage: payload.successMessage };
    case 'HIDE_SUCCESS_MESSAGE':
      return { ...state, successMessage: undefined };
    default:
      return state;
  }
};

export const BookContext = React.createContext<BooksState>(initialState);

interface BookProviderProps {
  children: React.ReactNode;
}

export const BookProvider: React.FC<BookProviderProps> = ({ children }) => {
  const [state, dispatch] = useReducer(reducer, initialState);
  const { token } = useContext(AuthContext);
  const { networkStatus } = useNetwork();
  const [toast] = useIonToast();

  // ✅ Efect: fetch books
  useEffect(() => {
    if (token?.trim()) {
      fetchBooks();
      setupWebSocket();
    }
  }, [token]);

  // ✅ Efect: sync offline books
  useEffect(() => {
    if (networkStatus.connected && token) {
      runPendingOperations();
    }
  }, [networkStatus.connected, token]);

  const updateBook = useCallback<UpdateBookFn>(async book => {
    try {
      dispatch({ type: 'UPDATE_BOOK_STARTED' });
      const updated = await updateBookAPI(token!, book);
      dispatch({ type: 'UPDATE_BOOK_SUCCEEDED', payload: { book: updated } });
    } catch (error: any) {
      toast('Offline: saving update locally', 3000);
      book.isNotSaved = true;
      await Preferences.set({ key: `upd-${book.title}`, value: JSON.stringify({ token, book }) });
      dispatch({ type: 'UPDATE_BOOK_SUCCEEDED', payload: { book } });
    }
  }, [token]);

  const addBook = useCallback<UpdateBookFn>(async book => {
    try {
      dispatch({ type: 'CREATE_BOOK_STARTED' });
      const added = await createBookAPI(token!, book);
      dispatch({ type: 'CREATE_BOOK_SUCCEEDED', payload: { book: added } });
    } catch (error: any) {
      toast('Offline: saving locally', 3000);
      book.isNotSaved = true;
      await Preferences.set({ key: `sav-${book.title}`, value: JSON.stringify({ token, book }) });
      dispatch({ type: 'CREATE_BOOK_SUCCEEDED', payload: { book } });
    }
  }, [token]);

  const deleteBook = useCallback<DeleteBookFn>(async id => {
    try {
      dispatch({ type: 'DELETE_BOOK_STARTED' });
      const deleted = await deleteBookAPI(token!, id);
      dispatch({ type: 'DELETE_BOOK_SUCCEEDED', payload: { book: deleted } });
    } catch (error: any) {
      dispatch({ type: 'DELETE_BOOK_FAILED', payload: { error } });
    }
  }, [token]);

  async function fetchBooks() {
    dispatch({ type: 'FETCH_BOOKS_STARTED' });
    try {
      const books = await getAllBooks(token!);
      dispatch({ type: 'FETCH_BOOKS_SUCCEEDED', payload: { books } });
    } catch (error: any) {
      dispatch({ type: 'FETCH_BOOKS_FAILED', payload: { error } });
    }
  }

  function setupWebSocket() {
    const close = newWebSocket(token!, message => {
      if (message.event === 'updated') {
        dispatch({
          type: 'SHOW_SUCCESS_MESSAGE',
          payload: { successMessage: message.payload.successMessage }
        });
      }
    });
    return () => close();
  }

  async function runPendingOperations() {
    const { keys } = await Preferences.keys();
    for (const key of keys) {
      if (key.startsWith('sav-') || key.startsWith('upd-')) {
        const { value } = await Preferences.get({ key });
        if (value) {
          const { book } = JSON.parse(value);
          await addBook(book);
          await Preferences.remove({ key });
        }
      }
    }
  }

  const value = {
    ...state,
    updateBook,
    addBook,
    deleteBook,
    closeShowSuccess: () => dispatch({ type: 'HIDE_SUCCESS_MESSAGE' }),
  };

  return (
    <BookContext.Provider value={value}>
      {children}
    </BookContext.Provider>
  );
};
