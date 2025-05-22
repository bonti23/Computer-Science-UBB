import cv2
import os
import numpy as np
from glob import glob
import os
import csv


normal_folder = 'dataset/normal/'
sepia_folder = 'dataset/sepia/'

with open('dataset.csv', mode='w', newline='') as file:
    writer = csv.writer(file)
    writer.writerow(['image_path', 'label'])

    # Adaugă imagini normale (label=0)
    for img_name in os.listdir(normal_folder):
        if img_name.endswith(('.jpg', '.png')):
            img_path = os.path.join(normal_folder, img_name)
            writer.writerow([img_path, 0])  # 0 normale

    # Adaugă imagini sepia (label=1)
    for img_name in os.listdir(sepia_folder):
        if img_name.endswith(('.jpg', '.png')):
            img_path = os.path.join(sepia_folder, img_name)
            writer.writerow([img_path, 1])  # 1 sepia

print("datasetul a fost creat si salvat in dataset.csv.")


def apply_sepia(image):
    sepia_filter = np.array([[0.272, 0.534, 0.131],
                             [0.349, 0.686, 0.168],
                             [0.393, 0.769, 0.189]])
    sepia_img = cv2.transform(image, sepia_filter)
    sepia_img = np.clip(sepia_img, 0, 255)
    return sepia_img.astype(np.uint8)

input_folder = 'dataset/normal'
output_folder = 'dataset/sepia'
os.makedirs(output_folder, exist_ok=True)

for img_path in glob(f'{input_folder}/*.jpg'):
    img = cv2.imread(img_path)
    sepia = apply_sepia(img)
    filename = os.path.basename(img_path)
    cv2.imwrite(f'{output_folder}/{filename}', sepia)

print("fisier 'sepia' complet")
