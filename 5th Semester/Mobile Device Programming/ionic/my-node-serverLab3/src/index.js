import http from 'http';
import Koa from 'koa';
import WebSocket from 'ws';
import Router from 'koa-router';
import bodyParser from "koa-bodyparser";
import jwt from 'koa-jwt';
import cors from '@koa/cors';
import { jwtConfig, timingLogger, exceptionHandler } from './utils.js';
import { initWss } from './wss.js';
import { itemRouter } from './item.js';
import { authRouter } from './auth.js';

const app = new Koa();
const server = http.createServer(app.callback());
const wss = new WebSocket.Server({ server });
initWss(wss);

app.use(cors());
app.use(timingLogger);
app.use(exceptionHandler);
app.use(bodyParser());

const prefix = '/api';

// 🔓 Public routes
const publicApiRouter = new Router({ prefix });
publicApiRouter.use('/auth', authRouter.routes());
app.use(publicApiRouter.routes()).use(publicApiRouter.allowedMethods());

// 🔒 JWT protection for the rest
app.use(jwt(jwtConfig));

// 🔐 Protected routes
const protectedApiRouter = new Router({ prefix });
protectedApiRouter.use('/book', itemRouter.routes());
app.use(protectedApiRouter.routes()).use(protectedApiRouter.allowedMethods());

// ✅ No NeDB, everything in memory
console.log('✅ Server started on port 3000');
server.listen(3000);
