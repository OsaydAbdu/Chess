import numpy as np
import matplotlib.image as mpimg
import matplotlib.pyplot as plt

img1 = mpimg.imread('blackBishop_copy.png')
img2 = mpimg.imread('blackKnight_copy.png')
result = np.ndarray((51,45,4))
for i in range(51):
    for j in range(45):
        for k in range(4):
            if (i<45) :
                result[i][j][k] = img2[i][j][k]
for i in range(6):
    for j in range(45) : 
        for k in range(4):
            if (j>= 5 and j <40) :
                result[i+40][j+3][k] = img1[i][j-5][k]
plt.figure()
plt.imshow(result)
plt.show()
plt.imsave('empress.png', result)
