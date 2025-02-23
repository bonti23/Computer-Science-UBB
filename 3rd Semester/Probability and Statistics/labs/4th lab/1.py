from random import sample

from scipy.stats import bernoulli, binom, hypergeom, geom
from matplotlib.pyplot import bar, show, hist, grid, legend, xticks, gca


def ex1():
    p=0.5
    pasi=15
    n=1000
    nod=0#pozitia initiala
    pozitiiNod=[]

    for i in range(pasi):
        pozitiiNod.append(nod)
        nod+=(bernoulli.rvs(p)*2-1)#+1 avanseaza si -1 daca se retrage
    print("Pozitii: ",pozitiiNod)

    data=[]

    for i in range(n):
        nod=0
        for j in range(pasi):
            nod+=(bernoulli.rvs(p)*2-1)
        data.append(nod)

    left=min(data)
    right=max(data)

    bin_edges=[k+0.5 for k in range(left-1, right+1)]
    hist(data, bin_edges, density=True, rwidth=1, color='blue', edgecolor='black')
    ax = gca()
    ax.set_xticks(range(left, right + 1))
    show()


    start=0
    noduriCerc=5

    data=[]

    for i in range(n):
        nod=start
        for j in range(pasi):
            nod+=(bernoulli.rvs(p) * 2 - 1)
            if nod<0:
                nod=noduriCerc-1
            if nod==noduriCerc:
                nod=0
        data.append(nod)

    left=min(data)
    right=max(data)

    bin_edges=[k+0.5 for k in range(left-1, right+1)]
    hist(data, bin_edges, density=True, rwidth=1, color='pink')
    show()

def ex2():
    lista = []
    for i in range(500):
        total=0
        bilet_castigator=False

        while not bilet_castigator:
            alese=sample(range(1, 50), 6)
            castigatoare=sample(range(1, 50), 6)

            corecte=0

            for bila in alese:
                if bila in castigatoare:
                    corecte+=1

            if corecte>=3:
                bilet_castigator=True

            total+=1

        lista.append(total-1)

    print("Lista cu numarul de bilete necastigatoare pana la primul bilet necastigator: \n", lista)

    areLoc=0
    for nr in lista:
        if nr>=10:
            areLoc+=1

    probabilitateSimulata=areLoc/500
    print("Probabilitatea simulata: \n"+str(probabilitateSimulata))




    p=sum(hypergeom.pmf(k, 49, 6, 6) for k in range(3, 7))
    probabilitateTeoretica=1-geom.cdf(9, p)
    print("Probabilitatea teoretica: \n", probabilitateTeoretica)


ex1()
