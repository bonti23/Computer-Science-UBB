import numpy as np
import matplotlib.pyplot as plt
from scipy.stats import norm, expon, uniform
from scipy.integrate import quad

# 1. Simularea valorii înălțimii unei persoane
for n in [50]:
    mean_height = 165
    std_dev_height = 10
    heights = np.random.normal(mean_height, std_dev_height, n)

    # i) Histogramă și funcția de densitate
    plt.hist(heights, bins=14, range=(130, 200), density=True, alpha=0.6, color='g', label='Histogram')
    x = np.linspace(130, 200, 500)
    plt.plot(x, norm.pdf(x, mean_height, std_dev_height), 'r-', label='Density Function')
    plt.title(f'Histogram and Density Function (n={n})')
    plt.xlabel('Height (cm)')
    plt.ylabel('Relative Frequency')
    plt.legend()
    plt.grid()
    plt.show()

    # ii) Valoarea medie, deviația standard și proporția [160, 170]
    empirical_mean = np.mean(heights)
    empirical_std = np.std(heights)
    proportion = np.mean((heights >= 160) & (heights <= 170))

    theoretical_mean = mean_height
    theoretical_std = std_dev_height
    theoretical_proportion = norm.cdf(170, mean_height, std_dev_height) - norm.cdf(160, mean_height, std_dev_height)

    print(f"For n={n}:")
    print(f"Empirical Mean: {empirical_mean}, Theoretical Mean: {theoretical_mean}")
    print(f"Empirical Std: {empirical_std}, Theoretical Std: {theoretical_std}")
    print(f"Empirical Proportion [160, 170]: {proportion}, Theoretical Proportion: {theoretical_proportion}")



# 2. Timpul de printare pentru două imprimante
prob_I1 = 0.4
prob_I2 = 0.6

T1 = expon(scale=1/0.2)  # λ = 0.2, scale = 1/λ
T2 = uniform(4, 2)       # Interval [4, 6]

n = 10000
samples_I1 = T1.rvs(size=n)
samples_I2 = T2.rvs(size=n)

# Timpul total de printare (alegere random)
choices = np.random.choice([1, 2], size=n, p=[prob_I1, prob_I2])
times = np.where(choices == 1, samples_I1, samples_I2)

# a) Media și deviația standard
mean_time = np.mean(times)
std_time = np.std(times)
print(f"Mean time: {mean_time}, Std time: {std_time}")

# b) Probabilitatea estimată ca timpul să fie < 5 secunde
prob_less_5_empirical = np.mean(times < 5)
print(f"Empirical P(T < 5): {prob_less_5_empirical}")

# c) Probabilitatea teoretică
prob_less_5_theoretical = (prob_I1 * T1.cdf(5)) + (prob_I2 * T2.cdf(5))
print(f"Theoretical P(T < 5): {prob_less_5_theoretical}")

# 3. Estimarea integralei folosind Monte Carlo
def integrand(x):
    return np.exp(-x**2)

a, b = -1, 3
n = 100000

# Valori uniforme pe [a, b]
x_samples = np.random.uniform(a, b, n)
estimate = (b - a) * np.mean(np.exp(-x_samples**2))
print(f"Monte Carlo Estimate: {estimate}")

# Valoarea exactă
exact_value, _ = quad(integrand, a, b)
print(f"Exact Integral Value: {exact_value}")
