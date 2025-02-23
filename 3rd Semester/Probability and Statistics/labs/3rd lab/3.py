import numpy as np
import matplotlib.pyplot as plt
from scipy.stats import binom

# a) Generate a list of 1000 values for X
n=5  # number of trials (draws)
p=0.6  # probability of drawing a ball with number 1
size=1000  # size of the sample

# Generate the values for the random variable X
X_values=binom.rvs(n, p, size=size)

# b) Plot the histogram of relative frequencies and theoretical values
plt.figure(figsize=(10, 6))

# Histogram of relative frequencies
plt.hist(X_values, bins=np.arange(n+2) - 0.5, density=True, alpha=0.6, color='g', label='Relative Frequencies')

# Theoretical values (probabilities for each possible value of X)
x=np.arange(0, n+1)
pmf_values = binom.pmf(x, n, p)
plt.bar(x, pmf_values, alpha=0.6, color='b', label='Theoretical Values', width=0.3)

plt.xlabel('Value of X')
plt.ylabel('Relative Frequency')
plt.title('Histogram of Relative Frequencies and Theoretical Values')
plt.legend()
plt.xticks(x)
plt.show()

# c) Estimate the probability P(2 < X ≤ 5) and the theoretical value
# Estimate the probability from the generated data
P_estimate = np.sum((X_values > 2) & (X_values <= 5)) / size

# Theoretical value of the probability P(2 < X ≤ 5)
P_theoretical = binom.cdf(5, n, p) - binom.cdf(2, n, p)

print("Estimated Probability P(2 < X ≤ 5):", P_estimate)
print("Theoretical Probability P(2 < X ≤ 5):", P_theoretical)
