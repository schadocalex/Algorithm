#!/usr/bin/python3

from collections import namedtuple

class Graph:
	def __init__(self):
		self.neighbors = []
		self.name2node = {}
		self.node2name = []
		self.weight = []

	def __len__(self):
		return len(self.node2name)

	def __getitem__(self, v):
		return self.neighbors[v]

	def add_node(self, name):
		assert name not in self.name2node
		self.name2node[name] = len(self.name2node)
		self.node2name.append(name)
		self.neighbors.append([])
		self.weight.append({})
		return self.name2node[name]

	def add_edge(self, name_u, name_v, weight_uv=None):
		self.add_arc(name_u, name_v, weight_uv)
		self.add_arc(name_v, name_u, weight_uv)

	def add_arc(self, name_u, name_v, weight_uv=None):
		u = self.name2node[name_u]
		v = self.name2node[name_v]
		self.neighbors[u].append(v)
		self.weight[u][v] = weight_uv

Circle = namedtuple('Circle', ['x', 'y', 'r', 'h'])

def inside(a, b):
	if a.r >= b.r:
		return False
	dist2 = (a.x - b.x)**2 + (a.y - b.y)**2
	return dist2 < b.r**2

N = int(input())
M = N+1 # with world
circles = [Circle(*map(int, input().split(' '))) for _ in range(N)]

graph = Graph()
graph.add_node('world')

for circle in circles:
	graph.add_node(circle)
for circle in circles:
	circles_outside = [x for x in circles if inside(circle, x)]
	if len(circles_outside) > 0:
		parent = min(circles_outside, key=lambda x:x.r)
		graph.add_edge(parent, circle, abs(parent.h - circle.h))
	else:
		graph.add_edge('world', circle, abs(circle.h))

def dfs(graph, weight, start):
	seen = [False] * M
	seen[start] = True
	dist = [0] * M
	to_visit = [start]
	while to_visit:
		node = to_visit.pop()
		for neighbor in graph[node]:
			if not seen[neighbor]:
				seen[neighbor] = True
				to_visit.append(neighbor)
				dist[neighbor] = dist[node] + weight[node][neighbor]
	return dist

dist = dfs(graph, graph.weight, 0)
max_start = dist.index(max(dist))
dist2 = dfs(graph, graph.weight, max_start)

print(max(dist2))