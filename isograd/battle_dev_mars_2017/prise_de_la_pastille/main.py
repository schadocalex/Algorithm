#!/usr/bin/python3

from heapq import heappush, heappop

N = int(input())

grid = []
for i in range(N):
    grid.append(list(input()))
    try:
        j = grid[-1].index('O')
        pastille = (i,j)
    except:
        pass

def dfs_grid(grid, start):
    to_visit = [(0, start[0], start[1])]
    min_dist = float('inf')
    while to_visit:
        dist, i1, j1 = heappop(to_visit)
        if min_dist < dist:
            return min_dist + 1
        for i2, j2 in [(i1 + 1, j1), (i1, j1 + 1),
                       (i1 - 1, j1), (i1, j1 - 1)]:
            i2 %= N
            j2 %= N
            if grid[i2][j2] == 'M':
                return 0
            elif grid[i2][j2] == 'C':
                min_dist = dist
            elif grid[i2][j2] == '.':
                grid[i2][j2] = 'O'
                heappush(to_visit, (dist + 1, i2, j2))
    return 0

print(dfs_grid(grid, pastille))
