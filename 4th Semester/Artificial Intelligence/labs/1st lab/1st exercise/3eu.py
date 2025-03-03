def produs(tablou1, tablou2):

    minim = min(len(tablou1), len(tablou1))
    sum=0
    for i in range(minim):
        sum=sum+tablou1[i]*tablou2[i]
    return sum

assert(produs([1,0,2,0,3],[1,2,0,3,1])==4)
tablou1 = list(map(int, input("Primul tablou: ").split()))
tablou2 = list(map(int, input("Al doilea tablou: ").split()))
print(produs(tablou1, tablou2))
