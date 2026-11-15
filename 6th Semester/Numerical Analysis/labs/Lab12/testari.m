f=@(x) cos(x)-x

clf; hold on;
fplot(f,[0,pi/2])
plot([0,pi/2],[0,0])

[X, FVAL, INFO, OUTPUT]=fzero(f,pi/4)
[c,val_f,i]=bisection(f,0,pi/2)
[c,val_f,i]=false_pos(f,0,pi/2)
[x2,val_f,i]=secant(f,0,pi/2)
df=@(x) -sin(x)-1
[x1,val_f,i]=Newton(f,df,pi/4)
g=@(x) cos(x)
[x1,i]=succesiv(g,pi/4)
[p,i]=Steffensen(g,pi/4)
phi=@(x) x-f(x)./df(x)
[x1,i]=succesiv(phi,pi/4)
