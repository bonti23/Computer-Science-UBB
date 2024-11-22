from repository.repository import examenRepository
from service.service import examenService
from ui.controller import Controller

repo = examenRepository("domain/fisier.txt")
service = examenService(repo)
controller = Controller(service)

controller.runner()
