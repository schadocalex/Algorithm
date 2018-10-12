#!/usr/bin/python3

N = int(input())

grid = []
for i in range(N):
    grid.append(input())

dist = [[0] * N for _ in range(N)]
for i in range(N):
    for j in range(N):
        dist[i][j] = max(dist[x[0]][x[1]] for x in ((i-1, j), (i, j-1)))
        if grid[i][j] == 'X':
            dist[i][j] += 1

print(dist[-1][-1])
