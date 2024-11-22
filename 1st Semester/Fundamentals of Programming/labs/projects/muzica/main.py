from repository.repository import melodieRepository
from service.service import melodieService
from ui.controller import melodiesController

repo = melodieRepository("domain/fisier.txt")
service = melodieService(repo)
controller = melodiesController(service)

controller.runner()
