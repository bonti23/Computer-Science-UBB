"""
cmd = input("Introduceti optiunea:")
while cmd!=5:
    cmd = input("Introduceti optiunea:")
"""


def adaugare_taskuri_default(lista_taskuri):
    lista_taskuri.append("BookSkyDivingAdventure_18_in-progress")
    lista_taskuri.append("LearnJuggling_25_pending")
    lista_taskuri.append("StartVegetableGarden_27_in-progress")
    lista_taskuri.append("HostMovieMarathonNight_17_done")
    lista_taskuri.append("TravelToUnchartedIsland_31_pending")
    lista_taskuri.append("ReadBook_25_in-progress")
    lista_taskuri.append("BuildTreehouse_18_done")


def print_menu():
    print("1. Citire lista")
    print("2. Adaugare task\n3. Filtrare dupa status\n4. Task-uri pe zile")
    print("5. Iesire")
    print("10. Afisare lista de task-uri")


def citeste_lista():
    lista = input("Introduceti lista")
    # de implementat validare
    lista = lista.split(', ')
    lista = [task.strip() for task in lista]
    # daca vrem ca elementele listei sa fie nr. intregi
    # lista = [int(element) for element in lista]
    return lista


def citeste_task():
    descriere = input("Descrierea activitatii")
    zi = input("Zi deadline")
    status = input("Statusul activitatii")
    return descriere, zi, status


def adauga_in_lista(lista_taskuri):
    descriere, zi, status = citeste_task()
    task_string = descriere + '_' + zi + '_' + status
    lista_taskuri.append(task_string)



def print_lista_nicely(lista_taskuri):
    # for task in lista_taskuri:
    #     print(task)
    print("Lista de task-uri este: ")
    for task_number, task in enumerate(lista_taskuri):
        task_number_string = '[' + str(task_number) + ']'
        task_attributes = obtine_atribute_task(task)
        print(task_number_string, 'Descriere activitate:', task_attributes['descriere_task'], ' | Zi deadline:',
              task_attributes['zi_deadline_task'], ' | Status:', task_attributes['status_task'])


def obtine_atribute_task(task):
    atribute_task = task.split('_')
    descriere = atribute_task[0].strip()
    zi = int(atribute_task[1].strip())
    status = atribute_task[2].strip()
    return {'descriere_task': descriere, 'zi_deadline_task': zi, 'status_task': status}


def filtreaza_dupa_status(lista_taskuri, status):
    lista_taskuri_status_cerut = []
    for task in lista_taskuri:
        if obtine_atribute_task(task)['status_task'] == status:
            lista_taskuri_status_cerut.append(task)
    return lista_taskuri_status_cerut


def transforma_lista_taskuri_dict_zile(lista_taskuri):
    dictionar_taskuri = {}
    for task in lista_taskuri:
        atribute_task = obtine_atribute_task(task)
        zi = atribute_task['zi_deadline_task']
        if zi not in dictionar_taskuri:
            dictionar_taskuri[zi] = []
            dictionar_taskuri[zi].append(atribute_task['descriere_task'])
        else:
            dictionar_taskuri[zi].append(atribute_task['descriere_task'])
    return dictionar_taskuri


def print_dictionar_nicely(dictionar_taskuri):
    for zi_task, descrieri_taskuri in dictionar_taskuri.items():
        print('Ziua ', zi_task)
        for descriere in descrieri_taskuri:
            print('\t',descriere)


lista_taskuri = []
adaugare_taskuri_default(lista_taskuri)
while True:
    print_menu()
    option = input("Introduceti optiunea:")
    option = option.strip()
    option = int(option)

    match option:
        case 1:
            lista_taskuri = citeste_lista()
            print('Lista a fost citita cu succes.')
        case 2:
            adauga_in_lista(lista_taskuri)
            print('Task-ul s-a adaugat cu succes.')
        case 3:
            status = input('Introduceti statusul dupa care se filtreaza:')
            status = status.strip()
            lista_taskuri_filtrata = filtreaza_dupa_status(lista_taskuri, status)
            print_lista_nicely(lista_taskuri_filtrata)
        case 4:
            dictionar_taskuri = transforma_lista_taskuri_dict_zile(lista_taskuri)
            print_dictionar_nicely(dictionar_taskuri)
        case 10:
            print_lista_nicely(lista_taskuri)
        case 5:
            break
