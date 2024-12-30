from random import sample, choice
from math import factorial
from itertools import permutations

def permutari():
    cuvant = "word"
    #permutations for the "word" word
    #join is from the itertools library
    #for p in permutations GENERATES ALL THE PERMUTATIONS IN ONE VARIABLE
    #''.join(p) converts each tuple p into a string
    all_permutations = [''.join(p) for p in permutations(cuvant)]
    return all_permutations
permutari()

def counter():
    all_permutations = permutari()
    return len(all_permutations)

def choice_your_permutation():
    all_permutations = permutari()
    return choice(all_permutations)

def main():
    print(permutari())
    print(counter())
    print(choice_your_permutation())

main()
