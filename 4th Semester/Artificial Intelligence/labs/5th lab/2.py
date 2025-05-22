import pandas as pd
import matplotlib.pyplot as plt
from IPython.display import display

file_path = "data/v1_world-happiness-report-2017.csv"
df = pd.read_csv(file_path)

display(df.head())

features = {
    "GDP": "Economy..GDP.per.Capita.",
    "Family": "Family",
    "Freedom": "Freedom",
    "Happiness": "Happiness.Score"
}

def least_squares_regression(x_feature, y_feature):
    X = df[features[x_feature]].values
    y = df[features[y_feature]].values

    # Compute the least squares manually
    n = len(X)
    sum_x = sum(X)
    sum_y = sum(y)
    sum_xy = sum(X * y)
    sum_x2 = sum(X * X)

    # Compute coefficients
    slope = (n * sum_xy - sum_x * sum_y) / (n * sum_x2 - sum_x ** 2)
    intercept = (sum_y - slope * sum_x) / n

    #predictii
    y_pred = [slope * xi + intercept for xi in X]

    #Compute Mean Squared Error (MSE) manually
    mse = sum((yi - y_hat) ** 2 for yi, y_hat in zip(y, y_pred)) / n

    #Compute R-squared manually
    mean_y = sum_y / n
    ss_total = sum((yi - mean_y) ** 2 for yi in y)
    ss_residual = sum((yi - y_hat) ** 2 for yi, y_hat in zip(y, y_pred))
    r2 = 1 - (ss_residual / ss_total)

    plt.figure(figsize=(8, 5))
    plt.scatter(X, y, color='blue', label='Actual data')
    plt.plot(X, y_pred, color='red', linewidth=2, label='Regression Line')
    plt.xlabel(x_feature)
    plt.ylabel(y_feature)
    plt.title(f'Regression: {y_feature} vs {x_feature}')
    plt.legend()
    plt.show()

    print(f"Model for {y_feature} vs {x_feature}:")
    print(f"Slope: {slope}")
    print(f"Intercept: {intercept}")
    print(f"Mean Squared Error: {mse}")
    print(f"R-squared Score: {r2}\n")

least_squares_regression("GDP", "Happiness")  # Demo example
least_squares_regression("Family", "Happiness")  # Homework
least_squares_regression("GDP", "Freedom")
