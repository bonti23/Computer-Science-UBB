def print_menu():
    print('1. Adaugare task')
    print('2. Cautare task cu deadline intre 2 date')
    print('3. Stergere task dupa descriere')
    print('4. Eliminare task-uri dupa status')
    print('5. Filtrare: afisare task-uri carre contin in descriere string')
    print('6. Afisare raport pe luna')
    print('7. Undo')
    print('P. Afisarea tuturor task-urilor')
    print('A. Adaugare task-uri default')
    print('E. Iesire')


def validate_task(task) -> int:
    """
    Valideaza un task dat
    :param task: task-ul de validat
    :type task: dict
    :return: coduri de eroare
            0 - descrierea nu este string
            1 - descrierea are mai putin de 2 caractere
            2 - ...
            3 - ...
    :rtype: int
    """
    if type(task['descriere']) != str:
        return 0
    if len(task['descriere']) < 2:
        return 1
    # TO DO: add other validations


def print_task_list(task_list):
    for task in task_list:
        print(task)


def create_task(task_str: str) -> dict:
    """
    Creeaza un task
    :param task_str: string-ul care reprezinta task-ul
    :type task_str: str
    :return: task-ul creat
    :rtype: dict
    """
    descriere, data, status = task_str.split(',')
    descriere = descriere.strip()
    data = data.strip()
    status = status.strip()
    zi, luna = data.split('-')
    task_dict = {'descriere': descriere, 'zi_deadline': zi, 'luna_deadline': luna, 'status': status}
    return task_dict


def add_task(task_list: list, task: dict) -> None:
    """
    Adauga un task in lista de task-uri
    :param task_list: lista de task-uri
    :type task_list: list
    :param task: task-ul de adaugat
    :type task: dict
    :return: -; modifica lista prin adaugarea la sfarsit a task-ului
    :rtype:
    """
    task_list.append(task)


def add_task_ui():
    # ui = user interface/interfata cu utilizatorul
    task_str = input('Introduceti detaliile task-ului separate prin virgula:')
    task = create_task(task_str)
    add_task(task_list, task)


# 1 task = 1 dictionar
# example_task = {'descriere': 'BookSkydivingAdventure', 'deadline_zi':10, 'deadline_month': 8, 'status': 'pending'}
# Alte reprezentari posibile pentru 1 task:
#    lista: ['BookSkydivingAdventure', 10, 8, 'pending'] (conventie ca pe poz 0 avem descriere, poz 1 zi,...)
#    tuplu: (...)

# lista de task-uri = lista de dictionare
task_list = []
while True:
    print_menu()
    option = input('>')
    option = option.upper().strip()
    if option == "P":
        print_task_list(task_list)
    elif option == "A":
        pass
    elif option == '1':
        add_task_ui()
    elif option == '2':
        pass
    elif option == '3':
        pass
    elif option == '4':
        pass
    elif option == '5':
        pass
    elif option == '6':
        pass
    elif option == '4':
        pass
    elif option == "E":
        break
    else:
        print("Invalid option.")


def test_adaugare():
    test_task_list = []
    task = create_task("HostMovieMarathon, 10-08, pending")
    add_task(test_task_list, task)
    assert (len(test_task_list) == 1)
    assert (test_task_list[0]['descriere'] == 'HostMovieMarathon')
    # assert (len(task['descriere']) >= 2)


def test_create_task():
    test_task_str = "BookSkydivingAdventure, 11-12, pending"
    task = create_task(test_task_str)
    assert (type(task) == dict)
    assert (task['descriere'] == "BookSkydivingAdventure")
    # assert (task['zi_deadline'] == 11)
    # assert (task['luna_deadline'] == 12)
    # assert (task['status'] == 'pending')

# test_adaugare()
