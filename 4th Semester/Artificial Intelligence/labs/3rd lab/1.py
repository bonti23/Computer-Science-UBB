import os
from azure.cognitiveservices.vision.computervision import ComputerVisionClient
from azure.cognitiveservices.vision.computervision.models import OperationStatusCodes
from msrest.authentication import CognitiveServicesCredentials
import time
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
    extracted_text = []
    if read_result.status == OperationStatusCodes.succeeded:
        for text_result in read_result.analyze_result.read_results:
            for line in text_result.lines:
                extracted_text.append(line.text)
    return "\n".join(extracted_text)

def levenshtein_distance(recognised_text, expected_text):
    return Levenshtein.distance(recognised_text, expected_text)

def CER(recognised_text, expected_text):
    distance = levenshtein_distance(recognised_text, expected_text)
    cer_value= distance / max(len(recognised_text), 1)
    return round(cer_value * 100, 2)

def WER(recognised_text, expected_text):
    words1 = recognised_text.split()
    words2 = expected_text.split()
    distance = levenshtein_distance(" ".join(words1), " ".join(words2))
    wer_value = distance / max(len(words2), 1)
    return round(wer_value * 100, 2)

def main():
    print("1. Detect the text.")
    print("2. Calculate the Levenshtein Distance")
    print("3. Character Error Rate")
    print("4. Word Error Rate")
    print("5. Exit...")

image_path="images/test2.jpeg"
expected_text="Succes in resolvarea\nTEMELOR la\nLABORA toarele de\nInteligenta Artificialà!"
recognised_text = recognise(image_path)

def runner():
    main()
    while(True):
        index=int(input("Choose your option: "))
        if index == 1:
            print(recognise(image_path))
        elif index == 2:
            print(levenshtein_distance(recognised_text, expected_text))
        elif index == 3:
            print(CER(recognised_text, expected_text))
        elif index == 4:
            print(WER(recognised_text, expected_text))
        elif index == 5:
            print("Thank you for using this program")
            break
        else:
            print("Wrong input")

runner()
