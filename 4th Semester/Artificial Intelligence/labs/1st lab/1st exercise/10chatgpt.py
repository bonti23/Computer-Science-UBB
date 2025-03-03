def linia_cu_most_1(matrice):
    # Inițializăm variabile pentru linia cu cele mai multe 1 și numărul maxim de 1
    index_maxim = -1
    max_1 = -1

    # Parcurgem fiecare linie
    for i in range(len(matrice)):
        # Căutăm ultima poziție care conține un 1 în linia curentă
        j = len(matrice[i]) - 1
        while j >= 0 and matrice[i][j] == 1:
            j -= 1
        # Numărul de 1 pe linia i este j + 1
        numar_1 = len(matrice[i]) - 1 - j
        # Actualizăm linia cu cele mai multe 1
        if numar_1 > max_1:
            max_1 = numar_1
            index_maxim = i

    return index_maxim+1


# Exemplu
matrice = [
    [0, 0, 0, 1, 1],
    [0, 1, 1, 1, 1],
    [0, 0, 1, 1, 1]
]

print(linia_cu_most_1(matrice))  # Va returna 1 (a doua linie)
