alpha = ' abcdefghijklmnopqrstuvwxyz'
n = int(input())
s = input()
res = ''
for c in s:
    res += alpha[(index(alpha , c) + n) % len(alpha)]
print(res)