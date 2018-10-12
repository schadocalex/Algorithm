#!/usr/bin/python3

N = int(input())

doors = []
for i in range(N):
    line = input()
    for j in range(N):
        if line[j] == 'X':
            doors.append((i,j))

graph = [[] for _ in range(len(doors))]
for x, doorx in enumerate(doors):
    for y, doory in enumerate(doors):
        if x != y and doory[0] >= doorx[0] and doory[1] >= doorx[1]:
            graph[x].append(y)

def augment(u, bigraph, visit, match):
    for v in bigraph[u]:
        if not visit[v]:
            visit[v] = True
            if match[v] is None or augment(match[v],  bigraph, visit, match):
                match[v] = u       # found an augmenting path
                return True
    return False


def max_bipartite_matching(bigraph):
    n = len(bigraph)               # same domain for U and V
    match = [None] * n
    for u in range(n):
        augment(u, bigraph, [False] * n, match)
    return match

match = max_bipartite_matching(graph)
print(len([x for x in match if x is None]))