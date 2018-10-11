#!/usr/bin/python3

N = int(input())
pot = sorted([int(input()) for _ in range(N)])
res = []
while pot:
    res.append(pot.pop())
    if pot:
        res.append(pot.pop(0))

print(' '.join(map(str, res)))