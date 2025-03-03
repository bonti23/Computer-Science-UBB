def functie(sir):
    cuvinte = [cuv for cuv in sir.split(" ")]
    sortat = sorted(cuvinte)
    return sortat[len(sortat) - 1]

assert(functie("ana are mere rosii si galbene")=="si")
sir = input("Introduceti sirul: ")
print(functie(sir))