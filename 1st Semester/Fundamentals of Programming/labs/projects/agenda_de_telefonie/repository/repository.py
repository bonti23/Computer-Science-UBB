from domain.entity import Contact

class contactRepository:
    def __init__(self, filename):
        self.__filename = filename

    def read_from_file(self):
        f= open(file = self.__filename, mode = "r")
        contacte = []
        lines = f.readlines()
        for line in lines:
            params = line.split(",")
            params = [param.strip() for param in params]
            id = params[0]
            nume = params[1]
            telefon = params[2]
            grup = params[3]
            contact = Contact(id, nume, telefon, grup)
            contacte.append(contact)
        f.close()
        return contacte

    def write_to_file(self, contacte):
        with open(file = self.__filename, mode = "w") as f:
            for contact in contacte:
                params = (contact.get_id(), contact.get_nume(), contact.get_telefon(), contact.get_grup())
                params = [str(param) for param in params]
                line = ", ".join(params) + "\n"
                f.write(line)

    def add_repository(self, id, nume, telefon, grup):
        contacte = self.read_from_file()
        contact = Contact(id, nume, telefon, grup)
        contacte.append(contact)
        self.write_to_file(contacte)

    def find_id(self, id):
        contacte = self.read_from_file()
        for contact in contacte:
            if contact.get_id() == id:
                return contact
        return None

    def find_nume(self, nume):
        contacte = self.read_from_file()
        for contact in contacte:
            if contact.get_nume() == nume:
                return contact
        return None

    def find_telefon(self, telefon):
        contacte = self.read_from_file()
        for contact in contacte:
            if contact.get_telefon() == telefon:
                return contact
        return None

    def find_telefon_for_a_certain_name(self, nume):
        try:
            if self.find_nume(nume):
                return self.find_nume(nume)
        except:
            raise ValueError("no such name!")

    def tiparire_repository(self, grup):
        contacte = self.read_from_file()
        jmekerii = [contact for contact in contacte if contact.get_grup() == grup]
        jmekerii_sortati = sorted(jmekerii, key=lambda x: x.get_nume(), reverse=False)
        if len(jmekerii_sortati) == 0:
            raise ValueError("no such group!")
        else:
            return jmekerii_sortati

    def exporta_repository(self, grup, filename):
        contacte = self.read_from_file()
        jmekerii = [contact for contact in contacte if contact.get_grup() == grup]
        with open(file = filename, mode = "w") as f:
            for contact in jmekerii:
                params = (contact.get_nume(), contact.get_telefon())
                params = [str(param) for param in params]
                line = ", ".join(params) + "\n"
                f.write(line)
