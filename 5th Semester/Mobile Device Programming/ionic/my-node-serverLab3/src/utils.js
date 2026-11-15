export const jwtConfig = { secret: 'my-secret' };

export const exceptionHandler = async (ctx, next) => {
  try {
    return await next();
  } catch (err) {
    console.error('Exception:', err);
    ctx.body = { message: err.message || 'Unexpected error.' };
    ctx.status = err.status || 500;
  }
};

export const timingLogger = async (ctx, next) => {
  const start = Date.now();
  await next();
  const ms = Date.now() - start;
  console.log(`${ctx.method} ${ctx.url} => ${ctx.response.status}, ${ms}ms`);
};
