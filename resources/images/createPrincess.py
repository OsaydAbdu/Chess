import numpy as np
import matplotlib.image as mpimg
import matplotlib.pyplot as plt

img1 = mpimg.imread('whiteRook_copy.png')
img2 = mpimg.imread('whiteKnight_copy.png')
result = np.ndarray((53,45,4))
for i in range(53):
    for j in range(45):
        for k in range(4):
            if (i<45) :
                result[i][j][k] = img2[i][j][k]
for i in range(8):
    for j in range(45) : 
        for k in range(4):
            if (j>= 10 and j <35) :
                result[i+40][j+3][k] = img1[i][j-10][k]
plt.figure()
plt.imshow(result)
plt.show()
plt.imsave('whitePrincess.png', result)
