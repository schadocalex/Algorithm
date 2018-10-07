#!/usr/bin/python3

from collections import defaultdict

L = int(input())
N = int(input())

teams = defaultdict(lambda:0)

result = 0
for i in range(N):
    x = int(input())
    if x == L:
        result += 1
    elif teams[L-x] > 0:
        teams[L-x] -= 1
        result += 1
    else:
        teams[x] += 1

print(result)