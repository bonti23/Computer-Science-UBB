from repository.repository import contactRepository
from service.service import contactService
from ui.controller import Controller

repo = contactRepository("domain/fisier.txt")
serv = contactService(repo)
controller = Controller(serv)

controller.runner()
