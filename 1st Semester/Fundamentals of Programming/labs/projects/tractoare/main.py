from repository.repository import tractorRepository
from service.service import tractorService
from ui.controller import Controller

repo = tractorRepository("domain/fisier.txt")
service = tractorService(repo)
controller = Controller(service)

controller.runner()
