def functie(text):
    sir=""
    cuvinte=[cuv for cuv in text.split()]
    sortate=sorted(cuvinte)
    ok=1
    i=0
    while(i<=len(sortate)-2):
        while(sortate[i]==sortate[i+1]):
            ok=0
            i+=1
        if ok==1:
            sir+=sortate[i]+" "
        ok=1
        i+=1
    if ok==1:#ultimul cuvant
        sir+=sortate[i]
    return sir

assert(functie("ana are ana are mere rosii ana")=="mere rosii")
text=input("Introduceti textul: ")
print(functie(text))
