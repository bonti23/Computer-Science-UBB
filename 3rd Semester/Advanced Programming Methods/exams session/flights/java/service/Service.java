package ubb.scs.map.zboruri.service;

import ubb.scs.map.zboruri.domain.Client;
import ubb.scs.map.zboruri.domain.Flight;
import ubb.scs.map.zboruri.domain.Ticket;
import ubb.scs.map.zboruri.repository.ClientDBRepository;
import ubb.scs.map.zboruri.repository.FlightDBRepository;
import ubb.scs.map.zboruri.repository.TicketDBRepository;
import ubb.scs.map.zboruri.utils.observer.ObservableImplementat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Service extends ObservableImplementat {
    private ClientDBRepository clientRepo;
    private FlightDBRepository flightRepo;
    private TicketDBRepository ticketRepo;
    //private List<Observer<EntityChangeEvent>> observers= new ArrayList<>();

    public Service(ClientDBRepository clientRepo, FlightDBRepository flightRepo, TicketDBRepository ticketRepo) {
        this.clientRepo = clientRepo;
        this.flightRepo = flightRepo;
        this.ticketRepo = ticketRepo;
    }

    public List<Client> getLocations() {
        List<Client> clients = new ArrayList<>();
        clientRepo.findAll().forEach(clients::add);
        return clients;
    }

    public List<Flight> getFlights() {
        List<Flight> flights = new ArrayList<>();
        flightRepo.findAll().forEach(flights::add);
        return flights;
    }

    public List<Ticket> getTickets() {
        List<Ticket> tickets = new ArrayList<>();
        ticketRepo.findAll().forEach(tickets::add);
        return tickets;
    }

    public Client getClientByUsername(String username) {
        for (Client c : clientRepo.findAll())
            if (c.getUsername().equals(username))
                return c;
        return null;
    }

    public void adaugaTicket(String username, Integer idFlight) {
        Ticket ticket = new Ticket(username, idFlight, LocalDateTime.now());
        ticketRepo.adauga(ticket);
    }
}
