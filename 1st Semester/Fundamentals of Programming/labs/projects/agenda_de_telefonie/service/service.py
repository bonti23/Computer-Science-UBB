from repository.repository import contactRepository
from domain.validator import contactValidator

class contactService:
    def __init__(self, contact: contactRepository):
        self.__contact = contact

    def add_service(self, id, nume, telefon, grup):
        validator = contactValidator(self.__contact)
        if validator.validate_contact(id, nume, telefon, grup) == True:
            self.__contact.add_repository(id, nume, telefon, grup)
        else:
            return validator.validate_contact(id, nume, telefon, grup)

    def find_service(self, nume):
        return self.__contact.find_telefon_for_a_certain_name(nume)

    def tiparire_service(self, grup):
        return self.__contact.tiparire_repository(grup)

    def export_service(self, grup, filename):
        self.__contact.exporta_repository(grup, filename)
