import WebSocket from "ws";
import jwt from "jsonwebtoken";
import { jwtConfig } from "./utils.js";

let wss;

export const initWss = (value) => {
  wss = value;
  wss.on('connection', (ws) => {
    ws.on('message', (message) => {
      const { type, payload } = JSON.parse(message);
      if (type !== 'authorization') {
        ws.close();
        return;
      }
      try {
        ws.user = jwt.verify(payload.token, jwtConfig.secret);
        console.log(`WebSocket authenticated for user ${ws.user.username}`);
      } catch (err) {
        console.error('WebSocket auth failed:', err.message);
        ws.close();
      }
    });

    ws.on('close', () => {
      if (ws.user) console.log(`WebSocket closed for user ${ws.user.username}`);
    });
  });
};

export const broadcast = (userId, data) => {
  if (!wss) return;
  wss.clients.forEach((client) => {
    if (client.readyState === WebSocket.OPEN && client.user && client.user._id === userId) {
      console.log(`Broadcast to ${client.user.username}`);
      client.send(JSON.stringify(data));
    }
  });
};
