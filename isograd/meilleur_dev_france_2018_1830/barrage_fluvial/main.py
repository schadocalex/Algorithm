#!/usr/bin/python3

import math

N = int(input())

g = [0] * N
for i in range(N):
	g[i] = list(input())

def dfs_grid(grid, i, j, s):
    height = len(grid)
    width = len(grid[0])
    grid[i][j] = 'x'
    for ni, nj in [(i + 1, j), (i, j + 1),
                   (i - 1, j), (i, j - 1)]:
        if 0 <= ni < height and 0 <= nj < width:
            if grid[ni][nj] == '#':
                dfs_grid(grid, ni, nj, s)
            elif grid[ni][nj] == '.':
            	s.add((i, j))

s1 = set()
dfs_grid(g, N-1, 0, s1)
s2 = set()
dfs_grid(g, 0, N-1, s2)

_min = float('inf')
for x in s1:
	for y in s2:
		n = (x[0]-y[0])**2+(x[1]-y[1])**2
		if n < _min:
			_min = n

print(math.ceil(math.sqrt(_min)))