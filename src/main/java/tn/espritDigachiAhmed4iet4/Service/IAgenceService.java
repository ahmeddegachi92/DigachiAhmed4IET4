package tn.espritDigachiAhmed4iet4.Service;

import java.util.List;
import tn.espritDigachiAhmed4iet4.Domain.Agence;

public interface IAgenceService {
    Agence getById(Long id);
    Agence add(Agence agence);
    List<Agence> findAll();
    Agence update(Agence agence);
    void deleteById(Long id);
}
