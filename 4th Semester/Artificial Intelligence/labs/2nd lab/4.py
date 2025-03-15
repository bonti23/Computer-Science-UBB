import csv
import math
from collections import Counter
import numpy as np
from matplotlib import pyplot as plt
import random
import os
from PIL import Image

def clipping(values, min_threshold, max_threshold):
    return [max(min(x, max_threshold), min_threshold) for x in values]

def log_scaling(values):
    return [math.log(x + 1) for x in values]

filename = "survey/surveyDataSience.csv"
def normalize_study_duration():
    study_durations = {"Bachelor's degree": 3, "Master's degree": 2, "Doctoral degree": 3}
    with open(filename, newline='', encoding='utf-8') as csv_file:
        csv_reader = csv.reader(csv_file)
        names = next(csv_reader)
        next(csv_reader)
        allData = list(csv_reader)

    durations = [study_durations.get(row[names.index('Q4')], 0) for row in allData]
    clipped_values = clipping(durations, min(durations), max(durations))
    log_scaled = log_scaling(durations)

    print("Clipping:", clipped_values)
    print("*********************************************************")
    print("Log scaling:", log_scaled)

    fig, (ax1, ax2, ax3) = plt.subplots(1, 3, figsize=(15, 5))
    ax1.hist(durations)
    ax1.set_title('Original')
    ax2.hist(clipped_values)
    ax2.set_title('Clipped')
    ax3.hist(log_scaled)
    ax3.set_title('Log Scaled')
    plt.show()

def normalize_experience():
    experience_mapping = {
        "< 1 years": 0.5, "1-3 years": 2, "3-5 years": 4,
        "5-10 years": 7.5, "10-20 years": 15, "20+ years": 25,
        "I have never written code": 0
    }
    with open(filename, newline='', encoding='utf-8') as csv_file:
        csv_reader = csv.reader(csv_file)
        names = next(csv_reader)
        next(csv_reader)
        allData = list(csv_reader)

    experience_years = [experience_mapping[row[names.index('Q6')]] for row in allData if
                        row[names.index('Q6')] in experience_mapping]
    clipped_values = clipping(experience_years, min(experience_years), max(experience_years))
    log_scaled = log_scaling(experience_years)
    print("*********************************************************")
    print("Clipping:", clipped_values)
    print("*********************************************************")
    print("Log scaling:", log_scaled)
    fig, (ax1, ax2, ax3) = plt.subplots(1, 3, figsize=(15, 5))
    ax1.hist(experience_years)
    ax1.set_title('Original')
    ax2.hist(clipped_values)
    ax2.set_title('Clipped')
    ax3.hist(log_scaled)
    ax3.set_title('Log Scaled')
    plt.show()

folder="images"
images=[image for image in os.listdir(folder) if image.endswith((".jpg", ".png", ".webp"))]
def clipping2(values, min_threshold, max_threshold):
    return np.clip(values, min_threshold, max_threshold)

def log_scaling2(values):
    return np.log1p(values)

def normalize_pixels():
    if images:
        chosen = random.choice(images)
        img = Image.open(os.path.join(folder, chosen)).convert("L")
        pixel_values = np.array(img, dtype=np.float32)
        clipped_values = clipping2(pixel_values, np.min(pixel_values), np.max(pixel_values))
        log_scaled = log_scaling2(pixel_values)
        fig, (ax1, ax2, ax3) = plt.subplots(1, 3, figsize=(15, 5))
        ax1.imshow(img, cmap="gray")
        ax1.set_title("Original")
        ax1.axis("off")
        ax2.imshow(clipped_values, cmap="gray")
        ax2.set_title("Clipped")
        ax2.axis("off")
        ax3.imshow(log_scaled, cmap="gray")
        ax3.set_title("Log Scaled")
        ax3.axis("off")
        plt.tight_layout()
        plt.show()
    else:
        print("It hasn't found any image.")

file="text/texts.txt"
def word_occurrences():
    with open(file, "r", encoding="utf-8") as f:
        text = f.read()
    sentences = text.split('.!')
    for i, sentence in enumerate(sentences, 1):
        words = sentence.split()
        word_count = Counter(words)
        occurrences = list(word_count.values())
        clipped_values = [min(x, 5) for x in occurrences]
        log_scaled_values = [math.log(x + 1) for x in occurrences]
        print(f"Sentence {i}: {word_count}")
        print(f"Clipped occurrences: {clipped_values}")
        print(f"Log-scaled occurrences: {log_scaled_values}")

def main():
    print("Menu")
    print("1. Normalize study duration")
    print("2. Normalize experience")
    print("3. Normalize pixels")
    print("4. Word occurrences")

def runner():
    main()
    while(True):
        index=int(input("Select an option: "))
        if index==1:
            normalize_study_duration()
        elif index==2:
            normalize_experience()
        elif index==3:
            normalize_pixels()
        elif index==4:
            word_occurrences()
        elif index==5:
            print("Exiting...")
            break
        else:
            print("Invalid option")

runner()