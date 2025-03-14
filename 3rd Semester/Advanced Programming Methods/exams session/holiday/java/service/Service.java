package ubb.scs.map.vacante.service;

import javafx.scene.control.Alert;
import ubb.scs.map.vacante.domain.*;
import ubb.scs.map.vacante.repository.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Service {
    private final LocationDBRepository locationRepo;
    private final HotelDBRepository hotelRepo;
    private final SpecialOfferDBRepository offRepo;
    private final ClientDBRepository clientRepo;
    private final ReservationDBRepository rezervareRepo;

    public Service(LocationDBRepository locationRepo, HotelDBRepository hotelRepo, SpecialOfferDBRepository offRepo, ClientDBRepository clientRepo, ReservationDBRepository rezervareRepo) {
        this.locationRepo = locationRepo;
        this.hotelRepo = hotelRepo;
        this.offRepo = offRepo;
        this.clientRepo = clientRepo;
        this.rezervareRepo = rezervareRepo;
    }

    public List<Location> getLocations() {
        List<Location> locations = new ArrayList<>();
        locationRepo.findAll().forEach(locations::add);
        return locations;
    }

    public List<Hotel> getHotels() {
        List<Hotel> hotels = new ArrayList<>();
        hotelRepo.findAll().forEach(hotels::add);
        return hotels;
    }

    public List<SpecialOffer> getOffers() {
        List<SpecialOffer> offers = new ArrayList<>();
        offRepo.findAll().forEach(offers::add);
        return offers;
    }

    public List<Client> getClients() {
        List<Client> clients = new ArrayList<>();
        clientRepo.findAll().forEach(clients::add);
        return clients;
    }

    public Long getLocationId(String location) {
        Long locationId = StreamSupport.stream(locationRepo.findAll().spliterator(), false)
                .filter(loc -> loc.getName().equals(location))
                .map(Location::getID)
                .findFirst()
                .orElse(null);
        return locationId;
    }

    public Client getClientById(Long idClient) {
        Optional<Client> client = clientRepo.findOne(idClient);

        return client.orElse(null);
    }

    public List<SpecialOffer> getOffersAvailable(Long idClient) {
        Client client = getClientById(idClient);
        if (client == null) {
            return new ArrayList<>();
        }

        List<SpecialOffer> offers = StreamSupport.stream(offRepo.findAll().spliterator(), false)
                .filter(off -> off.getEnd().isAfter(LocalDate.now()) && client.getFidelitygrade() >= off.getPercent())
                .collect(Collectors.toList());

        return offers;


    }

    public void adaugaRezervare(Long idClient, Long idHotel, LocalDate dataStart, Integer noNights) {
        Reservation r = new Reservation(idClient.intValue(), idHotel.intValue(), dataStart, noNights);
        rezervareRepo.adauga(r);
        Client reservingClient = getClientById(idClient);
        if (reservingClient != null) {
            notifyClientsWithSameHobby(reservingClient);
        }
    }
    private void notifyClientsWithSameHobby(Client reservingClient) {
        // Obținem toți clienții
        List<Client> allClients = getClients();

        for (Client client : allClients) {
            // Verificăm dacă clientul curent nu este același cu cel care a făcut rezervarea
            if (!client.getID().equals(reservingClient.getID())) {
                // Comparăm hobby-urile clientului care face rezervarea cu cele ale altor clienți
                if (client.getHobby().equals(reservingClient.getHobby())) {
                    // Creăm și afișăm un alert pentru notificare
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Notificare Rezervare");
                    alert.setHeaderText("Un alt utilizator cu același hobby a făcut o rezervare!");
                    alert.setContentText("Inca un utilizator cu hobby-ul " + reservingClient.getHobby() +
                            " a facut o rezervare la hotelul X.");
                    alert.showAndWait();
                }
            }
        }
    }
}
