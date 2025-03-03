def maxim(matrice, m):
    maxim=-1
    line=-1
    for i in range(m):
        counter=matrice[i].count(1)
        if counter> maxim:
            maxim=counter
        line=i
    return line

assert(maxim([[0,0,0,1,1],
[0,1,1,1,1],
[0,0,1,1,1]], 3)==2)
m, n = map(int, input("Dimensiunile matricei: ").split())
matrice = []
for i in range(m):
    line=list(map(int, input(f"Introduceti valorile pentru linia {i+1}: ").split()))
    matrice.append(line)
print(maxim(matrice, m))
