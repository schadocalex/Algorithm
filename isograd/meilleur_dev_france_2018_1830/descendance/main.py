#!/usr/bin/python3

import sys
from collections import defaultdict

N = int(input())

nodes = set()
children = defaultdict(lambda:[])
parents = defaultdict(lambda:[])
for i in range(N):
	p, e = input().split()
	nodes.add(p)
	nodes.add(e)
	children[p].append(e)
	parents[e].append(p)

def get_score(x):
	if len(children[x]) == 0:
		return 1
	else:
		return sum(get_score(y) for y in children[x])

max_score = 0
max_nodes = None
for node in (x for x in nodes if len(parents[x]) == 0):
	score = get_score(node)

	if score > max_score:
		max_score = score
		max_nodes = [node]
	elif score >= max_score:
		max_nodes.append(node)

print(' '.join(max_nodes))