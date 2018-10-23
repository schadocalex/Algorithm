#!/usr/bin/python3

N = int(input())
pylones = [0] * N
for i in range(N):
    pylones[i] = int(input())

pol = sorted([(h, i) for i, h in enumerate(pylones)])

before = list(range(-1,N-1))
after = list(range(1,N+1))
banderole = 0
for i in range(N-1):
    h1, i1 = pol[i]
    h2, i2 = pol[i+1]

    before[after[i1]%N] = before[i1]

    if h1 == h2 and after[i1] == i2:
        banderole += i2 - i1
        after[i1] = after[i2]
    
    after[before[i1]] = after[i1]
    

print(banderole)