import math

def prim(n):
    if n<=1:
        return False
    if n%2==0 and n!=2:
        return False
    for i in range(3, int(math.sqrt(n)) + 1, 2):
        if n%i==0:
            return False
    return True

numar=int(input("Numarul citit: "))
print(prim(numar))
