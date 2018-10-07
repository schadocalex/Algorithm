#!/usr/bin/python3

def topological_order(graph):
    V = range(len(graph))
    indeg = [0 for _ in V]
    for node in V:
        for neighbor in graph[node]:
            indeg[neighbor] += 1
    Q = [node for node in V if indeg[node] == 0]
    order = []
    while Q:
        node = Q.pop()
        order.append(node)
        for neighbor in graph[node]:
            indeg[neighbor] -= 1
            if indeg[neighbor] == 0:
                Q.append(neighbor)
    return order

N = int(input())
warriors = [0] * N
graph = [[] for _ in range(N)]
for i in range(N):
    line = input().split()
    warriors[i] = int(line[0])
    for x in line[1:]:
        graph[int(x)].append(i)

order = topological_order(graph)

graph_set = [set([i]) for i in range(N)]
for i in order:
    for j in graph[i]:
        graph_set[j] |= graph_set[i]

total_warriors = [0] * N
for i in range(N):
    for x in graph_set[i]:
        total_warriors[i] += warriors[x]

print(max(total_warriors))