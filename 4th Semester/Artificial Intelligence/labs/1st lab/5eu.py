"""
complexitate: O(n)
"""
def functie(tablou):
    contor=1
    ok=0
    for i in range(len(tablou)-2):
        if tablou[i]>=tablou[i+1] and contor==tablou[i] and ok==0:
            return tablou[i+1]
            ok=1
        elif tablou[i]>=tablou[i+1] and contor!=tablou[i+1] and ok==0:
            return tablou[i]
            ok=1
        contor=contor+1
    if tablou[len(tablou)-1]!=contor+1:
        return tablou[len(tablou)-1]

assert(functie([1,2,3,4,2])==2)
assert(functie([1,2,4,4,5])==4)
tablou=list(map(int, input("Introduceti tabloul: ").split()))
print(functie(tablou))

