alpha = ' abcdefghijklmnopqrstuvwxyz'
n = int(input())
s = input()
res = ''
for c in s:
    res += alpha[(alpha.index(c) + n) % len(alpha)]
print(res)