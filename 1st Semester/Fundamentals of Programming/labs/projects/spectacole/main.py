from repository.repository import spectacolRepository
from service.service import spectacolService
from ui.controller import spectacolController

repo = spectacolRepository("domain/fisier.txt")
service = spectacolService(repo)
controller = spectacolController(service)

controller.runner()
