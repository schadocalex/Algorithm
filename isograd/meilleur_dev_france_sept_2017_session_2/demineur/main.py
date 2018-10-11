#!/usr/bin/python3

H = int(input())
L = int(input())
grid = [list(input()) for _ in range(H)]

near_bomb = set()

def adj(i,j):
    res = [(i+1, j), (i+1, j+1), (i+1, j-1), (i, j+1), (i, j-1), (i-1, j), (i-1, j+1), (i-1, j-1)]
    return [x for x in res if 0 <= x[0] < H and 0 <= x[1] < L]

def dfs_grid(grid, i, j, mark='x', free='.'):
    nb = 1
    to_visit = [(i, j)]
    grid[i][j] = mark
    while to_visit:
        i1, j1 = to_visit.pop()
        for i2, j2 in adj(i1, j1):
            if grid[i2][j2] == free:
                nb += 1
                grid[i2][j2] = mark  # marquer visite
                if (i2, j2) not in near_bomb:
                    to_visit.append((i2, j2))
    return nb

cross = None

for i in range(H):
    for j in range(L):
        if grid[i][j] == 'x':
            cross = (i, j)
        elif grid[i][j] == '*':
            near_bomb |= set(adj(i, j))

if cross in near_bomb:
    print(1)
else:
    print(dfs_grid(grid, cross[0], cross[1]))
