import csv
from collections import Counter
import numpy as np

import matplotlib.pyplot as plt
from math import log, sqrt

filename = "survey/surveyDataSience.csv"
def loadDataMoreInputs():
    data = []
    dataNames = []
    with open(filename) as csv_file:
        csv_reader=csv.reader(csv_file, delimiter=',')
        line_count=0
        for row in csv_reader:
            if line_count==0:
                dataNames=row
            elif line_count==1:
                pass
            else:
                data.append(row)
            line_count+=1
    return data, dataNames

def people():
    data, dataNames = loadDataMoreInputs()
    print(len(data))

def attributes():
    data, dataNames = loadDataMoreInputs()
    print(len(dataNames))

def complete_answers():
    with open(filename, newline='', encoding='utf-8') as csv_file:
        csv_reader = csv.reader(csv_file)
        data = list(csv_reader)[2:]
        complete_respondents = sum(1 for row in data if all(cell.strip() for cell in row))
        print(f"Number of respondents with complete data: {complete_respondents}")

def calculate_average_study_duration():
    with open(filename, newline='', encoding='utf-8') as csv_file:
        csv_reader = csv.reader(csv_file)
        names = next(csv_reader)
        next(csv_reader)
        allData = list(csv_reader)

    study_durations = {
        "Bachelor's degree": 3,
        "Master's degree": 2,
        "Doctoral degree": 3
    }
    total_years = total_respondents = 0
    total_years_romania = total_respondents_romania = 0
    total_years_female_romania = total_respondents_female_romania = 0

    for row in allData:
        education_level = row[names.index('Q4')]
        country = row[names.index('Q3')]
        gender = row[names.index('Q2')]
        years = study_durations.get(education_level, 0)
        if years:
            total_years+=years
            total_respondents+=1
            if country == "Romania":
                total_years_romania+=years
                total_respondents_romania+=1
                if gender == "Woman":
                    total_years_female_romania+=years
                    total_respondents_female_romania+=1

    average_years_all=total_years/total_respondents
    average_years_romania=total_years_romania/total_respondents_romania
    average_years_female_romania=total_years_female_romania/total_respondents_female_romania

    print(f"Average duration of studies for all respondents: {average_years_all:.2f} years")
    print(f"Average duration of studies for respondents from Romania: {average_years_romania:.2f} years")
    print(f"Average duration of studies for female respondents from Romania: {average_years_female_romania:.2f} years")

def women_with_complete_data():
    with open(filename, newline='', encoding='utf-8') as csv_file:
        csv_reader = csv.reader(csv_file)
        names = next(csv_reader)
        next(csv_reader)
        allData = list(csv_reader)
    total_women_complete = sum(1 for row in allData if row[names.index('Q2')] == "Woman" and all(cell.strip() for cell in row))
    print(f"Number of women with complete data: {total_women_complete}")

def women_programming_language(country="Romania", language=""):
    with open(filename, newline='', encoding='utf-8') as csv_file:
        csv_reader = csv.reader(csv_file)
        names = next(csv_reader)
        next(csv_reader)
        index_age = names.index('Q1')
        index_gender = names.index('Q2')
        index_country = names.index('Q3')
        index_python = names.index('Q7_Part_1')
        index_cpp = names.index('Q7_Part_5')
        age_distribution = Counter()
        count_women = 0
        for row in csv_reader:
            if len(row) <= max(index_age, index_gender, index_country, index_python, index_cpp):
                continue
            if row[index_gender].strip() == "Woman" and row[index_country].strip() == country:
                if language == "Python":
                    knows_language = row[index_python].strip().lower() == "python"
                elif language == "C++":
                    knows_language = row[index_cpp].strip().lower() == "c++"
                if knows_language:
                    count_women+=1
                    age_distribution[row[index_age].strip()]+=1
        most_common_age_group = age_distribution.most_common(1)[0] if age_distribution else ("N/A", 0)
        print(f"Number of women from {country} who program in {language}: {count_women}")
        print(f"Age interval with most women programming in {language}: {most_common_age_group[0]} ({most_common_age_group[1]} people)")
        return count_women, most_common_age_group

def analyse_features():
    with open(filename, newline='', encoding='utf-8') as csv_file:
        csv_reader = csv.reader(csv_file)
        names = next(csv_reader)
        next(csv_reader)
        allData = list(csv_reader)
    features_info = {}
    for i, column in enumerate(names):
        values = [row[i].strip() for row in allData if row[i].strip() != ""]
        if not values:
            features_info[column] = {"Type": "Empty", "Unique Values": 0, "Values": []}
            continue
        is_numeric = all(v.replace('.', '', 1).isdigit() for v in values if v)
        if is_numeric:
            numeric_values = [float(v) for v in values]
            features_info[column] = {
                "Type": "Numeric",
                "Min": min(numeric_values),
                "Max": max(numeric_values),
                "Count": len(numeric_values)
            }
        else:
            unique_values = set(values)
            features_info[column] = {
                "Type": "Categorical",
                "Unique Values": len(unique_values),
                "Values": list(unique_values)[:10]
            }
    for feature, info in features_info.items():
        print(f"Feature: {feature}")
        for key, value in info.items():
            print(f"  {key}: {value}")
        print()

def analyse_experience():
    experience_mapping = {
        "< 1 years": 0.5,
        "1-3 years": 2,
        "3-5 years": 4,
        "5-10 years": 7.5,
        "10-20 years": 15,
        "20+ years": 25,
        "I have never written code": 0
    }
    with open(filename, newline='', encoding='utf-8') as csv_file:
        csv_reader = csv.reader(csv_file)
        names = next(csv_reader)
        next(csv_reader)
        allData = list(csv_reader)

    column_name = "Q6"
    index_experience = names.index(column_name)
    experience_years = [
        experience_mapping[row[index_experience]]
        for row in allData if row[index_experience] in experience_mapping
    ]
    min_exp = np.min(experience_years)
    max_exp = np.max(experience_years)
    mean_exp = np.mean(experience_years)
    std_dev_exp = np.std(experience_years)
    median_exp = np.median(experience_years)
    print(f"minimum: {min_exp} ani")
    print(f"maximum: {max_exp} ani")
    print(f"average: {mean_exp:.2f} ani")
    print(f"standard deviation: {std_dev_exp:.2f} ani")
    print(f"median: {median_exp:.2f} ani")

def main():
    print('Menu')
    print('1. Total number of respondents')
    print('2. Number of attributes')
    print('3. Number of complete answers')
    print('4. Average study duration for all respondents->romania->women')
    print('5. Number of women with complete data')
    print('6. Number of women according to programming language')
    print('7. Analyse data')
    print('8. Analyse experience')
    print('9. Exit')

def runner():
    main()
    while(True):
        index=int(input("Choose an option: "))
        if index==1:
            people()
        elif index==2:
            attributes()
        elif index==3:
            complete_answers()
        elif index==4:
            calculate_average_study_duration()
        elif index==5:
            women_with_complete_data()
        elif index==6:
            print("Python: ")
            python_data = women_programming_language(language="Python")
            print("C++: ")
            cpp_data = women_programming_language(language="C++")
            if python_data and cpp_data:
                print("Compare")
                women_python, age_python = python_data
                women_cpp, age_cpp = cpp_data
                if women_python > women_cpp:
                    print("python>c++")
                elif women_python < women_cpp:
                    print("python<c++")
                else:
                    print("python=c++")
        elif index==7:
            analyse_features()
        elif index==8:
            analyse_experience()
        elif index==9:
            break
        else:
            print("That's not a valid option.\n")

runner()