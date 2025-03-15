"""
complexitate: O(n*m)
"""
def inlocuire(matrice, m, n):
    for i in range(1, n-1):
        for j in range(1, m-1):
            if matrice[i][j]==0 and (matrice[i-1][j]==1 and matrice[i+1][j]==1 and matrice[i][j-1]==1 and matrice[i][j+1]==1):
                    matrice[i][j]=1
    return matrice

assert(inlocuire([
    [1, 1, 1, 1, 1],
    [1, 0, 0, 0, 1],
    [1, 0, 1, 0, 1],
    [1, 0, 0, 0, 1],
    [1, 1, 1, 1, 1]
], 5, 5)==[[1, 1, 1, 1, 1], [1, 0, 0, 0, 1], [1, 0, 1, 0, 1], [1, 0, 0, 0, 1], [1, 1, 1, 1, 1]])
m, n = map(int, input("Dimensiunile matricei: ").split())
matrice = []
for i in range(m):
    line=list(map(int, input(f"Introduceti valorile pentru linia {i+1}: ").split()))
    matrice.append(line)
print(inlocuire(matrice, m, n))