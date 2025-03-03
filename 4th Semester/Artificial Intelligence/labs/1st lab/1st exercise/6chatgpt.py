def gaseste_majoritar(sir):
    # Pasul 1 și 2: Identificarea unui candidat
    candidate = None
    count = 0

    for numar in sir:
        if count == 0:
            candidate = numar
        count += (1 if numar == candidate else -1)

    # Pasul 3: Verificăm dacă candidatul este cu adevărat majoritar
    if sir.count(candidate) > len(sir) // 2:
        return candidate
    else:
        return None  # Nu există element majoritar


# Testare
sir = [2, 8, 7, 2, 2, 5, 2, 3, 1, 2, 2]
resultat = gaseste_majoritar(sir)
print(f"Elementul majoritar este: {resultat}")
