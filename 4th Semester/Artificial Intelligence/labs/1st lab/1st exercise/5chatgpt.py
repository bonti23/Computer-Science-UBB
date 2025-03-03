def gaseste_duplicat(sir):
    # Set pentru a urmari elementele deja vazute
    elemente_vazute = set()

    # Iteram prin fiecare element din sir
    for element in sir:
        # Daca elementul este deja in set, este duplicatul
        if element in elemente_vazute:
            return element
        # Altfel, adaugam elementul in set
        elemente_vazute.add(element)

    # In caz ca nu gasim niciun duplicat (nu ar trebui sa se intample)
    return None


# Exemplu de utilizare
sir = [1, 2, 3, 4, 2]
duplicat = gaseste_duplicat(sir)
print("Elementul duplicat este:", duplicat)
