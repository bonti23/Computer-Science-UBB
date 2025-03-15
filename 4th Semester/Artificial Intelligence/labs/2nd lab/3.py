from PyDictionary import PyDictionary

file="text/texts.txt"

def number_of_sentences():
    with open(file, "r", encoding="utf-8") as f:
        text=f.read()
    sentence_enders=['.', '!', '?']
    sentence_count=sum(text.count(ender) for ender in sentence_enders)
    return sentence_count

def number_of_words():
    with open(file, "r", encoding="utf-8") as f:
        text=f.read()
    words = text.split()
    return len(words)

def different_words():
    with open(file, "r", encoding="utf-8") as f:
        text=f.read()
    words = text.split()
    unique_words = set(words)
    return len(unique_words)

def shortest_longest():
    with open(file, "r", encoding="utf-8") as f:
        text=f.read()
    words = text.split()
    words = [word.strip('.,!?()[]{}":;') for word in words]
    sorted_words = sorted(words, key=len)
    print("The shortest word is:", sorted_words[0])
    print("The longest word is:", sorted_words[len(sorted_words)-1])

def without_diactritics():
    """
    translation_table = str.maketrans({
        'ă': 'a',
        'â': 'a',
        'î': 'i',
        'ș': 's',
        'ț': 't'
    })

    with open(file, "r", encoding="utf-8") as f:
        text = f.read()

    # Use translate to remove diacritics
    return text.translate(translation_table)
    """
    with open(file, "r", encoding="utf-8") as f:
        text=f.read()
    new_text = ""
    for char in text:
        if char == 'ă' or char == 'â':
            new_text += 'a'
        elif char == 'î':
            new_text += 'i'
        elif char == 'ș':
            new_text += 's'
        elif char == 'ț':
            new_text += 't'
        else:
            new_text += char
    return new_text

def synonym():
    with open(file, "r", encoding="utf-8") as f:
        text=f.read()
    words = text.split()
    words = [word.strip('.,!?()[]{}":;') for word in words]
    sorted_words = sorted(words, key=len)
    longest=sorted_words[len(sorted_words)-1]
    dictionary = PyDictionary()
    synonyms = dictionary.synonym(longest)
    return synonyms if synonyms else ["No synonyms found."]


def main():
    print('Menu: ')
    print("1. Number of sentences in text.")
    print("2. Number of words in text.")
    print("3. Number of different words in text.")
    print("4. The shortest & the longest word in text.")
    print("5. Without diacritics.")
    print("6. Synonym for the longest word in text.")
    print("7. Exit.")

def runner():

    main()
    while(True):
        index=int(input("Enter your choice: "))
        if index==1:
            print(number_of_sentences())
        elif index==2:
            print(number_of_words())
        elif index==3:
            print(different_words())
        elif index==4:
            shortest_longest()
        elif index==5:
            print(without_diactritics())
        elif index==6:
            print(synonym())
        elif index==7:
            print("Thank you!")
            break
        else:
            print("Invalid choice.")

runner()