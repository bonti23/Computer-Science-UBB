from repository.repository import evenimentRepository
from service.service import evenimentService
from ui.controller import evenimentController

repo = evenimentRepository("domain/fisier.txt")
service = evenimentService(repo)
controller = evenimentController(service)

controller.runner()
