import pytesseract
from PIL import Image, ImageFilter
import re
import Levenshtein

def preprocess_image(image_path):
    """
    7 }
tEMELOR La

KAGOKA toarele che
Pnledigents, bvhecilé
    """
    image = Image.open(image_path)
    gray_image = image.convert('L')
    threshold = 150
    binary_image = gray_image.point(lambda p: p > threshold and 255)
    denoised_image = binary_image.filter(ImageFilter.MedianFilter(size=3))
    return denoised_image

def ocr_with_tesseract(image_path):
    processed_image=preprocess_image(image_path)
    custom_config=r'--oem 3 --psm 6'
    text=pytesseract.image_to_string(processed_image, config=custom_config)
    return text


def normalize_text(text):
    text = text.strip()
    text = re.sub(r'\s+', ' ', text)
    text = text.lower()
    return text


def levenshtein_distance(recognised_text, expected_text):
    return Levenshtein.distance(recognised_text, expected_text)

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

expected_text="Succes in resolvarea\nTEMELOR la\nLABORA toarele de\nInteligenta Artificialà!"
image_path = "images/test2.jpeg"
text = ocr_with_tesseract(image_path)
print(evaluate_localization(text, expected_text))
