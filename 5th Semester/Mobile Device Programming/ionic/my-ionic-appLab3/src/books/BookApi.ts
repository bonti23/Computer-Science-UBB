import axios from "axios";
import { getLogger, authConfig, baseUrl, withLogs } from "../core";
import { BookProps } from "./BookProps";

const log = getLogger('BookApi');
const bookUrl = `http://${baseUrl}/api/book`;

// ✅ Returnează doar .data, nu întregul răspuns Axios
export const getAllBooks = async (token: string): Promise<BookProps[]> => {
  const response = await withLogs(axios.get(bookUrl, authConfig(token)), 'getAllBooks');
  return response.data;
};

export const updateBookAPI = async (token: string, book: BookProps): Promise<BookProps> => {
  const response = await withLogs(axios.put(`${bookUrl}/${book._id}`, book, authConfig(token)), 'updateBook');
  return response.data;
};

export const createBookAPI = async (token: string, book: BookProps): Promise<BookProps> => {
  const response = await withLogs(axios.post(bookUrl, book, authConfig(token)), 'createBook');
  return response.data;
};

export const deleteBookAPI = async (token: string, id: string): Promise<BookProps> => {
  const response = await withLogs(axios.delete(`${bookUrl}/${id}`, authConfig(token)), 'deleteBook');
  return response.data;
};

interface MessageData {
  event: string;
  payload: {
    successMessage: string;
    updatedBook: BookProps;
  };
}

// ✅ WebSocket configurat corect (nu se reconectează în buclă)
export const newWebSocket = (token: string, onMessage: (data: MessageData) => void) => {
  const ws = new WebSocket(`ws://${baseUrl}/ws`);
  
  ws.onopen = () => {
    log('WebSocket open');
    ws.send(JSON.stringify({ type: 'authorization', payload: { token } }));
  };
  
  ws.onmessage = event => {
    const data = JSON.parse(event.data);
    log('WebSocket message', data);
    onMessage(data);
  };
  
  ws.onerror = error => log('WebSocket error', error);
  ws.onclose = () => log('WebSocket closed');
  
  return () => ws.close();
};
