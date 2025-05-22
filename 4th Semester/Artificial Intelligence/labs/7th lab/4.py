#ann cod propriu
import tensorflow as tf
from keras import Input, Model
from keras.src.models import Sequential
from keras.src.layers import Conv2D, MaxPooling2D, Flatten, Dense
from keras.src.legacy.preprocessing.image import ImageDataGenerator

def train_ann_custom():
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

    inputs = Input(shape=(128, 128, 3))
    x = Flatten()(inputs)
    x = Dense(128, activation='relu')(x)
    x = Dense(64, activation='relu')(x)
    output = Dense(1, activation='sigmoid')(x)

    model_ann_custom = Model(inputs=inputs, outputs=output)
    model_ann_custom.compile(optimizer='adam', loss='binary_crossentropy', metrics=['accuracy'])
    history = model_ann_custom.fit(train_data, validation_data=val_data, epochs=10)
    return model_ann_custom.evaluate(val_data), history

if __name__ == '__main__':
    result, history = train_ann_custom()
    print("ANN propriu:", result)
