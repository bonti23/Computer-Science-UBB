from repository.repository import produsRepository
from service.service import produsService
from ui.controller import produsController

repo = produsRepository("domain/fisier.txt")
serv = produsService(repo)
contr = produsController(serv)

contr.runner()
