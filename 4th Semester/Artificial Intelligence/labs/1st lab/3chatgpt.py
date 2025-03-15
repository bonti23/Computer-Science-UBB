def produs_scalar(v1, v2):
    # Reprezentăm vectorii ca dicționare cu index -> valoare
    dict_v1 = {i: val for i, val in enumerate(v1) if val != 0}
    dict_v2 = {i: val for i, val in enumerate(v2) if val != 0}

    # Intersecția cheilor și calculul produsului scalar
    return sum(dict_v1[i] * dict_v2[i] for i in dict_v1 if i in dict_v2)


def produs_scalar_matrice(m1, m2):
    # Reprezentăm matricele ca dicționare cu (linie, coloană) -> valoare
    dict_m1 = {(i, j): val for i, row in enumerate(m1) for j, val in enumerate(row) if val != 0}
    dict_m2 = {(i, j): val for i, row in enumerate(m2) for j, val in enumerate(row) if val != 0}

    # Intersecția cheilor și calculul produsului scalar
    return sum(dict_m1[key] * dict_m2[key] for key in dict_m1 if key in dict_m2)


m1 = [
    [1, 0, 2],
    [0, 3, 0],
    [4, 0, 5]
]
m2 = [
    [1, 2, 0],
    [0, 1, 3],
    [4, 0, 1]
]
print(produs_scalar_matrice(m1, m2))

v1 = [1, 0, 2, 0, 3]
v2 = [1, 2, 0, 3, 1]
print(produs_scalar(v1, v2))  # Output: 4