import os
import cv2
import matplotlib.pyplot as plt
import matplotlib.patches as patches
from azure.cognitiveservices.vision.computervision import ComputerVisionClient
from azure.cognitiveservices.vision.computervision.models import VisualFeatureTypes
from msrest.authentication import CognitiveServicesCredentials


subscription_key = os.environ["VISION_KEY"] = 
endpoint = os.environ["VISION_ENDPOINT"] = 
computervision_client = ComputerVisionClient(endpoint, CognitiveServicesCredentials(subscription_key))
print("VISION_KEY:", os.environ.get("VISION_KEY"))
print("VISION_ENDPOINT:", os.environ.get("VISION_ENDPOINT"))

image_folder = "images/bikes"
image_files = [f for f in os.listdir(image_folder) if f.endswith(('.jpg', '.png'))]

# Etichete reale (ground truth) pentru localizarea bicicletelor
manual_bounding_boxes = {}


def label_manual_bounding_boxes(image_path):
    image = cv2.imread(image_path)
    clone = image.copy()
    bbox = cv2.selectROI("Select Bicycle", clone, fromCenter=False, showCrosshair=True)
    cv2.destroyAllWindows()
    return [int(bbox[0]), int(bbox[1]), int(bbox[2]), int(bbox[3])]


def intersection_over_union(boxA, boxB):
    xA = max(boxA[0], boxB[0])
    yA = max(boxA[1], boxB[1])
    xB = min(boxA[0] + boxA[2], boxB[0] + boxB[2])
    yB = min(boxA[1] + boxA[3], boxB[1] + boxB[3])

    interArea = max(0, xB - xA) * max(0, yB - yA)
    boxAArea = boxA[2] * boxA[3]
    boxBArea = boxB[2] * boxB[3]

    iou = interArea / float(boxAArea + boxBArea - interArea)
    return iou


def draw_bounding_boxes(image_path, predicted_bb=None, actual_bb=None):
    image = plt.imread(image_path)
    fig, ax = plt.subplots(1)
    ax.imshow(image)

    if actual_bb:
        rect = patches.Rectangle((actual_bb[0], actual_bb[1]), actual_bb[2], actual_bb[3],
                                 linewidth=2, edgecolor='blue', facecolor='none', label='Manual')
        ax.add_patch(rect)

    if predicted_bb:
        rect = patches.Rectangle((predicted_bb[0], predicted_bb[1]), predicted_bb[2], predicted_bb[3],
                                 linewidth=2, edgecolor='red', facecolor='none', label='AI')
        ax.add_patch(rect)

    plt.show()


# Etichetare manuala
for image_file in image_files:
    image_path = os.path.join(image_folder, image_file)
    print(f"Etichetare manuala pentru {image_file}. Selectati regiunea si apasati tasta ENTER.")
    manual_bounding_boxes[image_file] = label_manual_bounding_boxes(image_path)

# Detectare biciclete si comparare cu etichetele manuale
processed_images = set()
for image_file in image_files:
    image_path = os.path.join(image_folder, image_file)
    with open(image_path, "rb") as img:
        result = computervision_client.analyze_image_in_stream(img, visual_features=[VisualFeatureTypes.objects])

    detected = False
    for obj in result.objects:
        if obj.object_property.lower() == "bicycle":
            detected = True
            predicted_bb = [obj.rectangle.x, obj.rectangle.y, obj.rectangle.w, obj.rectangle.h]
            print("Coordonate: ", predicted_bb)
            actual_bb = manual_bounding_boxes.get(image_file, None)

            if image_file not in processed_images:
                draw_bounding_boxes(image_path, predicted_bb, actual_bb)
                processed_images.add(image_file)

            # calcul eroare detectie (MSE)
            if actual_bb:
                mse_error = sum((pred - actual) ** 2 for pred, actual in zip(predicted_bb, actual_bb)) / 4
                iou_score = intersection_over_union(predicted_bb, actual_bb)

                print(f"{image_file} - MSE: {mse_error:.2f}, IoU: {iou_score:.2f}")

    if not detected:
        print(f"nicio bicicleta detectata în {image_file}.")

print("detectare si comparare finalizate.")
