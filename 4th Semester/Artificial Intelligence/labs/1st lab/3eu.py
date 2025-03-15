"""
complexitate: O(n)
"""
def produs(tablou1, tablou2):
    minim = min(len(tablou1), len(tablou2))
    suma = sum(tablou1[i] * tablou2[i] for i in range(minim))
    return suma

"""
complexitate: O(n*m)
"""
def produs_bidimensional(matrice1, matrice2):
    m = len(matrice1)
    n = len(matrice1[0])
    suma_totala = 0
    for i in range(m):
        suma_rand = sum(matrice1[i][j] * matrice2[i][j] for j in range(n))
        suma_totala += suma_rand
    return suma_totala

assert produs([1, 0, 2, 0, 3], [1, 2, 0, 3, 1])==4
assert (produs_bidimensional([[1, 0, 2], [0, 3, 1]], [[1, 2, 0], [3, 1, 4]])==8)

mod = input("Unidimensional sau bidimensional? ")
if mod=="unidimensional":
    tablou1 = list(map(int, input("Primul tablou: ").split()))
    tablou2 = list(map(int, input("Al doilea tablou: ").split()))
    print(produs(tablou1, tablou2))

elif mod=="bidimensional":
    m, n = map(int, input("Dimensiunile matricelor (m n): ").split())
    print("Introduceti prima matrice:")
    matrice1 = [list(map(int, input().split())) for _ in range(m)]
    print("Introduceti a doua matrice:")
    matrice2 = [list(map(int, input().split())) for _ in range(m)]
    print(produs_bidimensional(matrice1, matrice2))
