#!/usr/bin/python3

N = int(input())

DIR = [(0,-1), (1,0), (0,1), (-1,0)]
pos = (N//2, N//2)
grid = [['='] * N for _ in range(N)]
nb = 2
count = 0

while 0 <= pos[0] < N and 0 <= pos[1] < N:
    grid[pos[0]][pos[1]] = '#'
    nb -= 1
    if nb == 0:
        count += 1
        nb = count + 1
    pos = tuple(a+b for a,b in zip(pos, DIR[count%4]))

for line in grid:
    print(''.join(line))