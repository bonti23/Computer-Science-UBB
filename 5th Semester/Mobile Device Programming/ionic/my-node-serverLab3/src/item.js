import Router from 'koa-router';
import { broadcast } from './wss.js';

const books = [
  { _id: 'b1', title: 'The Great Gatsby', price: 39.99, sold: false, launchDate: '2021-03-15T00:00:00.000Z', userId: 'vI1GMkzBza5i82zj' },
  { _id: 'b2', title: 'To Kill a Mockingbird', price: 29.5, sold: true, launchDate: '2020-07-11T00:00:00.000Z', userId: 'vI1GMkzBza5i82zj' },
  { _id: 'b3', title: '1984', price: 25.0, sold: false, launchDate: '2019-06-08T00:00:00.000Z', userId: 'vI1GMkzBza5i82zj' },
  { _id: 'b4', title: 'Brave New World', price: 32.0, sold: true, launchDate: '2022-02-10T00:00:00.000Z', userId: 'vI1GMkzBza5i82zj' },
  { _id: 'b5', title: 'The Catcher in the Rye', price: 27.75, sold: false, launchDate: '2021-05-20T00:00:00.000Z', userId: 'vI1GMkzBza5i82zj' },
  { _id: 'b6', title: 'Moby Dick', price: 41.2, sold: true, launchDate: '2023-09-01T00:00:00.000Z', userId: 'vI1GMkzBza5i82zj' },
  { _id: 'b7', title: 'Pride and Prejudice', price: 33.0, sold: false, launchDate: '2024-01-05T00:00:00.000Z', userId: 'vI1GMkzBza5i82zj' },
  { _id: 'b8', title: 'The Lord of the Rings', price: 59.99, sold: true, launchDate: '2018-12-19T00:00:00.000Z', userId: 'vI1GMkzBza5i82zj' },
  { _id: 'b9', title: "Harry Potter and the Philosopher's Stone", price: 45.5, sold: false, launchDate: '2020-10-01T00:00:00.000Z', userId: 'vI1GMkzBza5i82zj' },
  { _id: 'b10', title: 'Crime and Punishment', price: 36.8, sold: true, launchDate: '2022-08-12T00:00:00.000Z', userId: 'vI1GMkzBza5i82zj' }
];

export const itemRouter = new Router();

// 🔹 Get all books for the logged-in user
itemRouter.get('/', async (ctx) => {
  const userId = ctx.state.user._id;
  const userBooks = books.filter(b => b.userId === userId);
  ctx.response.body = userBooks;
  ctx.response.status = 200;
});

// 🔹 Create book
itemRouter.post('/', async (ctx) => {
  try {
    const userId = ctx.state.user._id;
    const item = ctx.request.body;

    if (!item.title) throw new Error('Missing title property');
    if (!item.price) throw new Error('Missing price property');

    const newBook = {
      _id: `b${Math.random().toString(16).slice(2, 8)}`,
      ...item,
      userId
    };
    books.push(newBook);

    ctx.response.body = newBook;
    ctx.response.status = 201;

    broadcast(userId, { type: 'created', payload: newBook });
  } catch (err) {
    console.error("❌ Error creating item:", err.message);
    ctx.response.body = { message: err.message };
    ctx.response.status = 400;
  }
});

// 🔹 Update or create book
itemRouter.put('/:id', async (ctx) => {
  const id = ctx.params.id;
  const item = ctx.request.body;
  const userId = ctx.state.user._id;
  const index = books.findIndex(b => b._id === id);

  if (item._id && item._id !== id) {
    ctx.response.status = 400;
    ctx.response.body = { message: 'Param id and body _id should be the same' };
    return;
  }

  if (index !== -1) {
    books[index] = { ...books[index], ...item, userId };
    ctx.response.body = books[index];
    ctx.response.status = 200;
    broadcast(userId, { type: 'updated', payload: books[index] });
  } else {
    const newBook = { _id: id, ...item, userId };
    books.push(newBook);
    ctx.response.body = newBook;
    ctx.response.status = 201;
    broadcast(userId, { type: 'created', payload: newBook });
  }
});

// 🔹 Delete book
itemRouter.del('/:id', async (ctx) => {
  const id = ctx.params.id;
  const userId = ctx.state.user._id;
  const index = books.findIndex(b => b._id === id && b.userId === userId);

  if (index === -1) {
    ctx.response.status = 404;
    ctx.response.body = { message: 'Book not found or not yours' };
    return;
  }

  const [deleted] = books.splice(index, 1);
  ctx.response.status = 204;
  broadcast(userId, { type: 'deleted', payload: deleted });
});
