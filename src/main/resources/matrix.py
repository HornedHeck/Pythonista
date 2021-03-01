def emptyMatrix(n):
    result = []
    for i in range(n):
        result.append([])
        for j in range(n):
            result[i].append(0)
    return result

def mul(X, Y):
    result = emptyMatrix(len(X))
    for i in range(len(X)):
        for j in range(len(Y[0])):
            for k in range(len(Y)):
                result[i][j] += X[i][k] * Y[k][j]
    return result

def transpose(X):
    result = emptyMatrix(len(X))
    for i in range(len(X)):
        for j in range(len(X[0])):
            result[j][i] = X[i][j]
    return result

a = [[1,2],[2,3]]
b = [[3,2],[2,1]]
c = mul(a,b)
print(c)
d = transpose(c)
print(d)
