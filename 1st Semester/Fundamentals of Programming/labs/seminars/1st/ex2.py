import random
import math
#import numpy

#Toate 3 variantele creeaza o lista de temperaturi


"""
#Varianta 1 - Citesc temperaturile una cate una
lista_temperaturi = []

for ora in range(24):
    print(f'Introduceti temperatura inregistrata la ora {ora}:')
    temperatura_curenta = input()
    temperatura_curenta = float(temperatura_curenta)

    lista_temperaturi.append(temperatura_curenta)
"""


#Varianta 2 - Citim temperaturile ca un sir

sir_temperaturi = input("Introduceti 24 temperaturi (ora 00:00 - ora 23:00) separate prin spatiu:")
#impartim sirul citit in functie de spatii
lista_temperaturi = sir_temperaturi.split()
#transformam fiecare element din sir din string in float - list comprehension
#optional, vom invata despre ele mai incolo: https://www.w3schools.com/python/python_lists_comprehension.asp
lista_temperaturi = [float(temperatura) for temperatura in lista_temperaturi]


"""
#Varianta 3 - nu citim de la tastatura toate cele 24 de valori, ci le generam random dintr-un interval
lista_temperaturi = []
for ora in range(24):
    print("Pentru ora: "+str(ora)+" s-a generat temperatura:", end=" ")
    #generam un numar float random intre 10 si 30
    temperatura_curenta = random.uniform(10, 30)
    #retinem doar prima zecimala
    temperatura_curenta = round(temperatura_curenta,1)

    temperatura_curenta = float(temperatura_curenta)
    print(temperatura_curenta)

    lista_temperaturi.append(temperatura_curenta)
"""

#Afisare lista temperaturi

print('Temperaturile sunt:', lista_temperaturi)

"""
#sau

for index in range(len(lista_temperaturi)):
    #index = ora
    print("La ora ", index, "s-a inregistrat temperatura", lista_temperaturi[index])
#sau
    
for temperatura in lista_temperaturi:
    #nu avem acces imediat la index (ora) - putem folosi enumerate, vezi for urmator
    print("Temperatura:", temperatura)

"""
#math, random nu trebuie instalate, pot fi folosite direct (doar sa existe instructiunile
#import - vezi inceput fisier)

temperatura_minima = math.inf
temperatura_maxima = -math.inf

ora_temp_minima, ora_temp_maxima = 0,0

for ora, temp in enumerate(lista_temperaturi):
    if temp<temperatura_minima:
        temperatura_minima = temp
        ora_temp_minima = ora
    elif temp>temperatura_maxima:
        temperatura_maxima = temp
        ora_temp_maxima = ora


print('Temperatura minima este de', temperatura_minima, 'si se inregistreaza la ora', ora_temp_minima)
print('Temperatura maxima este de', temperatura_maxima, 'si se inregistreaza la ora', ora_temp_maxima)

#Calculare min/ora temperatura minima cu ajutorul numpy - optional, necesita instalare numpy

#Gaseste minim
temperatura_minima_direct = min(lista_temperaturi)
#Gaseste indexul temperaturii minime in lista (instructiune pentru care trebuie numpy)
#ora_temp_min_direct = numpy.argmin(lista_temperaturi)
#print("Temperatura minima calculata in mod direct:",temperatura_minima_direct,"si s-a inregistrat la ora", ora_temp_min_direct)

medie_temperaturi = round(sum(lista_temperaturi)/len(lista_temperaturi), 1)
print('Temperatura medie este de', medie_temperaturi)
        
