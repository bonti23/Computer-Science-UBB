from repository.repository import jucatorRepository
from service.service import jucatorService
from ui.controller import Controller

repo = jucatorRepository("domain/fisier.txt")
service = jucatorService(repo)
controller = Controller(service)

controller.runner()
