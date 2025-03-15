import math
"""
complexitate: O(1)
"""
def functie(a, b, c, d):
    #sqrt((x2-x1)^2+(y2-y1)^2)
    return math.sqrt(pow(c-a,2)+pow(d-b,2))

assert(functie(1,5,4,1)==5.0)
a, b = map(int, input("Introduceti prima pereche: ").split(','))
c, d = map(int, input("Introduceti a doua pereche: ").split(','))
print(functie(a, b, c, d))