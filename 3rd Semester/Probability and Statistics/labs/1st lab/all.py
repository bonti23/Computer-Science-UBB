from random import sample
from math import factorial
from itertools import permutations

print(list(permutations('word')))
factorial(len('word'))
sample('word', 4)

from random import sample
from math import perm, comb
from itertools import permutations, combinations

def aranjamente(lista, k, numar_total=False, aleator=False):
    if aleator:
        return sample(lista, k)
    elif numar_total:
        return perm(len(lista), k)
    else:
        return list(permutations(lista, k))

def combinari(lista, k, numar_total=False, aleator=False):
    if aleator:
        indici_aleatori = sample(list(range(len(lista))), k)
        return [lista[i] for i in sorted(indici_aleatori)]
    elif numar_total:
        return comb(len(lista), k)
    else:
        return list(combinations(lista, k))

print(aranjamente('word', 2))
print(aranjamente('word', 2, numar_total=True))
print(aranjamente('word', 2, aleator=True))
print(combinari('word', 2))
print(combinari('word', 2, numar_total=True))
print(combinari('word', 2, aleator=True))

from itertools import combinations_with_replacement

k = 4
for s in combinations_with_replacement('ABCDE', k):
    print(''.join(s))


contor = 0
for indici_persoane in aranjamente(range(12), 5):
    ok = 1
    indici_persoane = sorted(indici_persoane)
    for i in range(4):
        if indici_persoane[i + 1] - indici_persoane[i] == 1:
            ok = 0
            break
    contor += ok
print(contor)
perm(5) * comb(8, 5)
