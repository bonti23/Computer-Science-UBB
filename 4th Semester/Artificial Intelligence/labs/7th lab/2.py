#ann tool
import os
import tensorflow as tf
from keras.src.models import Sequential
from keras.src.layers import Dense, Flatten
from keras.src.optimizers import Adam
from keras.src.layers import Dense, Flatten, Conv2D, MaxPooling2D
from keras.src.callbacks import EarlyStopping

from keras.src.legacy.preprocessing.image import ImageDataGenerator

def check_directory():
    print("Lista fișierelor din normal:", os.listdir('dataset/normal'))
    print("Lista fișierelor din sepia:", os.listdir('dataset/sepia'))

def train_ann_keras():
    img_size = (128, 128)
    batch_size = 32

    datagen = ImageDataGenerator(
        rescale=1. / 255,
        validation_split=0.5,
        rotation_range=30,
        width_shift_range=0.2,
        height_shift_range=0.2,
        zoom_range=0.3,
        horizontal_flip=True,
        fill_mode='nearest'
    )

    train_data = datagen.flow_from_directory(
        'dataset',
        target_size=img_size,
        batch_size=batch_size,
        class_mode='binary',
        subset='training',
        shuffle=True
    )

    val_data = datagen.flow_from_directory(
        'dataset',
        target_size=img_size,
        batch_size=batch_size,
        class_mode='binary',
        subset='validation',
        shuffle=False
    )

    print("Imagini de antrenament:", len(train_data.filenames))
    print("Imagini de validare:", len(val_data.filenames))

    model_ann = Sequential([
        Conv2D(32, (3, 3), activation='relu', input_shape=(128, 128, 3)),
        MaxPooling2D(2, 2),
        Conv2D(64, (3, 3), activation='relu'),
        MaxPooling2D(2, 2),
        Flatten(),
        Dense(64, activation='relu'),
        Dense(1, activation='sigmoid')
    ])
    model_ann.compile(optimizer=Adam(), loss='binary_crossentropy', metrics=['accuracy'])
    history_ann = model_ann.fit(train_data, validation_data=val_data, epochs=10)
    return model_ann.evaluate(val_data), history_ann

if __name__ == '__main__':
    check_directory()
    result, history = train_ann_keras()
    print("Testare model Keras:", result)
