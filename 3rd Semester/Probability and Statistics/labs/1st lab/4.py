from itertools import combinations_with_replacement

def repetition(word, k):
    all = [''.join(p) for p in combinations_with_replacement(word, k)]
    return all

def main():
    all = repetition('ABCDE', 4)
    for one in all:
        print(one)

main()
