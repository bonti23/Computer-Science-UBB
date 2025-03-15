def al_klea_cel_mai_mare_element(sir, k):
    # Sortăm șirul în ordine descrescătoare
    sir_sortat = sorted(sir, reverse=True)
    # Returnăm al k-lea cel mai mare element (k-1 datorită indicelui)
    return sir_sortat[k-1]

# Exemplu
sir = [7, 4, 6, 3, 9, 1]
k = 2
rezultat = al_klea_cel_mai_mare_element(sir, k)
print(f"Al {k}-lea cel mai mare element este: {rezultat}")
