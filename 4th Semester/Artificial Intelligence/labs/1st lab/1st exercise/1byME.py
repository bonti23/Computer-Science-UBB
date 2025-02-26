def functie(sir):
    cuvinte=[cuv for cuv in sir.split(" ")]
    sortat = sorted(cuvinte)
    print(sortat[len(sortat)-1])
    
sir=input("Introduceti sirul: ")
functie(sir)
