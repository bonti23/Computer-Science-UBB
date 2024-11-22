from domain.entity import Examen
from datetime import datetime, timedelta

class examenRepository:
    def __init__(self, filename):
        self.__filename = filename

    def read_from_file(self):
        f = open(file = self.__filename, mode = "r")
        examene = []
        lines = f.readlines()
        for line in lines:
            params = line.split(",")
            params = [param.strip() for param in params]
            data = params[0]
            ora = params[1]
            materie = params[2]
            tip = params[3]
            examen = Examen(data, ora, materie, tip)
            examene.append(examen)
        f.close()
        return examene

    def write_to_file(self, examene):
        with open(file = self.__filename, mode = "w") as f:
            for examen in examene:
                params = (examen.get_data(), examen.get_ora(), examen.get_materie(), examen.get_tip())
                params = [str(param) for param in params]
                line = ", ".join(params) + "\n"
                f.write(line)

    def next_day(self):
        examene = self.read_from_file()
        today = datetime.today()
        tomorrow = today + timedelta(days=1)
        tomorrow = tomorrow.strftime("%d.%m")
        examene_maine = []
        for examen in examene:
            examen.set_data(datetime.strptime(examen.get_data(), "%d.%m"))
            if examen.get_data() == tomorrow:
                examene_maine.append(examen)
        sortate = sorted(examene_maine, key = lambda x: x.get_ora(), reverse = False)
        return sortate

    def add_exam(self, data, ora, materie, tip):
        examene = self.read_from_file()
        examen = Examen(data, ora, materie, tip)
        examene.append(examen)
        self.write_to_file(examene)

    def third_day(self, date):
        date = datetime.strptime(date, "%d.%m")
        examene = self.read_from_file()
        thirdday = date + timedelta(days=3)
        exams_bune = []
        for examen in examene:
            exam_date = datetime.strptime(examen.get_data(), "%d.%m")
            if date <= exam_date <= thirdday:
                exams_bune.append(examen)
        return exams_bune

    def export(self, filename, sir):
        super = []
        examene = self.read_from_file()
        for examen in examene:
            if sir in examen.get_materie():
                super.append(examen)
        with open(file = filename, mode = "w") as f:
            for examen in super:
                params = (examen.get_data(), examen.get_ora(), examen.get_materie(), examen.get_tip())
                params = [str(param) for param in params]
                line = ", ".join(params) + "\n"
                f.write(line)

    def find_materie(self, materie):
        examene = self.read_from_file()
        for examen in examene:
            if examen.get_materie() == materie:
                return examen
        return None

    def find_tip(self, tip):
        examene = self.read_from_file()
        for examen in examene:
            if examen.get_tip() == tip:
                return examen
        return None
