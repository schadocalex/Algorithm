#!/usr/bin/python3

N = int(input())
P = int(input())

DIR = {
    'D': (1,0),
    'G': (-1,0),
    'H': (0,-1),
    'B': (0,1)
}

snake = [(x,0) for x in range(N)]

for _ in range(P):
    dx, dy = DIR[input()]
    snake.pop(0)
    x, y = snake[-1]
    snake.append((x+dx, y+dy))

print(*snake[0])