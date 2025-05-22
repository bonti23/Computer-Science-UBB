import tensorflow as tf
tf.config.run_functions_eagerly(True)

from keras.src.models import Sequential
from keras.src.layers import Dense, Flatten
from keras.src.optimizers import Adam, SGD, RMSprop
from keras.src.legacy.preprocessing.image import ImageDataGenerator
import matplotlib.pyplot as plt
import os

def tune_hyperparameters():
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
        shuffle=True
    )

    optimizer_classes = [Adam, SGD, RMSprop]
    epochs_list = [5, 10, 15]

    os.makedirs("plots", exist_ok=True)

    for optimizer_class in optimizer_classes:
        for num_epochs in epochs_list:
            optimizer = optimizer_class()
            optimizer_name = optimizer.__class__.__name__
            print(f"Antrenare cu optimizator {optimizer_name} și {num_epochs} epoci")

            model = Sequential([
                Flatten(input_shape=(128, 128, 3)),
                Dense(128, activation='relu'),
                Dense(64, activation='relu'),
                Dense(1, activation='sigmoid')
            ])

            model.compile(optimizer=optimizer, loss='binary_crossentropy', metrics=['accuracy'])
            history = model.fit(train_data, validation_data=val_data, epochs=num_epochs)

            # Plot și salvare grafic
            plt.figure()
            plt.plot(history.history['accuracy'], label='Train Accuracy')
            plt.plot(history.history['val_accuracy'], label='Val Accuracy')
            plt.title(f'Accuracy - {optimizer_name} - {num_epochs} epoci')
            plt.xlabel('Epocă')
            plt.ylabel('Acuratețe')
            plt.legend()
            plot_filename = f'plots/accuracy_{optimizer_name}_{num_epochs}ep.png'
            plt.savefig(plot_filename)
            plt.close()
            print(f"Grafic salvat: {plot_filename}")

if __name__ == '__main__':
    tune_hyperparameters()
