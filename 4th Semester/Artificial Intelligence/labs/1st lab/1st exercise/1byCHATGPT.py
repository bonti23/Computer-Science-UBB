def ultimul_cuvant(text):
    cuvinte = text.split()  # Împărțim textul în cuvinte
    return max(cuvinte)  # Returnăm ultimul în ordine alfabetică

# Exemplu de utilizare
text = "Ana are mere rosii si galbene"
print(ultimul_cuvant(text))
