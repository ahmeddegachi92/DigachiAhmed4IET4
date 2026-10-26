package tn.espritDigachiAhmed4iet4.Service;

import java.util.List;
import tn.espritDigachiAhmed4iet4.Domain.Contrat;

public interface IContratService {
    Contrat getById(Long id);
    Contrat add(Contrat contrat);
    List<Contrat> findAll();
    Contrat update(Contrat contrat);
    void deleteById(Long id);
}
