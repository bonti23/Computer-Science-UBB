nodes=[0 3 5 8 13];
values=[0  225  383  623  993];
der_values=[75  77   80   74   72];
t=linspace(0,13,1001);
[distance,speed]=Hermite_interpolare(nodes,values,der_values,t);


clf;
hold on;axis square;
plot(values,der_values,'or','MarkerSize',8,'MarkerFaceColor','r')
plot(distance,speed,'b','LineWidth',1.5)

xlabel("distance",'fontweight','bold');
ylabel("speed",'fontweight','bold');
grid on;set(gca,"fontsize", 15)

