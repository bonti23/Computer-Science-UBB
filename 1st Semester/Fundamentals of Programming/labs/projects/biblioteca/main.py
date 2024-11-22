from repository.repository import carteRepository
from service.service import carteService
from ui.controller import carteController

repo = carteRepository("domain/fisier.txt")
service = carteService(repo)
controller = carteController(service)

controller.runner()
