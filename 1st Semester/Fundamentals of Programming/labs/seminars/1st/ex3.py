from pprint import pprint

villain_dict = {}
villain_dict['Shade'] = 'darkness_manipulation'
villain_dict['Weather Wizard'] = 'weather_manipulation'
villain_dict['Melanie'] = 'telepathy'

#pretty printing
pprint(villain_dict)

#Functionalitate 1: Afisare superputere după nume
villain_name = input("Which is the villain you are seeking?")

#S-ar putea sa citim cu spatii suplimentare de la tastatura
#si atunci comparatia intre string-uri nu ne va returna True
#i.e. ”Melanie ”!=”Melanie”
#strip() utilizat pentru a scapa de posibile spatii inainte sau dupa
#string-ul citit
villain_name = villain_name.strip()
if villain_name in villain_dict:
    #daca am gasit numele in dictionar
    superpower = villain_dict[villain_name]
    superpower = superpower.replace('_',' ')
    superpower = superpower.capitalize()
    print('Their superpower is', superpower)
else:
    print('That particular villain is not registered yet.')


#Functionalitate 2: Memorare lista cu datele incidentelor
villain_name = input("For which villain do you want to add info?").strip()
#presupunem ca exista, altfel facem verificarea ca mai sus

#schimbam modul in care stocam date in dictionar pentru a putea retine si date
#cum altfel am putea stoca informatiile in dictionar?
for v_name in villain_dict.keys():
    
    villain_superpower = villain_dict[v_name]
    villain_dict[v_name] = {'superpower':villain_superpower, 'incidents_dates':[]}

cmd = "insert"
while cmd.strip()!="done":
    data = input("Data de introdus (format dd/mm/yyyy)")
    data = data.strip()
    villain_dict[villain_name]['incidents_dates'].append(data)
    #va continua doar daca introduc comanda insert, sau orice string?
    cmd = input("Introduceti comanda insert daca doriti sa mai adaugati, done daca ati finalizat.")

print()
print('Dupa introducerea ultimelor date, acum avem urmatoarele informatii:')
for villain_name, villain_info in villain_dict.items():
    superpower = villain_info['superpower']
    incident_dates = villain_info['incidents_dates']
    print(str.format("Villain {} has a superpower of {} and we registered the following incident dates:{}", villain_name, superpower, incident_dates)) 


#Functionalitate 3: Numarul de zile de la ultimul incident

#pentru usurinta calculului, folosim modulul datetime
#consideram ultimul incident cel introdus ultimul in lista (presupunem ca introducem in ordine cronologica)
#vom discuta sortari ulterior

villain_name = input("For which villain do you want to compute number of days since last incident?").strip()
#Presupunem ca exista villain cu numele dat, altfel includem si verificare
#Dar verificam daca avem inregistrate date

incident_dates_for_villain = villain_dict[villain_name]['incidents_dates']
if len(incident_dates_for_villain)==0:
    print("No registered incidents for the given villain.")
else:
    
    last_incident_date = incident_dates_for_villain[-1]
    date_info = last_incident_date.split('/')
    print("After split, we have the following info:", date_info)
    day, month, year = date_info
    
    from datetime import date
    
    #una din modalitatile de a transforma un str dat
    #la un tip de date care ne permite o scadere directa
    incident_date = date(int(year), int(month), int(day))
    today = date.today()

    delta = today-incident_date
    print("Au trecut", delta.days, "zile de la ultimul incident.")




