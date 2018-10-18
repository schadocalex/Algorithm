#!/usr/bin/python3

import itertools

BEATS = {
    'E': 'F',
    'F': 'P',
    'P': 'E'
}

N = int(input())
my_cats = list(input())
M = int(input())
his_cats = list(input())

def fight(me, him):
    if len(me) == 0 and len(him) == 0:
        return 0
    elif len(me) == 0:
        return -1
    elif len(him) == 0:
        return 1
    
    if me[0] == him[0]:
        return fight(me[1:], him[1:])
    elif BEATS[me[0]] == him[0]:
        return fight(me[:], him[1:])
    else:
        return fight(me[1:], him[:])

score_max, cats = max((fight(x, his_cats[:]), x) for x in itertools.permutations(my_cats))

print(['=','+','-'][score_max] + ''.join(cats))