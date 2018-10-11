#!/usr/bin/python3

N = int(input())
mid = N//2
mod = 1 if N % 4 == 1 else 0

def dist_to_center(i, j):
    if i == j or i == N-1 - j:
        return abs(mid - i) + 1
    else:
        return max(abs(mid - i), abs(mid - j))

for i in range(N):
    s = [0] * N
    for j in range(N):
        if dist_to_center(i, j) % 2 == mod:
            s[j] = '#'
        else:
            s[j] = '*'
    if i == 0 or i == N-1:
        s[0] = s[N-1] = '.'
    print(''.join(s))