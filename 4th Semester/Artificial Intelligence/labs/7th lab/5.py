#cnn cod propriu

import tensorflow as tf

from keras.src.models import Sequential
from keras.src.layers import Dense, Flatten, Conv2D, MaxPooling2D
from keras.src.optimizers import Adam
from keras.src.legacy.preprocessing.image import ImageDataGenerator

def train_cnn_custom():
    img_size = (128, 128)
    batch_size = 32
    datagen = ImageDataGenerator(rescale=1. / 255, validation_split=0.5)

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

    # Model CNN personalizat
    model_cnn_custom = Sequential([
        Conv2D(32, (3, 3), activation='relu', input_shape=(128, 128, 3)),
        MaxPooling2D(2, 2),
        Conv2D(64, (3, 3), activation='relu'),
        MaxPooling2D(2, 2),
        Flatten(),
        Dense(64, activation='relu'),
        Dense(1, activation='sigmoid')
    ])

    model_cnn_custom.compile(optimizer='adam', loss='binary_crossentropy', metrics=['accuracy'])
    history = model_cnn_custom.fit(train_data, validation_data=val_data, epochs=10) 
    return model_cnn_custom.evaluate(val_data), history

if __name__ == '__main__':
    result, history = train_cnn_custom()
    print("CNN propriu:", result)
