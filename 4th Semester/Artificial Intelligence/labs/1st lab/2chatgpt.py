import math

def distanta_euclidiana(x1, y1, x2, y2):
    return math.sqrt((x2 - x1)**2 + (y2 - y1)**2)

# Exemplu:
x1, y1 = 1, 5
x2, y2 = 4, 1
print(distanta_euclidiana(x1, y1, x2, y2))  # Va returna 5.0
