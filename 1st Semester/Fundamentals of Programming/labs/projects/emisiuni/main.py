from repository.repository import emisiuneRepository
from service.service import emisiuneService
from ui.controller import emisiuneController

repo = emisiuneRepository("domain/fisier.txt")
service = emisiuneService(repo)
controller = emisiuneController(service)

controller.runner()
