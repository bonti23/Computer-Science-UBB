import os
from azure.cognitiveservices.vision.computervision import ComputerVisionClient
from azure.cognitiveservices.vision.computervision.models import OperationStatusCodes
from msrest.authentication import CognitiveServicesCredentials
import time
import re
import Levenshtein

subscription_key = os.environ["VISION_KEY"] = 
endpoint = os.environ["VISION_ENDPOINT"] = 
computervision_client = ComputerVisionClient(endpoint, CognitiveServicesCredentials(subscription_key))
print("VISION_KEY:", os.environ.get("VISION_KEY"))
print("VISION_ENDPOINT:", os.environ.get("VISION_ENDPOINT"))

def recognise(image_path):
    with open(image_path, "rb") as img:
        read_response = computervision_client.read_in_stream(img, raw=True)
    operation_id = read_response.headers["Operation-Location"].split("/")[-1]
    while True:
        read_result = computervision_client.get_read_result(operation_id)
        if read_result.status not in [OperationStatusCodes.not_started, OperationStatusCodes.running]:
            break
        time.sleep(1)
    detected_lines = []
    if read_result.status == OperationStatusCodes.succeeded:
        for page in read_result.analyze_result.read_results:
            for line in page.lines:
                detected_lines.append(line.text)
    return detected_lines

def normalize_text(text):
    text = text.strip()
    text = re.sub(r'\s+', ' ', text)
    text = text.lower()
    return text

ground_truth_lines = [
    "Succes in resolvarea",
    "TEMELOR la",
    "LABORAtoarele de",
    "Inteligenta Artificialà!"
]

def evaluate_localization(detected_lines, ground_truth_lines):
    correct_detections = 0
    for detected_line in detected_lines:
        detected_line = normalize_text(detected_line)
        similarities = [Levenshtein.ratio(detected_line, normalize_text(gt_line)) for gt_line in ground_truth_lines]
        max_similarity = max(similarities)
        if max_similarity > 0.7:
            correct_detections += 1

    accuracy = correct_detections / len(ground_truth_lines)
    return accuracy

image_path = "images/test2.jpeg"
detected_lines = recognise(image_path)

accuracy = evaluate_localization(detected_lines, ground_truth_lines)
print(f"accuracy: {accuracy:.1%}")
