from repository.repository import piesaRepository
from service.service import piesaService
from ui.controller import piesaController

repo = piesaRepository("domain/ffisier.txt")
service = piesaService(repo)
controller = piesaController(service)

controller.runner()
