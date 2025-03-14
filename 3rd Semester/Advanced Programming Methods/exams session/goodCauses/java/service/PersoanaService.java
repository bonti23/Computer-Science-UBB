package ubb.scs.map.faptebune.service;

import ubb.scs.map.faptebune.domain.Persoana;
import ubb.scs.map.faptebune.repository.PersoanaDBRepository;

import java.util.ArrayList;
import java.util.List;

public class PersoanaService {

    private PersoanaDBRepository persoanaDataBaseRepository;

    public PersoanaService(PersoanaDBRepository persoanaDataBaseRepository){
        this.persoanaDataBaseRepository = persoanaDataBaseRepository;
    }

    public Persoana addPersoana(Persoana persoana) {
        return persoanaDataBaseRepository.addPersoana(persoana);
    }

    public boolean verifyIfAPersonExist(String username){
        return persoanaDataBaseRepository.verifyIfAPersonExist(username);
    }

    public long getMaxId(){
        return persoanaDataBaseRepository.getMaxId();
    }

    public List<Persoana> getAllPersoane() {
        List<Persoana> persoane = new ArrayList<>();
        persoanaDataBaseRepository.findAll().forEach(persoane::add);
        System.out.println("Persoane găsite: " + persoane.size());
        return persoane;

    }


    public Persoana findOnePersoana(long idPersoana){
        return persoanaDataBaseRepository.findOne(idPersoana).orElse(null);
    }

}
