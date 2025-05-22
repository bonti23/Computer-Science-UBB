import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
from sklearn.linear_model import LinearRegression
from sklearn.metrics import mean_squared_error, r2_score
from IPython.display import display

file_path = "data/v3_world-happiness-report-2017.csv"
df = pd.read_csv(file_path)

display(df.head())

features = {
    "GDP": "Economy..GDP.per.Capita.",
    "Family": "Family",
    "Freedom": "Freedom",
    "Happiness": "Happiness.Score"
}


def perform_regression(x_feature, y_feature):
    X = df[[features[x_feature]]].values  # Independent variable
    y = df[features[y_feature]].values  # Dependent variable

    # Train model
    model = LinearRegression()
    model.fit(X, y)

    # Predictions
    y_pred = model.predict(X)

    # Evaluate model
    mse = mean_squared_error(y, y_pred)
    r2 = r2_score(y, y_pred)

    # Plot results
    plt.figure(figsize=(8, 5))
    plt.scatter(X, y, color='blue', label='Actual data')
    plt.plot(X, y_pred, color='red', linewidth=2, label='Regression Line')
    plt.xlabel(x_feature)
    plt.ylabel(y_feature)
    plt.title(f'Regression: {y_feature} vs {x_feature}')
    plt.legend()
    plt.show()

    print(f"Model for {y_feature} vs {x_feature}:")
    print(f"Mean Squared Error: {mse}")
    print(f"R-squared Score: {r2}\n")


df = df.dropna()
perform_regression("GDP", "Happiness")  # Demo example
perform_regression("Family", "Happiness")  # Homework
perform_regression("GDP", "Freedom")  # Homework
