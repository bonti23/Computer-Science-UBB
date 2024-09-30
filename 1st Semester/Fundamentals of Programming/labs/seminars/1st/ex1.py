n=input("Introduceti un numar natural:")
#print(type(n))
n=int(n)

numar_cifre = 0
suma_cifre_impare = 0
if n==0:
    numar_cifre=1
while n>0:
    #print(n)
    c=n%10
    numar_cifre+=1
    if c%2==1:
        suma_cifre_impare+=c
    n=n//10

print('Numarul introdus are', numar_cifre, 'cifre')
print('Suma cifrelor impare este', suma_cifre_impare**2)

    
