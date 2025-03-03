def suma_submatrici(matrice, perechi):
    # Listă pentru a stoca rezultatele sumelor pentru fiecare sub-matrice
    rezultate = []

    # Parcurgem fiecare pereche de coordonate
    for (p, q), (r, s) in perechi:
        suma = 0

        # Iterăm prin toate elementele din sub-matrice
        for i in range(p, r + 1):
            for j in range(q, s + 1):
                suma += matrice[i][j]

        # Adăugăm suma pentru această sub-matrice la lista de rezultate
        rezultate.append(suma)

    return rezultate


# Exemplu de matrice
matrice = [
    [0, 2, 5, 4, 1],
    [4, 8, 2, 3, 7],
    [6, 3, 4, 6, 2],
    [7, 3, 1, 8, 3],
    [1, 5, 7, 9, 4]
]

# Lista de perechi de coordonate
perechi = [((1, 1), (3, 3)), ((2, 2), (4, 4))]

# Apelăm funcția pentru a calcula sumele
rezultate = suma_submatrici(matrice, perechi)

# Afișăm rezultatele
for i, suma in enumerate(rezultate, 1):
    print(f"Suma elementelor din sub-matricea {i} este: {suma}")
