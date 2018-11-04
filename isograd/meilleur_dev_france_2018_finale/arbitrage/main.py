#!/usr/bin/python3

def ints():
	return list(map(int, input().split()))
def floats():
	return list(map(float, input().split()))

N, M = ints()

graph = [0] * N
for i in range(N):
	graph[i] = floats()

best = [10000] + [0] * (N-1)

for m in range(M):
	new_best = best[:]
	for j in range(N):
		for i in range(N):
			new_amount = best[i] * graph[i][j]
			if new_amount > new_best[j]:
				new_best[j] = new_amount
	best = new_best

print(best[0])