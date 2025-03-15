import os
import random
#pil -> pillow
from PIL import Image, ImageFilter
import matplotlib.pyplot as plt


folder="images"
images=[image for image in os.listdir(folder) if image.endswith((".jpg", ".png", ".webp"))]

def print_an_image():
    if images:
        chosen=random.choice(images)
        img=Image.open(os.path.join(folder, chosen))
        img.show()
    else:
        print("It hasn't found any image.")

def resize():
    size=(128, 128)
    number_of_images=len(images)
    columns=min(3, number_of_images)
    rows=number_of_images//columns
    fig, axes = plt.subplots(rows, columns, figsize=(columns,rows))
    axes=[axes] if rows==1 else axes
    for ax, img_name in zip(axes.flat, images):
        img=Image.open(os.path.join(folder, img_name)).resize(size)
        ax.imshow(img)
        ax.axis("off")
    plt.tight_layout()
    plt.show()

def gray():
    number_of_images = len(images)
    columns = min(3, number_of_images)
    rows = number_of_images // columns
    fig, axes = plt.subplots(rows, columns, figsize=(columns, rows))
    axes = [axes] if rows == 1 else axes
    for ax, img_name in zip(axes.flat, images):
        img = Image.open(os.path.join(folder, img_name)).convert("L")
        ax.imshow(img, cmap="gray")
        ax.axis("off")
    plt.tight_layout()
    plt.show()

def blur():
    if images:
        chosen = random.choice(images)
        img = Image.open(os.path.join(folder, chosen))
        blurred = img.filter(ImageFilter.GaussianBlur(radius=5))
        fig, axes = plt.subplots(1, 2, figsize=(6, 3))
        axes[0].imshow(img)
        axes[0].set_title("original")
        axes[0].axis("off")
        axes[1].imshow(blurred)
        axes[1].set_title("blurred")
        axes[1].axis("off")
        plt.tight_layout()
        plt.show()
    else:
        print("It hasn't found any image.")

def edges():
    chosen = random.choice(images)
    img = Image.open(os.path.join(folder, chosen))
    edges = img.convert("L").filter(ImageFilter.FIND_EDGES)
    fig, axes = plt.subplots(1, 2, figsize=(6, 3))
    axes[0].imshow(img)
    axes[0].set_title("Original")
    axes[0].axis("off")
    axes[1].imshow(edges, cmap="gray")
    axes[1].set_title("Edges Detected")
    axes[1].axis("off")
    plt.tight_layout()
    plt.show()

def main():
    print('Menu')
    print('1. Show a random image')
    print('2. Resize all images and display them in a table')
    print('3. Convert all images to grayscale and display them in a table')
    print('4. Blur an image and show the before-and-after result')
    print('5. Detect edges in an image and show the before-and-after result')
    print('6. Exit')

def runner():
    main()
    while(True):
        index=int(input("Choose an option: "))
        if index==1:
            print_an_image()
        elif index==2:
            resize()
        elif index==3:
            gray()
        elif index==4:
            blur()
        elif index==5:
            edges()
        elif index==6:
            print("Thank you!")
            break
        else:
            print("That's not a valid option.")

runner()

