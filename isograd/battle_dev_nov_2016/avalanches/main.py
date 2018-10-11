#!/usr/bin/python3

from heapq import heappop, heappush

N = int(input())

A, B = tuple(map(int, input().split(' ')))
weight = [[0] * N for _ in range(N)]

for i in range(N):
	line = input().split(' ')
	for j in range(N):
		weight[i][j] = float(line[j])

def dijkstra(weight, source, target):
	black = [False] * N
	dist = [float('inf')] * N
	dist[source] = 0
	heap = [(0, source)]
	while heap:
		dist_node, node = heappop(heap)
		if not black[node]:
			black[node] = True
			if node == target:
				break
			for neighbor in range(N):
				if node == neighbor:
					continue
				dist_neighbor = 1 - (1-dist_node) * (1-weight[node][neighbor])
				if dist_neighbor < dist[neighbor]:
					dist[neighbor] = dist_neighbor
					heappush(heap, (dist_neighbor, neighbor))
	return dist

dist = dijkstra(weight, A, B)

print("%.3f" % dist[B])
