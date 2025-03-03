def functie(tablou):
    frecventa={}
    for i in tablou:
        if i in frecventa:
            frecventa[i]+=1
        else:
            frecventa[i]=1
    for value, frec in frecventa.items():
        if frec>=len(tablou)/2:
            return value

assert(functie([2,8,7,2,2,5,2,3,1,2,2])==2)
tablou=list(map(int, input("Introduceti tabloul: ").split()))
print(functie(tablou))
