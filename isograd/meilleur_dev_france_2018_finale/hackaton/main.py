#!/usr/bin/python3

from collections import defaultdict

N = int(input())
M = [None] * N
for i in range(N):
    M[i] = [int(x) for x in input().split()]

res = defaultdict(lambda: False)

def f(nodes):
    if not res[nodes]:
        if len(nodes) <= 1:
            return len(nodes)
        
        children = tuple(x for x in nodes[1:] if M[x][nodes[0]] == 1)
        others = tuple(nodes[1:])
        
        res[nodes] = max(f(children) + 1, f(others))
    
    return res[nodes]

print(f(tuple(range(N))))