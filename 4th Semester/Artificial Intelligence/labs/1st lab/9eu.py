"""
complexitate: O(k*m*n)
"""
def suma(matrice, p, q, r, s):
    suma=0
    for i in range(p, r+1):
        for j in range(q, s+1):
            suma+=matrice[i][j]
    return suma



assert(suma([[0, 2, 5, 4, 1],
[4, 8, 2, 3, 7],
[6, 3, 4, 6, 2],
[7, 3, 1, 8, 3],
[1, 5, 7, 9, 4]], 1, 1, 3, 3)==38)
assert(suma([[0, 2, 5, 4, 1],
[4, 8, 2, 3, 7],
[6, 3, 4, 6, 2],
[7, 3, 1, 8, 3],
[1, 5, 7, 9, 4]], 2, 2, 4, 4)==44)
m, n = map(int, input("Dimensiunile matricei: ").split())
matrice = []
for i in range(m):
    line=list(map(int, input(f"Introduceti valorile pentru linia {i+1}: ").split()))
    matrice.append(line)

numar_perechi = int(input("Introduceti numarul de perechi de coordonate: "))
perechi = []
for _ in range(numar_perechi):
    p, q, r, s = map(int, input("Introduceti coordonatele sub-matricei (p, q, r, s): ").split())
    perechi.append((p, q, r, s))

for i, (p, q, r, s) in enumerate(perechi, 1):
    suma_submatrice = suma(matrice, p, q, r, s)
    print(f"Suma elementelor din sub-matricea {i} este: {suma_submatrice}")