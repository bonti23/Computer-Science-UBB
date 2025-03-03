def ultimul_cuvant(sir):
    cuvinte = sir.split()  # Împărțim textul în cuvinte
    return max(cuvinte)  # Returnăm ultimul în ordine alfabetică

sir="Ana are mere rosii si galbene"
print(ultimul_cuvant(sir))

