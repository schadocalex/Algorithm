#!/usr/bin/python3

N = int(input())
pylones = [0] * N
for i in range(N):
    pylones[i] = int(input())

score = [0] * N
for i in range(N):
    h = 0
    for j in range(i-1,-1,-1):
        if pylones[j] > h:
            h = pylones[j]
            score[i] += 1
    h = 0
    for j in range(i+1,N,+1):
        if pylones[j] > h:
            h = pylones[j]
            score[i] += 1

print(' '.join(map(str, score)))