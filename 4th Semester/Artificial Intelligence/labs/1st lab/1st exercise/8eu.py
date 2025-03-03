def binary(x):
    result=0
    power=0
    while x!=0:
        result=result+pow(10,power)*(x%2)
        power=power+1
        x=x//2
    return result

assert(binary(1)==1)
assert(binary(2)==10)
assert(binary(3)==11)
assert(binary(4)==100)
n=int(input("Introduceti-l pe n: "))
for i in range(1,n+1):
    print(binary(i))