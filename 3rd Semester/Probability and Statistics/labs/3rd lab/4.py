from collections import Counter
import itertools

#Generăm toate combinațiile posibile de aruncări cu trei zaruri (1-6)
outcomes = list(itertools.product(range(1, 6), repeat=3))

#Calculăm sumele pentru fiecare combinație
sums = [sum(outcome) for outcome in outcomes]

#Contorizăm frecvențele fiecărei sume
sum_frequencies = Counter(sums)

#Afișăm frecvențele ordonate după numărul de apariții
most_common_sums = sum_frequencies.most_common()

#Afișăm rezultatele
print("Sumă - Frecvență")
for sum_value, frequency in most_common_sums:
    print(f"{sum_value} - {frequency}")

#Determinăm suma cea mai probabilă
max_frequency = max(sum_frequencies.values())
most_probable_sums = [sum_value for sum_value, freq in sum_frequencies.items() if freq == max_frequency]

print("\nSumele cele mai probabile sunt:", most_probable_sums)
