import cv2
import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3'
import tensorflow as tf
import numpy as np
import matplotlib.pyplot as plt

CATEGORIES = ["A","B","C","D","E","F","G","H","I","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y"]

def prepare(filepath):
    IMG_SIZE = (400, 256)
    img_array = cv2.imread(filepath, cv2.IMREAD_GRAYSCALE)
    new_array = cv2.resize(img_array, (IMG_SIZE[0], IMG_SIZE[1]))

    return new_array.reshape(-1, IMG_SIZE[0], IMG_SIZE[1], 1)

model = tf.keras.models.load_model("Aakkosia-AllPics-1-CNN.model")
#print(model.summary())

img= prepare('image.jpg')

prediction = model.predict(img)

results = np.reshape(prediction,(24,1))
print(results)

num = 0
prev = 0.0

for i in range(24):

        current = float(np.asarray(results[i]))

        if (prev < current):
                prev = current
                num = i
        elif (prev > current):
                prev = prev

result = CATEGORIES[num]

print("Prediction is: ", result)
