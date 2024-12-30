from random import randint
from matplotlib.pyplot import plot, grid, title, show
#a and b do the same thing.
#a.
def simulareA(n, days, trials):
    number=0
    for i in range(trials):
        birthdays = [randint(1, days) for _ in range(n)]
        #set() elimina duplicatele
        if len(birthdays) != len(set(birthdays)): #if doubles exist
            number+=1
    return number/trials

#b
#it's more complicated to find this probability
#so, we do otherwise
#we substract from 1
def simulareB(n, days):
    p_distinct = 1.0
    # 365/365 * 364/365 * 363/365 * ... (n-i+1)/365
    for i in range(n):
        p_distinct *= (days - i) / days
    return 1 - p_distinct

#c
def graficC():
    days=365
    trials=1000
    maximum=50

    probabilities_A = []
    probabilities_B = []
    values = list(range(2, maximum + 1))

    for n in values:
        probabilities_A.append(simulareA(n, days, trials))
        probabilities_B.append(simulareB(n, days))

    plot(values, probabilities_A, label="Simulare (A)", marker='o')
    plot(values, probabilities_B, label="Calcul Exact (B)", linestyle='--')
    grid(True)
    title("Probabilitatea ca cel puțin două persoane să aibă aceeași zi de naștere")
    show()

def main():
    #a
    n=int(input("Introduceti numarul persoanelor: "))
    days=365
    trials=1000 #numar simulari
    probability_a = simulareA(n, days, trials)
    print(probability_a)

    print("\n")

    #b
    probability_b = simulareB(n, days)
    print(probability_b)

    graficC()

main()
