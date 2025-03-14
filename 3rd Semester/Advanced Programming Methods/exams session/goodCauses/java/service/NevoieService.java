package ubb.scs.map.faptebune.service;

import ubb.scs.map.faptebune.domain.Nevoie;
import ubb.scs.map.faptebune.repository.NevoieDBRepository;

import java.util.ArrayList;
import java.util.List;

public class NevoieService{

    private final NevoieDBRepository nevoieDataBaseRepository;

    public NevoieService(NevoieDBRepository nevoieDataBaseRepository){
        this.nevoieDataBaseRepository = nevoieDataBaseRepository;
    }


    public List<Nevoie> getAllNevoi(){
        List<Nevoie> nevoi = new ArrayList<>();
        nevoieDataBaseRepository.findAll().forEach(nevoi::add);
        System.out.println("Fetched " + nevoi.size() + " nevoi");
        return nevoi;
    }

    public Nevoie updateNevoie(long idNevoie, long idPersoana) {
        return nevoieDataBaseRepository.updateNevoie(idNevoie, idPersoana);
    }

    public long getMaxId(){
        return nevoieDataBaseRepository.getMaxId();
    }

    public Nevoie adaugaNevoie(Nevoie nevoie) {
        Nevoie nevoie1 = nevoieDataBaseRepository.adaugaNevoie(nevoie);
        return nevoie1;
    }
}
