"""
complexitate: O(k*n)
"""
def functie(tablou, k):
    while(k>0):
        maxim=max(tablou)
        tablou=[x for x in tablou if x!=maxim]
        k=k-1
        if(k==0):
            return maxim

assert(functie([7,4,6,3,9,1],2)==7)
tablou=list(map(int, input("Introduceti tabloul: ").split()))
k=int(input("Introduceti k-ul: "))
print(functie(tablou, k))
