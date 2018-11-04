#!/usr/bin/python3

def ints():
	return list(map(int, input().split()))
def line():
	hour, t = input().split()
	hh, mm = hour.split(':')
	return int(hh)*60 + int(mm), t

N, = ints()
M, = ints()

nb_people = 0
last_time = None
res = 0
for _ in range(N):
	time, typ = line()
	if typ == 'E':
		nb_people += 1
		if last_time is None and nb_people > M*10:
			last_time = time
	else:
		nb_people -= 1
		if last_time is not None and nb_people <= M*10:
			res += time - last_time
			last_time = None

if last_time is not None:
	res += 23*60 - last_time

print(res)
