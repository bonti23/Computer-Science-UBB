from repository.repository import jucatorRepository
from service.service import jucatorService
from ui.controller import jucatorController

repo = jucatorRepository("domain/fisier.txt")
service = jucatorService(repo)
controller = jucatorController(service)

controller.runner()
