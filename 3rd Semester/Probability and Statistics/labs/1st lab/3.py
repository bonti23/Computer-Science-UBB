from random import sample
from math import perm, comb
from itertools import permutations, combinations
from random import randint, choice

def arrangements(cuvant, i):
    all_arrangements = [''.join(p) for p in permutations(cuvant, i)]
    return all_arrangements

def combinations_function(cuvant, i):
    all_combinations = [''.join(p) for p in combinations(cuvant, i)]
    return all_combinations

def main():
    print(arrangements("word", 2))
    print("\n")
    print(choice(arrangements("word", 2)))
    print("\n")
    #perm works only with integers
    #like, perm(4, 2), because 4 is the length of the 'word' word
    print(perm(len("word"), 2))

    print("\n")
    print(combinations_function("word", 2))
    print("\n")
    print(choice(combinations_function("word", 2)))
    print("\n")
    print(comb(len("word"), 2))

main()
