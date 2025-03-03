def inlocuire_0_inconjurate(matrice):
    n = len(matrice)
    m = len(matrice[0]) if n > 0 else 0

    # Cream o copie a matricei pentru a nu modifica matricea in timpul parcurgerii
    copie_matrice = [row[:] for row in matrice]

    for i in range(1, n - 1):  # Evitam marginea
        for j in range(1, m - 1):  # Evitam marginea
            # Verificam daca elementul este 0 si este inconjurat de 1
            if matrice[i][j] == 0:
                if (matrice[i - 1][j] == 1 and matrice[i + 1][j] == 1 and
                        matrice[i][j - 1] == 1 and matrice[i][j + 1] == 1):
                    # Inlocuim cu 1
                    copie_matrice[i][j] = 1

    return copie_matrice


# Exemplu
matrice = [
    [1, 1, 1, 1, 1],
    [1, 0, 0, 0, 1],
    [1, 0, 1, 0, 1],
    [1, 0, 0, 0, 1],
    [1, 1, 1, 1, 1]
]

matrice_modificata = inlocuire_0_inconjurate(matrice)

for row in matrice_modificata:
    print(row)
