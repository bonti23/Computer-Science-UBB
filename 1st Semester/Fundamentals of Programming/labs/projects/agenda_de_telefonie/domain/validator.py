from repository.repository import contactRepository

class contactValidator:
    def __init__(self, contact: contactRepository):
        self.__contact = contact

    def validate_contact(self, id, nume, telefon, grup):
        errors = []
        if self.__contact.find_id(id):
            errors.append("id deja existent!")
        if self.__contact.find_nume(nume):
            errors.append("un nume nu poate avea 2 numere de telefon!")
        if len(telefon)!=10 and telefon[0]!="0" and telefon[1]!="7":
            errors.append("numar de telefon invalid")
        if grup!="prieteni" and grup!="familie" and grup!="job" and grup!="altele":
            errors.append("nu exista un asemenea grup!")
        if self.__contact.find_telefon(telefon):
            errors.append("numar de telefon deja existent!")
        if len(errors)>0:
            return errors
        else:
            return True
