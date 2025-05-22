from azure.cognitiveservices.vision.computervision import ComputerVisionClient
from azure.cognitiveservices.vision.computervision.models import OperationStatusCodes
from azure.cognitiveservices.vision.computervision.models import VisualFeatureTypes
from msrest.authentication import CognitiveServicesCredentials
import os
import sys
import time
import numpy as np
import matplotlib.pyplot as plt

subscription_key = os.environ["VISION_KEY"] = 
endpoint = os.environ["VISION_ENDPOINT"] = 
computervision_client = ComputerVisionClient(endpoint, CognitiveServicesCredentials(subscription_key))
print("VISION_KEY:", os.environ.get("VISION_KEY"))
print("VISION_ENDPOINT:", os.environ.get("VISION_ENDPOINT"))

image_folder = "images/bikes"
image_files = [f for f in os.listdir(image_folder) if f.endswith(('.jpg', '.png'))]

images = {
    "bike1.jpg": "bicycle",
    "bike02.jpg": "bicycle",
    "traffic01.jpg": "no_bicycle",
    "traffic02.jpg": "no_bicycle"
}

TP=FP=TN=FN=0

for image_file in image_files:
    image_path = os.path.join(image_folder, image_file)
    with open(image_path, "rb") as img:
        try:
            result = computervision_client.analyze_image_in_stream(img, visual_features=[VisualFeatureTypes.tags])
        except Exception as e:
            print(f"Error processing {image_file}: {e}")
            time.sleep(15)
            continue

    detected_bicycle = any(tag.name.lower() == "bicycle" for tag in result.tags)
    predicted_label = "bicycle" if detected_bicycle else "no_bicycle"

    actual_label = images.get(image_file, "no_bicycle")

    if predicted_label == "bicycle" and actual_label == "bicycle":
        TP += 1  #bicicleta detectata
    elif predicted_label == "bicycle" and actual_label == "no_bicycle":
        FP += 1  # fals pozitiv
    elif predicted_label == "no_bicycle" and actual_label == "no_bicycle":
        TN += 1  # detectat fara bicicleta
    elif predicted_label == "no_bicycle" and actual_label == "bicycle":
        FN += 1  # fals negativ

accuracy = (TP+TN) / (TP+TN+FP+FN)
precision = TP/(TP + FP) if (TP + FP) > 0 else 0
recall = TP / (TP + FN) if (TP + FN) > 0 else 0
f1_score = 2 * (precision * recall) / (precision + recall) if (precision + recall) > 0 else 0

print(f"accuracy: {accuracy:.2f}")
print(f"precision: {precision:.2f}")
print(f"recall: {recall:.2f}")
print(f"f1_score: {f1_score:.2f}")
