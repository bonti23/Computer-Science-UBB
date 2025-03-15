import csv
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns

filename = "survey/surveyDataSience.csv"
experience_mapping = {
    "< 1 years": 0.5,
    "1-3 years": 2,
    "3-5 years": 4,
    "5-10 years": 7.5,
    "10-20 years": 15,
    "20+ years": 25,
    "I have never written code": 0
}

def load_data():
    with open(filename, newline='', encoding='utf-8') as csv_file:
        csv_reader = csv.reader(csv_file)
        data = next(csv_reader)
        next(csv_reader)
        allData = list(csv_reader)
    return data, allData

def filter_python_users(names, allData, country=None, gender=None):
    required_columns = ['Q1', 'Q2', 'Q3', 'Q7_Part_1', 'Q6']
    for col in required_columns:
        if col not in names:
            print(f"Column '{col}' not found.")
            return []
    index_age = names.index('Q1')
    index_gender = names.index('Q2')
    index_country = names.index('Q3')
    index_python = names.index('Q7_Part_1')
    index_experience = names.index('Q6')
    filtered_data = []
    for row in allData:
        if row[index_python] == "Python":
            if country and row[index_country] != country:
                continue
            if gender and row[index_gender] != gender:
                continue
            filtered_data.append((row[index_age], experience_mapping.get(row[index_experience], None)))
    return filtered_data

def plot_histogram(data, title):
    ages = [row[0] for row in data]
    plt.figure(figsize=(8, 5))
    sns.countplot(x=ages, order=sorted(set(ages)), hue=ages, palette="viridis", legend=False)
    plt.title(title)
    plt.xlabel("Age category")
    plt.ylabel("Number of respondents")
    plt.xticks(rotation=45)
    plt.show()

def plot_boxplot(data):
    experience_years = [row[1] for row in data if row[1] is not None]
    plt.figure(figsize=(6, 4))
    sns.boxplot(y=experience_years, hue=[""] * len(experience_years), palette="coolwarm", legend=False)
    plt.title("Boxplot - Outliers for programming experience")
    plt.ylabel("Years of experience")
    plt.show()

def main():
    names, allData = load_data()
    python_users = filter_python_users(names, allData)
    plot_histogram(python_users, "age distribution -> python programmers")
    python_romania = filter_python_users(names, allData, country="Romania")
    plot_histogram(python_romania, "age distribution -> python programmers in Romania")
    python_romania_women = filter_python_users(names, allData, country="Romania", gender="Woman")
    plot_histogram(python_romania_women, "age distribution-> python programmers in Romania who are women")
    plot_boxplot(python_users)

main()
