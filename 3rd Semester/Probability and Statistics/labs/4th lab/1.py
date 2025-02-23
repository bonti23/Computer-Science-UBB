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

ex1()
