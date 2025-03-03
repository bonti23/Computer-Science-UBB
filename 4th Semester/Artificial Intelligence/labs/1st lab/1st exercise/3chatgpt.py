def produs_scalar(v1, v2):
    # Reprezentăm vectorii ca dicționare cu index -> valoare
    dict_v1 = {i: val for i, val in enumerate(v1) if val != 0}
    dict_v2 = {i: val for i, val in enumerate(v2) if val != 0}

    # Intersecția cheilor și calculul produsului scalar
    return sum(dict_v1[i] * dict_v2[i] for i in dict_v1 if i in dict_v2)

v1 = [1, 0, 2, 0, 3]
v2 = [1, 2, 0, 3, 1]
print(produs_scalar(v1, v2))  # Output: 4