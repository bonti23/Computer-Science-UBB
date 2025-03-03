from collections import Counter

def cuvinte_unice(text):
    # Împărțim textul în cuvinte
    cuvinte = text.split()
    # Numărăm aparițiile fiecărui cuvânt
    frecventa = Counter(cuvinte)
    # Selectăm cuvintele care apar exact o singură dată
    return [cuvant for cuvant, nr in frecventa.items() if nr == 1]

# Exemplu de utilizare
text = "ana are ana are mere rosii ana"
print(cuvinte_unice(text))  # Output: ['mere', 'rosii']
