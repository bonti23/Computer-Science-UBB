from random import choices, sample
from math import comb, perm

red=5
blue=3
green=2
simulations=1000

def extraction():
    balls = ['red']*red + ['blue']*blue + ['green']*green
    extracted_balls = sample(balls, 3)
    return extracted_balls

eveniments_A=0
eveniments_A_B=0

for _ in range(simulations):
    extracted_balls = extraction()
    if 'red' in extracted_balls:
        eveniments_A += 1
        if len(set(extracted_balls)) == 1:
            eveniments_A_B += 1

estimativ=eveniments_A/eveniments_A_B

P_A = 1 - (comb(5, 3) / comb(10, 3))

P_A_and_B = comb(5, 3) / comb(10, 3)

teoretic = P_A_and_B / P_A
